package com.example.reconcorps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

public class ReportActivity extends Activity{
    
	// ロケーションマネージャー
    LocationManager mLocationManager;
    
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    
    static final String PREF_LAT = "pref_lat";
    static final String PREF_LONG = "pref_long";
    static final String PREF_DIST = "pref_dist";
    
    AlphaAnimation animation_alpha;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_report);
		
        animation_alpha = new AlphaAnimation( 1, 0 );
        animation_alpha.setDuration( 1000 );
        animation_alpha.setRepeatCount( Animation.INFINITE );
        
        TextView text_report = (TextView)findViewById( R.id.text_report );
        text_report.startAnimation( animation_alpha );
    	
		//設定データ取得
    	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    	
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
            
        	mLocationManager.removeUpdates(this);

        	/*
        	Toast.makeText(getApplicationContext(), "報告しています",
                    Toast.LENGTH_LONG).show();
        	*/
        	
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

			animation_alpha.cancel();
			TextView text_lat = (TextView)findViewById( R.id.text_report );

			int ran = (int)(Math.random()*10);
			if(ran<5){
				text_lat.setText("報告しました");
			}else{
				text_lat.setText("3m巨人に遭遇");
			}
	    	
			/*
            Toast.makeText(getApplicationContext(), "報告しました",
                    Toast.LENGTH_LONG).show();
                    */
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
