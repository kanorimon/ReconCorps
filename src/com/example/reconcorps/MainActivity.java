package com.example.reconcorps;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends Activity {

	//設定
	private View clickSetting;
	private View clickSetting2;
	private View clickSetting3;
    private ImageView imgv;
    
    static final int REQUEST_PICK_CONTACT = 99;
    static final String PREF_IMAGE = "pref_image";
    static final String PREF_LAT = "pref_lat";
    static final String PREF_LONG = "pref_long";
    static final String PREF_DIST = "pref_dist";
    
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
	
	// ロケーションマネージャー
    LocationManager mLocationManager;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

		Log.v(getString(R.string.log),"MainActivity　onCreate start");

		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
		actionBar.hide();
        
      	//設定ボタンイベント
        clickSetting = findViewById(R.id.button_setting);
        clickSetting.setOnClickListener(oCLforShowButton);

      	//設定ボタンイベント
        clickSetting2 = findViewById(R.id.button_image);
        clickSetting2.setOnClickListener(oCLforShowButton);

      	//位置取得ボタンイベント
        clickSetting3 = findViewById(R.id.button_loc);
        clickSetting3.setOnClickListener(oCLforShowButton);
        
		Log.v(getString(R.string.log),"MainActivity　onCreate end");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public void onStart(){
    	super.onStart();
    	setText();
   }
    
    @Override
    public void onRestart(){
    	super.onRestart();
    	setText();
    }
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
	    imgv.setBackground(null);
	    clickSetting.setOnClickListener(null);
	    System.gc();
    }
    
    //画面表示の設定
    private void setText(){
		Log.v(getString(R.string.log),"MainActivity　setText start");
		
		//設定データ取得
    	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    	
    	//画面部品取得
    	imgv = (ImageView)findViewById(R.id.imageView1);

		//キャラクター画像設定

    	Log.v(getString(R.string.log),pref.getString(PREF_IMAGE, "none"));

    	if(pref.getString(PREF_IMAGE, "none").equals("none")){
    	    imgv.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.levi));	
    	}else{
    		try{
    			BufferedInputStream inputStream = new BufferedInputStream(getContentResolver().openInputStream(Uri.parse(pref.getString(PREF_IMAGE, null))));
    			Bitmap image = BitmapFactory.decodeStream(inputStream);
    			imgv.setImageBitmap(image);
    		}catch(Exception e){
    			Log.v(getString(R.string.log),"error");
    		}
    	}
    	
    	//
    	TextView text_lat = (TextView)findViewById( R.id.text_lat );
    	TextView text_long = (TextView)findViewById( R.id.text_long );
    	TextView text_dist = (TextView)findViewById( R.id.text_dist );
    	
    	text_lat.setText(pref.getString(PREF_LAT, "0"));
    	text_long.setText(pref.getString(PREF_LONG, "0"));
    	text_dist.setText(pref.getString(PREF_DIST, "999"));
    }

    //設定ボタンクリック時のリスナ
    private final OnClickListener oCLforShowButton = new OnClickListener() {
    	
        @Override
        public void onClick(View v) {
            switch(v.getId()){

            case R.id.button_setting:
            	Intent intent = new Intent( getApplicationContext(), SettingMenuActivity.class );
                startActivity( intent );
                break;

            case R.id.button_loc:
                getLocation();
                break;
                
            case R.id.button_image:
            	// インテント設定
            	Intent intent2 = new Intent(Intent.ACTION_PICK);
            	// とりあえずストレージ内の全イメージ画像を対象
            	intent2.setType("image/*");
            	// ギャラリー表示
            	startActivityForResult(intent2, REQUEST_PICK_CONTACT);
            	break;
            }
        }
    };
    
    /**
     * 標準ギャラリーから戻り時に呼ばれるイベント
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == REQUEST_PICK_CONTACT && resultCode == RESULT_OK) {
    		try {
    			BufferedInputStream inputStream = new BufferedInputStream(getContentResolver().openInputStream(data.getData()));
    			Bitmap image = BitmapFactory.decodeStream(inputStream);
    			imgv.setImageBitmap(image);
    			
    			Log.v(getString(R.string.log),data.getData().toString());
    			
    			prefEditor = pref.edit();
    			// savedCountを保存
    			prefEditor.putString(PREF_IMAGE, data.getData().toString());
    			// 最後commit
    			prefEditor.commit();
    			
    		} catch (Exception e) {
 
    		}
    	}
    }

    
    private void getLocation(){
        // 処理待ち中に行う処理をここに実装

    	// LocationManagerを取得
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    
		// Criteriaオブジェクトを生成
		Criteria criteria = new Criteria();
    
		// Accuracyを指定(低精度)
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		 
		// PowerRequirementを指定(低消費電力)
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		 
		// ロケーションプロバイダの取得
		String provider = mLocationManager.getBestProvider(criteria, true);

		// LocationListenerを登録
		mLocationManager.requestLocationUpdates(provider,
		        0,          // 10-second interval.
		        0,             // 10 meters.
		        listener);

    }
     

    private final LocationListener listener = new LocationListener(){

        @Override
        public void onLocationChanged(Location location) {
            
        	Toast.makeText(getApplicationContext(), "報告しています",
                    Toast.LENGTH_LONG).show();
        	
        	mLocationManager.removeUpdates(this);
        	
        	//旧ポイントを抽出
        	Double old_lat =Double.parseDouble(pref.getString(PREF_LAT, "0"));
        	Double old_long =Double.parseDouble(pref.getString(PREF_LONG, "0"));
        	
        	//差分を取得
        	Double new_lat = location.getLatitude();
        	Double new_long = location.getLongitude();
        	
        	//距離
        	float old_dist =Float.parseFloat(pref.getString(PREF_DIST, "0"));
        	float new_dist = 0f;
        	float[] result = new float[3];
        	Location.distanceBetween(old_lat, old_long, new_lat, new_long, result);
        	
        	if(old_lat!=0d && old_long!=0d){
        		new_dist = old_dist + result[0];
        	}

    		Log.v(getString(R.string.log),"onLocationChanged " + old_lat);
    		Log.v(getString(R.string.log),"onLocationChanged " + old_long);
    		Log.v(getString(R.string.log),"onLocationChanged　" + new_lat);
    		Log.v(getString(R.string.log),"onLocationChanged　" + new_long);
    		Log.v(getString(R.string.log),"onLocationChanged　" + old_dist);
    		Log.v(getString(R.string.log),"onLocationChanged　" + new_dist);

        	prefEditor = pref.edit();
        	
			// savedCountを保存
			prefEditor.putString(PREF_LAT, String.valueOf(new_lat));
			prefEditor.putString(PREF_LONG, String.valueOf(new_long));
			prefEditor.putString(PREF_DIST, String.valueOf(new_dist));
			// 最後commit
			prefEditor.commit();

            Toast.makeText(getApplicationContext(), "報告しました",
                    Toast.LENGTH_LONG).show();
        }

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
    };
    

 }

