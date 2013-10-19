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

    //定数
    static final int REQUEST_PICK_CONTACT = 99;

    static final String PREF_LAT = "pref_lat";
    static final String PREF_LONG = "pref_long";
    static final String PREF_DIST = "pref_dist";
    static final String PREF_LEVEL = "pref_level";
    static final String PREF_GAS = "pref_gas";
    
	//View部品

	private View clickGetLocation;
	private View clickAchievements;
    private ImageView imgv;

    //設定ファイル関連
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

		Log.v(getString(R.string.log),"MainActivity　onCreate() start");

		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      	//位置取得ボタン
        clickGetLocation = findViewById(R.id.button_loc);
        clickGetLocation.setOnClickListener(oCLforShowButton);
        
      	//実績ボタン
        clickAchievements = findViewById(R.id.button_achievements);
        clickAchievements.setOnClickListener(oCLforShowButton);

        Log.v(getString(R.string.log),"MainActivity　onCreate() end");

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
    
    //画面表示の設定
    private void setView(){
		Log.v(getString(R.string.log),"MainActivity　setView() start");
		
		//設定データ取得
    	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    	
    	//レベル
    	TextView text_level = (TextView)findViewById( R.id.image_hekigai_0 );
    	text_level.setText(pref.getString(PREF_LEVEL, "1"));

    	//テキスト設定
    	TextView text_lat = (TextView)findViewById( R.id.text_lat );
    	TextView text_long = (TextView)findViewById( R.id.text_long );
    	TextView text_dist = (TextView)findViewById( R.id.text_dist );
    	
    	text_lat.setText(pref.getString(PREF_LAT, "0"));
    	text_long.setText(pref.getString(PREF_LONG, "0"));
    	text_dist.setText(pref.getString(PREF_DIST, "999"));

    	Log.v(getString(R.string.log),"MainActivity　setView() end");

    }

    //ボタンクリック時のリスナ
    private final OnClickListener oCLforShowButton = new OnClickListener() {
    	
        @Override
        public void onClick(View v) {
            switch(v.getId()){

            //位置情報取得
            case R.id.button_loc:
                startActivity(new Intent( getApplicationContext(), ReportActivity.class ));
                overridePendingTransition(0, 0);
                break;
                
            //実績ページに遷移
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

