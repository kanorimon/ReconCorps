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

    static final String PREF_DIST = "pref_dist";
    static final String PREF_UP_DIST = "pref_up_dist";
    static final String PREF_LEVEL = "pref_level";
    
	//View���i

	private View clickGetLocation;
	private View clickAchievements;
	private View clickHistory;

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

      	//�̍��{�^��
        clickHistory = findViewById(R.id.button_history);
        clickHistory.setOnClickListener(oCLforShowButton);

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
    void setView(){
		Log.v(getString(R.string.log),"MainActivity�@setView() start");
		
		//�ݒ�f�[�^�擾
    	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    	
    	//���x��
    	setLevel(Integer.parseInt(pref.getString(PREF_LEVEL, "1")));
    	
    	//�݌v����
    	setNowDist(Float.parseFloat(pref.getString(PREF_DIST, "0")));
    	
    	//�c�苗��
    	setUpDist(Float.parseFloat(pref.getString(PREF_UP_DIST, "1000")));

    	Log.v(getString(R.string.log),"MainActivity�@setView() end");

    }
    
    void setLevel(int level){

    	setNumDrawable((ImageView)findViewById( R.id.image_hekigai_0),level / 10,true);
    	setNumDrawable((ImageView)findViewById( R.id.image_hekigai_1),level % 10,false);
    	
    }
    
    void setNowDist(float dist){

    	setNumDrawable((ImageView)findViewById( R.id.image_level_0),(int)(dist / 10000000f),true);
    	setNumDrawable((ImageView)findViewById( R.id.image_level_1),(int)((dist % 10000000f) / 1000000f),true);
    	setNumDrawable((ImageView)findViewById( R.id.image_level_2),(int)((dist % 1000000f) / 100000f),true);
    	setNumDrawable((ImageView)findViewById( R.id.image_level_3),(int)((dist % 100000f) / 10000f),true);
    	setNumDrawable((ImageView)findViewById( R.id.image_level_4),(int)((dist % 10000f) / 1000f),false);
    	setNumDrawable((ImageView)findViewById( R.id.image_level_5),(int)((dist % 1000f) / 100f),false);
    	setNumDrawable((ImageView)findViewById( R.id.image_level_6),(int)((dist % 100f) / 10f),false);
    	
    }
    
    void setUpDist(float dist){

    	setNumDrawable((ImageView)findViewById( R.id.image_ruikei_0),(int)(dist / 10000000f),true);
    	setNumDrawable((ImageView)findViewById( R.id.image_ruikei_1),(int)((dist % 10000000f) / 1000000f),true);
    	setNumDrawable((ImageView)findViewById( R.id.image_ruikei_2),(int)((dist % 1000000f) / 100000f),true);
    	setNumDrawable((ImageView)findViewById( R.id.image_ruikei_3),(int)((dist % 100000f) / 10000f),true);
    	setNumDrawable((ImageView)findViewById( R.id.image_ruikei_4),(int)((dist % 10000f) / 1000f),false);
    	setNumDrawable((ImageView)findViewById( R.id.image_ruikei_5),(int)((dist % 1000f) / 100f),false);
    	setNumDrawable((ImageView)findViewById( R.id.image_ruikei_6),(int)((dist % 100f) / 10f),false);
    	
    }
    
    
    void setNumDrawable(ImageView imv, int num, boolean istop){

    	if(istop && num==0){
    		imv.setVisibility(View.GONE);  		
    	}else{
    		imv.setImageBitmap(BitmapFactory.decodeResource(getResources(), getuNumDrawable(num))); 
    	}

    	
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
                MainActivity.this.finish();
                startActivity(new Intent( getApplicationContext(), ReportActivity.class ));
                overridePendingTransition(0, 0);
                break;
 
                //���уy�[�W�ɑJ��
                case R.id.button_history:
                    startActivity(new Intent( getApplicationContext(), HistoryActivity.class ));
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

