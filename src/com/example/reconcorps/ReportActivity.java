package com.example.reconcorps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
    
    //ボタン
	private View clickReturn;
	private View clickBattle;
	private View clickEscape;
	
    //アニメーション
    AlphaAnimation animation_alpha;
    
    //ガス量
    int gas;
	int need_gas;
	int used_gas;
	
    //レベル
    int level;
    
    //巨人の種類
    int titan;

	int titan_3;
	int titan_5;
	int titan_7;
	int titan_9;
	int titan_11;
	int titan_13;
	int titan_15;
	int kiko_3;
	int kiko_5;
	int kiko_7;
	int kiko_9;
	int kiko_11;
	int kiko_13; 
	int kiko_15;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
		Log.v(getString(R.string.log),"ReportActivity　onCreate() start");

		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

		//設定データ取得
    	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    	//アニメーション設定
		flashAnimation();
		
		//アニメーション適用
        /*
        TextView text_report = (TextView)findViewById( R.id.text_report );
        text_report.startAnimation( animation_alpha );
    	
      	//戻るボタン
        clickReturn = findViewById(R.id.button_return);
        clickReturn.setOnClickListener(oCLforShowButton);
        */

      	//戻るボタン
        clickBattle = findViewById(R.id.button_battle);
        clickBattle.setOnClickListener(oCLforShowButton);

      	//戻るボタン
        clickEscape = findViewById(R.id.button_escape);
        clickEscape.setOnClickListener(oCLforShowButton);

        
      	//位置情報取得
        getLocation();
        
   		Log.v(getString(R.string.log),"ReportActivity　onCreate() end");

    }

    
    //ボタンクリック時のリスナ
    private final OnClickListener oCLforShowButton = new OnClickListener() {
    	
        @Override
        public void onClick(View v) {
            switch(v.getId()){

            //戻るボタン
            /*
            case R.id.button_return:
                ReportActivity.this.finish();
                break;
              */  
            //たたかうボタン
            case R.id.button_battle:
            	
            	prefEditor = pref.edit();
            	if(titan==0){
        			prefEditor.putString(PREF_KIKO_15, String.valueOf(kiko_15 + 1));
            	}else if(titan==1){
        			prefEditor.putString(PREF_KIKO_13, String.valueOf(kiko_13 + 1));
            	}else if(titan==2){
        			prefEditor.putString(PREF_KIKO_11, String.valueOf(kiko_11 + 1));
            	}else if(titan==3){
        			prefEditor.putString(PREF_KIKO_9, String.valueOf(kiko_9 + 1));
            	}else if(titan==4){
        			prefEditor.putString(PREF_KIKO_7, String.valueOf(kiko_7 + 1));
            	}else if(titan==5){
        			prefEditor.putString(PREF_KIKO_5, String.valueOf(kiko_5 + 1));
            	}else if(titan==6){
        			prefEditor.putString(PREF_KIKO_3, String.valueOf(kiko_3 + 1));
            	}else if(titan==7){
        			prefEditor.putString(PREF_TITAN_15, String.valueOf(titan_15 + 1));
            	}else if(titan==8){
        			prefEditor.putString(PREF_TITAN_13, String.valueOf(titan_13 + 1));
            	}else if(titan==9){
        			prefEditor.putString(PREF_TITAN_11, String.valueOf(titan_11 + 1));
            	}else if(titan==10){
        			prefEditor.putString(PREF_TITAN_9, String.valueOf(titan_9 + 1));
            	}else if(titan==11){
        			prefEditor.putString(PREF_TITAN_7, String.valueOf(titan_7 + 1));
            	}else if(titan==12){
        			prefEditor.putString(PREF_TITAN_5, String.valueOf(titan_5 + 1));
            	}else if(titan==13){
        			prefEditor.putString(PREF_TITAN_3, String.valueOf(titan_3 + 1));
            	}
            	
    			prefEditor.putString(PREF_USED_GAS, String.valueOf(used_gas + need_gas));
    			prefEditor.commit();
                ReportActivity.this.finish();

                break;

            //にげるボタン
            case R.id.button_escape:
                ReportActivity.this.finish();
                break;
            }
        }
    };
    
    @Override
    public void finish() {
        super.finish();
 
        overridePendingTransition(0, 0);
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
			used_gas =Integer.parseInt(pref.getString(PREF_USED_GAS, "0"));
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
    	titan_3 = Integer.parseInt(pref.getString(PREF_TITAN_3, "0"));
    	titan_5 = Integer.parseInt(pref.getString(PREF_TITAN_5, "0"));
    	titan_7 = Integer.parseInt(pref.getString(PREF_TITAN_7, "0"));
    	titan_9 = Integer.parseInt(pref.getString(PREF_TITAN_9, "0"));
    	titan_11 = Integer.parseInt(pref.getString(PREF_TITAN_11, "0"));
    	titan_13 = Integer.parseInt(pref.getString(PREF_TITAN_13, "0"));
    	titan_15 = Integer.parseInt(pref.getString(PREF_TITAN_15, "0"));
    	kiko_3 = Integer.parseInt(pref.getString(PREF_KIKO_3, "0"));
    	kiko_5 = Integer.parseInt(pref.getString(PREF_KIKO_5, "0"));
    	kiko_7 = Integer.parseInt(pref.getString(PREF_KIKO_7, "0"));
    	kiko_9 = Integer.parseInt(pref.getString(PREF_KIKO_9, "0"));
    	kiko_11 = Integer.parseInt(pref.getString(PREF_KIKO_11, "0"));
    	kiko_13 = Integer.parseInt(pref.getString(PREF_KIKO_13, "0"));
    	kiko_15 = Integer.parseInt(pref.getString(PREF_KIKO_15, "0"));
    	
		//巨人と遭遇
    	StringBuffer sb = new StringBuffer("");
    	need_gas = 0;
		int[] random = {3,8,15,24,35,48,63,69,79,93,111,133,159,189};
		int ran = (int)(Math.random()*350);
		if(ran<random[0]){
			sb.append("15m級奇行種");
			need_gas = getNeedGas(15,false);
			titan = 0;
		}else if(ran<random[1]){
			sb.append("13m級奇行種");
			need_gas = getNeedGas(13,false);
			titan = 1;
		}else if(ran<random[2]){
			sb.append("11m級奇行種");
			need_gas = getNeedGas(11,false);
			titan = 2;
		}else if(ran<random[3]){
			sb.append("9m級奇行種");
			need_gas = getNeedGas(9,false);
			titan = 3;
		}else if(ran<random[4]){
			sb.append("7m級奇行種");
			need_gas = getNeedGas(7,false);
			titan = 4;
		}else if(ran<random[5]){
			sb.append("5m級奇行種");
			need_gas = getNeedGas(5,false);
			titan = 5;
		}else if(ran<random[6]){
			sb.append("3m級奇行種");
			need_gas = getNeedGas(3,false);
			titan = 6;
		}else if(ran<random[7]){
			sb.append("15m級巨人");
			need_gas = getNeedGas(15,true);
			titan = 7;
		}else if(ran<random[8]){
			sb.append("13m級巨人");
			need_gas = getNeedGas(13,true);
			titan = 8;
		}else if(ran<random[9]){
			sb.append("11m級巨人");
			need_gas = getNeedGas(11,true);
			titan = 9;
		}else if(ran<random[10]){
			sb.append("9m級巨人");
			need_gas = getNeedGas(9,true);
			titan = 10;
		}else if(ran<random[11]){
			sb.append("7m級巨人");
			need_gas = getNeedGas(7,true);
			titan = 11;
		}else if(ran<random[12]){
			sb.append("5m級巨人");
			need_gas = getNeedGas(5,true);
			titan = 12;
		}else if(ran<random[13]){
			sb.append("3m級巨人");
			need_gas = getNeedGas(3,true);
			titan = 13;
		}else{
			sb.append("報告しました");
			titan = 99;
		}

    	//アニメーション終了
		animation_alpha.cancel();
		
		/*
		//テキスト編集
		TextView text_lat = (TextView)findViewById( R.id.text_report );
		text_lat.setText(sb.toString());

		TextView text_gas = (TextView)findViewById( R.id.text_gas );
		text_gas.setText("ガス残量" + String.valueOf(gas) + " 必要ガス量" + String.valueOf(need_gas));
		Log.v(getString(R.string.log),"ReportActivity　showResult() gas  " + gas);
		Log.v(getString(R.string.log),"ReportActivity　showResult() needGas  " + need_gas);
*/

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
