package com.example.reconcorps;

import java.io.BufferedInputStream;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

public class MainActivity extends Activity {

    //�萔
    static final int REQUEST_PICK_CONTACT = 99;
    static final String PREF_IMAGE = "pref_image";
    static final String PREF_LAT = "pref_lat";
    static final String PREF_LONG = "pref_long";
    static final String PREF_DIST = "pref_dist";

	//View���i
	private View clickButtonImage;
	private View clickGetLocation;
	private View clickAchievements;
    private ImageView imgv;

    //�ݒ�t�@�C���֘A
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

		Log.v(getString(R.string.log),"MainActivity�@onCreate() start");

		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //�A�N�V�����o�[��\��
        ActionBar actionBar = getActionBar();
		actionBar.hide();
        
      	//�ǎ��ݒ�{�^��
        clickButtonImage = findViewById(R.id.button_image);
        clickButtonImage.setOnClickListener(oCLforShowButton);

      	//�ʒu�擾�{�^��
        clickGetLocation = findViewById(R.id.button_loc);
        clickGetLocation.setOnClickListener(oCLforShowButton);
        
      	//���у{�^��
        clickAchievements = findViewById(R.id.button_achievements);
        clickAchievements.setOnClickListener(oCLforShowButton);

        Log.v(getString(R.string.log),"MainActivity�@onCreate() end");

    }
    
    @Override
    public void onStart(){
    	super.onStart();
    	setView();
   }
    
    @Override
    public void onRestart(){
    	super.onRestart();
    	setView();
    }
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
	    imgv.setBackground(null);
	    clickButtonImage.setOnClickListener(null);
	    clickGetLocation.setOnClickListener(null);
	    System.gc();
    }
    
    //��ʕ\���̐ݒ�
    private void setView(){
		Log.v(getString(R.string.log),"MainActivity�@setView() start");
		
		//�ݒ�f�[�^�擾
    	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    	
    	//��ʕ��i�擾
    	imgv = (ImageView)findViewById(R.id.imageView1);

		//�ǎ��ݒ�
    	setWallpaper();
    	
    	//�e�L�X�g�ݒ�
    	TextView text_lat = (TextView)findViewById( R.id.text_lat );
    	TextView text_long = (TextView)findViewById( R.id.text_long );
    	TextView text_dist = (TextView)findViewById( R.id.text_dist );
    	
    	text_lat.setText(pref.getString(PREF_LAT, "0"));
    	text_long.setText(pref.getString(PREF_LONG, "0"));
    	text_dist.setText(pref.getString(PREF_DIST, "999"));

    	Log.v(getString(R.string.log),"MainActivity�@setView() end");

    }
    
    private void setWallpaper(){
    	if(pref.getString(PREF_IMAGE, "none").equals("none")){
    		//�f�t�H���g
    	    imgv.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.def));	
    	}else{
    		//���[�U�[�w��
    		try{
    			BufferedInputStream inputStream = new BufferedInputStream(getContentResolver().openInputStream(Uri.parse(pref.getString(PREF_IMAGE, null))));
    			Bitmap image = BitmapFactory.decodeStream(inputStream);
    			imgv.setImageBitmap(image);
    		}catch(Exception e){
    			Log.v(getString(R.string.log),"MainActivity�@setWallpaper() error");
    		}
    	}
    }

    //�{�^���N���b�N���̃��X�i
    private final OnClickListener oCLforShowButton = new OnClickListener() {
    	
        @Override
        public void onClick(View v) {
            switch(v.getId()){

            //�ʒu���擾
            case R.id.button_loc:
                startActivity(new Intent( getApplicationContext(), ReportActivity.class ));
                break;
                
            //���уy�[�W�ɑJ��
            case R.id.button_achievements:
                startActivity(new Intent( getApplicationContext(), AchievementsActivity.class ));
                break;

            //�ǎ��ݒ�
            case R.id.button_image:
            	putImage();
            	break;
            }
        }
    };
    
    //�ǎ��ݒ�
    void putImage(){
    	final String[] items = {"�f�t�H���g", "�摜�t�H���_����I��"};
    	
    	new AlertDialog.Builder(MainActivity.this)
    	.setTitle("�ǎ��ݒ�")
    	.setItems(items, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	        switch (item) {
    	        case 0:
    	        	//�f�t�H���g"none"��ݒ�
        			prefEditor = pref.edit();
        			prefEditor.putString(PREF_IMAGE, "none");
        			prefEditor.commit();
        			//�摜�ݒ�
        			setWallpaper();
    	            break;
    	        case 1:
    	        	//�M�������[���Ă�
                	Intent intentGetImage = new Intent(Intent.ACTION_PICK);
                	intentGetImage.setType("image/*");
                	startActivityForResult(intentGetImage, REQUEST_PICK_CONTACT);
    	            break;
    	        }
    	    }
    	})
    	.show();	
    }
    
    //�M�������[�߂莞�̃��X�i
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == REQUEST_PICK_CONTACT && resultCode == RESULT_OK) {
    		try {
    			//�I�������摜��URI��ݒ�
    			prefEditor = pref.edit();
    			prefEditor.putString(PREF_IMAGE, data.getData().toString());
    			prefEditor.commit();
    			//�摜�ݒ�
    			setWallpaper();
    		} catch (Exception e) {
    			Log.v(getString(R.string.log),"MainActivity�@onActivityResult() error");
    		}
    	}
    }


 }

