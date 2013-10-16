package com.example.reconcorps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

public class ReportActivity extends Activity{
    
    //定数
	static final String PREF_LAT = "pref_lat";
    static final String PREF_LONG = "pref_long";
    static final String PREF_DIST = "pref_dist";

    // ロケーションマネージャー
    LocationManager mLocationManager;

    //設定ファイル関連
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    
    //アニメーション
    AlphaAnimation animation_alpha;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
		Log.v(getString(R.string.log),"ReportActivity　onCreate() start");

		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        //アクションバー非表示
        ActionBar actionBar = getActionBar();
		actionBar.hide();

		//設定データ取得
    	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    	//アニメーション設定
		flashAnimation();
		
		//アニメーション適用
        TextView text_report = (TextView)findViewById( R.id.text_report );
        text_report.startAnimation( animation_alpha );
    	
      	//位置情報取得
        getLocation();
        
   		Log.v(getString(R.string.log),"ReportActivity　onCreate() end");

    }

    //点滅アニメーション設定
    private void flashAnimation(){
        animation_alpha = new AlphaAnimation( 1, 0 );
        animation_alpha.setDuration( 1000 );
        animation_alpha.setRepeatCount( Animation.INFINITE );
    }
    
    //位置情報取得
    private void getLocation(){
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
   		mLocationManager.requestLocationUpdates(provider, 0, 0, listener);
    }
    
    //位置情報取得時のリスナ
    private final LocationListener listener = new LocationListener(){

        @Override
        public void onLocationChanged(Location location) {
        	//位置情報取得リスナを無効にする
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

    		Log.v(getString(R.string.log),"ReportActivity　onLocationChanged() old_lat  " + old_lat);
    		Log.v(getString(R.string.log),"ReportActivity　onLocationChanged() old_long " + old_long);
    		Log.v(getString(R.string.log),"ReportActivity　onLocationChanged()　new_lat  " + new_lat);
    		Log.v(getString(R.string.log),"ReportActivity　onLocationChanged()　new_long " + new_long);
    		Log.v(getString(R.string.log),"ReportActivity　onLocationChanged()　old_dist " + old_dist);
    		Log.v(getString(R.string.log),"ReportActivity　onLocationChanged()　new_dist " + new_dist);

    		//位置情報保存
        	prefEditor = pref.edit();
			prefEditor.putString(PREF_LAT, String.valueOf(new_lat));
			prefEditor.putString(PREF_LONG, String.valueOf(new_long));
			prefEditor.putString(PREF_DIST, String.valueOf(new_dist));
			prefEditor.commit();

			showResult();

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
    
    private void showResult(){
		//アニメーション終了
		animation_alpha.cancel();
		
		//テキスト表示編集
		TextView text_lat = (TextView)findViewById( R.id.text_report );

		int ran = (int)(Math.random()*10);
		if(ran<5){
			text_lat.setText("報告しました");
		}else{
			text_lat.setText("3m巨人に遭遇");
		}
    }
    


}
