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
    static final String PREF_LEVEL = "pref_level";
    static final String PREF_USED_GAS = "pref_used_gas";
    static final String PREF_REPORT = "pref_report";
    static final String PREF_TITAN_3 = "pref_titan_3";
    static final String PREF_TITAN_5 = "pref_titan_5";
    static final String PREF_TITAN_7 = "pref_titan_7";
    static final String PREF_TITAN_9 = "pref_titan_9";
    static final String PREF_TITAN_11 = "pref_titan_11";
    static final String PREF_TITAN_13 = "pref_titan_13";
    static final String PREF_TITAN_15 = "pref_titan_15";
    static final String PREF_KIKO_3 = "pref_kiko_3";
    static final String PREF_KIKO_5 = "pref_kiko_5";
    static final String PREF_KIKO_7 = "pref_kiko_7";
    static final String PREF_KIKO_9 = "pref_kiko_9";
    static final String PREF_KIKO_11 = "pref_kiko_11";
    static final String PREF_KIKO_13 = "pref_kiko_13";
    static final String PREF_KIKO_15 = "pref_kiko_15";

    // ロケーションマネージャー
    LocationManager mLocationManager;

    //設定ファイル関連
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    
    //アニメーション
    AlphaAnimation animation_alpha;
    
    //ガス量
    int gas;
    
    //レベル
    int level;
    
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
   		//criteria.setAccuracy(Criteria.ACCURACY_COARSE);
   		// PowerRequirementを指定(低消費電力)
   		//criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setBearingRequired(false);	// 方位不要
		criteria.setSpeedRequired(false);	// 速度不要
		criteria.setAltitudeRequired(false);	// 高度不要
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

			//レベル
	    	level = 1;
	    	if(new_dist > 5f){
	   		    level = (int)(new_dist / 5f);
	    	}
        	prefEditor = pref.edit();
			prefEditor.putString(PREF_LEVEL, String.valueOf(level));
			prefEditor.commit();
			
			//ガス量
			int used_gas =Integer.parseInt(pref.getString(PREF_USED_GAS, "0"));
			gas = (int)(new_dist * 10.0f);
			gas = gas - used_gas;
			if(gas > 100){
				gas = 100;
			}
    		Log.v(getString(R.string.log),"ReportActivity　onLocationChanged() gas  " + gas);
    		Log.v(getString(R.string.log),"ReportActivity　onLocationChanged() new_dist * 10.0f  " + (new_dist * 10.0f));
			
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

    	//討伐数取得
    	int titan_3 = Integer.parseInt(pref.getString(PREF_TITAN_3, "0"));
    	int titan_5 = Integer.parseInt(pref.getString(PREF_TITAN_5, "0"));
    	int titan_7 = Integer.parseInt(pref.getString(PREF_TITAN_7, "0"));
    	int titan_9 = Integer.parseInt(pref.getString(PREF_TITAN_9, "0"));
    	int titan_11 = Integer.parseInt(pref.getString(PREF_TITAN_11, "0"));
    	int titan_13 = Integer.parseInt(pref.getString(PREF_TITAN_13, "0"));
    	int titan_15 = Integer.parseInt(pref.getString(PREF_TITAN_15, "0"));
    	int kiko_3 = Integer.parseInt(pref.getString(PREF_KIKO_3, "0"));
    	int kiko_5 = Integer.parseInt(pref.getString(PREF_KIKO_5, "0"));
    	int kiko_7 = Integer.parseInt(pref.getString(PREF_KIKO_7, "0"));
    	int kiko_9 = Integer.parseInt(pref.getString(PREF_KIKO_9, "0"));
    	int kiko_11 = Integer.parseInt(pref.getString(PREF_KIKO_11, "0"));
    	int kiko_13 = Integer.parseInt(pref.getString(PREF_KIKO_13, "0"));
    	int kiko_15 = Integer.parseInt(pref.getString(PREF_KIKO_15, "0"));
    	
		//巨人と遭遇
    	StringBuffer sb = new StringBuffer("");
    	int needGas = 0;
		int[] random = {3,8,15,24,35,48,63,69,79,93,111,133,159,189};
		int ran = (int)(Math.random()*350);
		if(ran<random[0]){
			sb.append("15m級奇行種");
			needGas = getNeedGas(15,false);
		}else if(ran<random[1]){
			sb.append("13m級奇行種");
			needGas = getNeedGas(13,false);
		}else if(ran<random[2]){
			sb.append("11m級奇行種");
			needGas = getNeedGas(11,false);
		}else if(ran<random[3]){
			sb.append("9m級奇行種");
			needGas = getNeedGas(9,false);
		}else if(ran<random[4]){
			sb.append("7m級奇行種");
			needGas = getNeedGas(7,false);
		}else if(ran<random[5]){
			sb.append("5m級奇行種");
			needGas = getNeedGas(5,false);
		}else if(ran<random[6]){
			sb.append("3m級奇行種");
			needGas = getNeedGas(3,false);
		}else if(ran<random[7]){
			sb.append("15m級巨人");
			needGas = getNeedGas(15,true);
		}else if(ran<random[8]){
			sb.append("13m級巨人");
			needGas = getNeedGas(13,true);
		}else if(ran<random[9]){
			sb.append("11m級巨人");
			needGas = getNeedGas(11,true);
		}else if(ran<random[10]){
			sb.append("9m級巨人");
			needGas = getNeedGas(9,true);
		}else if(ran<random[11]){
			sb.append("7m級巨人");
			needGas = getNeedGas(7,true);
		}else if(ran<random[12]){
			sb.append("5m級巨人");
			needGas = getNeedGas(5,true);
		}else if(ran<random[13]){
			sb.append("3m級巨人");
			needGas = getNeedGas(3,true);
		}else{
			sb.append("報告しました");
		}

    	//アニメーション終了
		animation_alpha.cancel();
		
		//テキスト編集
		TextView text_lat = (TextView)findViewById( R.id.text_report );
		text_lat.setText(sb.toString());

		TextView text_gas = (TextView)findViewById( R.id.text_gas );
		text_gas.setText("ガス残量" + String.valueOf(gas) + " 必要ガス量" + String.valueOf(needGas));
		Log.v(getString(R.string.log),"ReportActivity　showResult() gas  " + gas);
		Log.v(getString(R.string.log),"ReportActivity　showResult() needGas  " + needGas);


    }
    
    int getNeedGas(int titanSize,boolean normal){

    	int result = 0;
    	if(normal){
   			result = (100 * titanSize) / (level * 10);
    	}else{
   			result = (200 * titanSize) / (level * 10);
    	}
    	
		return result;
    	
    }
    


}
