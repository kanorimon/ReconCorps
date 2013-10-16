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

    //定数
    static final int REQUEST_PICK_CONTACT = 99;
    static final String PREF_IMAGE = "pref_image";
    static final String PREF_LAT = "pref_lat";
    static final String PREF_LONG = "pref_long";
    static final String PREF_DIST = "pref_dist";

	//View部品
	private View clickButtonImage;
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

        //アクションバー非表示
        ActionBar actionBar = getActionBar();
		actionBar.hide();
        
      	//壁紙設定ボタン
        clickButtonImage = findViewById(R.id.button_image);
        clickButtonImage.setOnClickListener(oCLforShowButton);

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
	    imgv.setBackground(null);
	    clickButtonImage.setOnClickListener(null);
	    clickGetLocation.setOnClickListener(null);
	    System.gc();
    }
    
    //画面表示の設定
    private void setView(){
		Log.v(getString(R.string.log),"MainActivity　setView() start");
		
		//設定データ取得
    	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    	
    	//画面部品取得
    	imgv = (ImageView)findViewById(R.id.imageView1);

		//壁紙設定
    	setWallpaper();
    	
    	//テキスト設定
    	TextView text_lat = (TextView)findViewById( R.id.text_lat );
    	TextView text_long = (TextView)findViewById( R.id.text_long );
    	TextView text_dist = (TextView)findViewById( R.id.text_dist );
    	
    	text_lat.setText(pref.getString(PREF_LAT, "0"));
    	text_long.setText(pref.getString(PREF_LONG, "0"));
    	text_dist.setText(pref.getString(PREF_DIST, "999"));

    	Log.v(getString(R.string.log),"MainActivity　setView() end");

    }
    
    private void setWallpaper(){
    	if(pref.getString(PREF_IMAGE, "none").equals("none")){
    		//デフォルト
    	    imgv.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.def));	
    	}else{
    		//ユーザー指定
    		try{
    			BufferedInputStream inputStream = new BufferedInputStream(getContentResolver().openInputStream(Uri.parse(pref.getString(PREF_IMAGE, null))));
    			Bitmap image = BitmapFactory.decodeStream(inputStream);
    			imgv.setImageBitmap(image);
    		}catch(Exception e){
    			Log.v(getString(R.string.log),"MainActivity　setWallpaper() error");
    		}
    	}
    }

    //ボタンクリック時のリスナ
    private final OnClickListener oCLforShowButton = new OnClickListener() {
    	
        @Override
        public void onClick(View v) {
            switch(v.getId()){

            //位置情報取得
            case R.id.button_loc:
                startActivity(new Intent( getApplicationContext(), ReportActivity.class ));
                break;
                
            //実績ページに遷移
            case R.id.button_achievements:
                startActivity(new Intent( getApplicationContext(), AchievementsActivity.class ));
                break;

            //壁紙設定
            case R.id.button_image:
            	putImage();
            	break;
            }
        }
    };
    
    //壁紙設定
    void putImage(){
    	final String[] items = {"デフォルト", "画像フォルダから選ぶ"};
    	
    	new AlertDialog.Builder(MainActivity.this)
    	.setTitle("壁紙設定")
    	.setItems(items, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	        switch (item) {
    	        case 0:
    	        	//デフォルト"none"を設定
        			prefEditor = pref.edit();
        			prefEditor.putString(PREF_IMAGE, "none");
        			prefEditor.commit();
        			//画像設定
        			setWallpaper();
    	            break;
    	        case 1:
    	        	//ギャラリーを呼ぶ
                	Intent intentGetImage = new Intent(Intent.ACTION_PICK);
                	intentGetImage.setType("image/*");
                	startActivityForResult(intentGetImage, REQUEST_PICK_CONTACT);
    	            break;
    	        }
    	    }
    	})
    	.show();	
    }
    
    //ギャラリー戻り時のリスナ
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == REQUEST_PICK_CONTACT && resultCode == RESULT_OK) {
    		try {
    			//選択した画像のURIを設定
    			prefEditor = pref.edit();
    			prefEditor.putString(PREF_IMAGE, data.getData().toString());
    			prefEditor.commit();
    			//画像設定
    			setWallpaper();
    		} catch (Exception e) {
    			Log.v(getString(R.string.log),"MainActivity　onActivityResult() error");
    		}
    	}
    }


 }

