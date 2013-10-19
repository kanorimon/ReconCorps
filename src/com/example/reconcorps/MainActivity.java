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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

public class MainActivity extends Activity {

    //�萔
    static final int REQUEST_PICK_CONTACT = 99;

    static final String PREF_LAT = "pref_lat";
    static final String PREF_LONG = "pref_long";
    static final String PREF_DIST = "pref_dist";
    static final String PREF_LEVEL = "pref_level";
    static final String PREF_GAS = "pref_gas";
    
	//View���i

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
	    clickGetLocation.setOnClickListener(null);
	    System.gc();
    }
    
    //��ʕ\���̐ݒ�
    private void setView(){
		Log.v(getString(R.string.log),"MainActivity�@setView() start");
		
		//�ݒ�f�[�^�擾
    	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    	
    	//���x��
    	setLevel(Integer.parseInt(pref.getString(PREF_LEVEL, "1")));

    	//�e�L�X�g�ݒ�
    	/*
    	TextView text_lat = (TextView)findViewById( R.id.text_lat );
    	TextView text_long = (TextView)findViewById( R.id.text_long );
    	TextView text_dist = (TextView)findViewById( R.id.text_dist );
    	
    	text_lat.setText(pref.getString(PREF_LAT, "0"));
    	text_long.setText(pref.getString(PREF_LONG, "0"));d
    	text_dist.setText(pref.getString(PREF_DIST, "999"));
    	*/

    	Log.v(getString(R.string.log),"MainActivity�@setView() end");

    }
    
    void setLevel(int level){

    	int topDrawable = getuNumDrawable(level / 10);
    	int bottomDrawable = getuNumDrawable(level %10);
    	
    	ImageView level1 = (ImageView)findViewById( R.id.image_hekigai_0);
    	level1.setImageBitmap(BitmapFactory.decodeResource(getResources(), topDrawable)); 
    	
    	ImageView level2 = (ImageView)findViewById( R.id.image_hekigai_1);
    	level2.setImageBitmap(BitmapFactory.decodeResource(getResources(), bottomDrawable));
    	
    }
    
    int getuNumDrawable(int num){
    	
    	int topDrawable = 0;
    	switch(num){
    	case 0:
    		topDrawable = R.drawable.num_l_0;
    		break;
    	case 1:
    		topDrawable = R.drawable.num_l_1;
    		break;
    	case 2:
    		topDrawable = R.drawable.num_l_2;
    		break;
    	case 3:
    		topDrawable = R.drawable.num_l_3;
    		break;
    	case 4:
    		topDrawable = R.drawable.num_l_4;
    		break;
    	case 5:
    		topDrawable = R.drawable.num_l_5;
    		break;
    	case 6:
    		topDrawable = R.drawable.num_l_6;
    		break;
    	case 7:
    		topDrawable = R.drawable.num_l_7;
    		break;
    	case 8:
    		topDrawable = R.drawable.num_l_8;
    		break;
    	case 9:
    		topDrawable = R.drawable.num_l_9;
    		break;
    	}
    	
    	return topDrawable;

    }
    

    //�{�^���N���b�N���̃��X�i
    private final OnClickListener oCLforShowButton = new OnClickListener() {
    	
        @Override
        public void onClick(View v) {
            switch(v.getId()){

            //�ʒu���擾
            case R.id.button_loc:
                startActivity(new Intent( getApplicationContext(), ReportActivity.class ));
                overridePendingTransition(0, 0);
                break;
                
            //���уy�[�W�ɑJ��
            case R.id.button_achievements:
                startActivity(new Intent( getApplicationContext(), AchievementsActivity.class ));
                overridePendingTransition(0, 0);
                break;

        }
            
        }
    };
    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
    	if (keyCode == KeyEvent.KEYCODE_BACK){
			MainActivity.this.finish();
    		return true;
    	}
    	return false;
    }


 }

