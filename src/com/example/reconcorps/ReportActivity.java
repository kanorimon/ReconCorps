package com.example.reconcorps;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ReportActivity extends Activity{
    
    //定数
	static final String PREF_LAT = "pref_lat";
    static final String PREF_LONG = "pref_long";
    static final String PREF_DIST = "pref_dist";
    static final String PREF_UP_DIST = "pref_up_dist";
    static final String PREF_DATE = "pref_date";
    static final String PREF_COUNT = "pref_count";
    static final String PREF_LEVEL = "pref_level";
    static final String PREF_GAS_DIST = "pref_gas_dist";
    static final String PREF_HAVE_GAS = "pref_have_gas";
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
	ImageView image_report_now;
	View relative_return;
	View relative_titan;
	View relative_sogu;
	
    //アニメーション
    AlphaAnimation animation_alpha;
    
    //ガス量
	int need_gas;
	int have_gas;
	
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

    	//非表示
    	image_report_now = (ImageView)findViewById(R.id.image_report_now);
    	relative_return = findViewById(R.id.relative_return);
    	relative_titan = findViewById(R.id.relative_titan);
    	relative_sogu = findViewById(R.id.relative_sogu);
    	
    	relative_return.setVisibility(View.GONE);
    	relative_titan.setVisibility(View.GONE);
    	relative_sogu.setVisibility(View.GONE);
    	
    	//アニメーション設定
		flashAnimation();
		
		//アニメーション適用
        image_report_now.startAnimation( animation_alpha );
    	
      	//戻るボタン
        clickReturn = findViewById(R.id.button_return);
        clickReturn.setOnClickListener(oCLforShowButton);

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
            
            case R.id.button_return:
                ReportActivity.this.finish();
                startActivity(new Intent( getApplicationContext(), MainActivity.class ));
                overridePendingTransition(0, 0);
                break;
             
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
            	
    			prefEditor.putString(PREF_HAVE_GAS, String.valueOf(have_gas - need_gas));
    			prefEditor.commit();
                ReportActivity.this.finish();
                startActivity(new Intent( getApplicationContext(), ResultActivity.class ));
                overridePendingTransition(0, 0);

                break;

            //にげるボタン
            case R.id.button_escape:
                ReportActivity.this.finish();
                startActivity(new Intent( getApplicationContext(), MainActivity.class ));
                overridePendingTransition(0, 0);
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

			//報告回数
			int cnt = Integer.parseInt(pref.getString(PREF_COUNT, "0"));
        	prefEditor = pref.edit();
			prefEditor.putString(PREF_COUNT, String.valueOf(cnt + 1));
			prefEditor.commit();
			
			//レベル
	    	level = 1;
	    	float exp[] = {
	    			0f,
	    			10000f,
	    			30000f,
	    			60000f,
	    			100000f,
	    			150000f,
	    			210000f,
	    			280000f,
	    			360000f,
	    			450000f,
	    			550000f,
	    			660000f,
	    			780000f,
	    			910000f,
	    			1050000f,
	    			1200000f,
	    			1360000f,
	    			1530000f,
	    			1710000f,
	    			1900000f,
	    			2100000f,
	    			2310000f,
	    			2530000f,
	    			2760000f,
	    			3000000f,
	    			3250000f,
	    			3510000f,
	    			3780000f,
	    			4060000f,
	    			4350000f,
	    			4650000f,
	    			4960000f,
	    			5280000f,
	    			5610000f,
	    			5950000f,
	    			6300000f,
	    			6660000f,
	    			7030000f,
	    			7410000f,
	    			7800000f,
	    			8200000f,
	    			8610000f,
	    			9030000f,
	    			9460000f,
	    			9900000f,
	    			10350000f,
	    			10810000f,
	    			11280000f,
	    			11760000f,
	    			12250000f,
	    			12750000f,
	    			13260000f,
	    			13780000f,
	    			14310000f,
	    			14850000f,
	    			15400000f,
	    			15960000f,
	    			16530000f,
	    			17110000f,
	    			17700000f,
	    			18300000f,
	    			18910000f,
	    			19530000f,
	    			20160000f,
	    			20800000f,
	    			21450000f,
	    			22110000f,
	    			22780000f,
	    			23460000f,
	    			24150000f,
	    			24850000f,
	    			25560000f,
	    			26280000f,
	    			27010000f,
	    			27750000f,
	    			28500000f,
	    			29260000f,
	    			30030000f,
	    			30810000f,
	    			31600000f,
	    			32400000f,
	    			33210000f,
	    			34030000f,
	    			34860000f,
	    			35700000f,
	    			36550000f,
	    			37410000f,
	    			38280000f,
	    			39160000f,
	    			40050000f,
	    			40950000f,
	    			41860000f,
	    			42780000f,
	    			43710000f,
	    			44650000f,
	    			45600000f,
	    			46560000f,
	    			47530000f,
	    			48510000f,
	    			49500000f,
	    			50500000
	    			};
	    	
	    	while(true){
		    	if(level > 99){
		    		break;
		    	}
		    	if(new_dist < exp[level] ){
		   		    break;
		    	}
		    	level++;
	    	}
	    	
        	prefEditor = pref.edit();
			prefEditor.putString(PREF_LEVEL, String.valueOf(level));
			prefEditor.commit();
			
			//残り距離
	    	if(level > 99){
				prefEditor = pref.edit();
				prefEditor.putString(PREF_UP_DIST, String.valueOf(99999));
				prefEditor.commit();
	    	}else{
	    		prefEditor = pref.edit();
	    		prefEditor.putString(PREF_UP_DIST, String.valueOf(exp[level] - new_dist));
	    		prefEditor.commit();
	    	}
			
			//ガス量
			float gas_dist =Float.parseFloat(pref.getString(PREF_GAS_DIST, "0"));
			have_gas =Integer.parseInt(pref.getString(PREF_HAVE_GAS, "0"));
			if(have_gas < 100){
				int gas = (int)((new_dist-gas_dist) / 10f);
				if(gas > 0){
					have_gas = have_gas + gas;
		        	prefEditor = pref.edit();
					prefEditor.putString(PREF_GAS_DIST, String.valueOf(new_dist));
					prefEditor.commit();
				}
			}
			if(have_gas > 100){
				have_gas = 100;
			}
        	prefEditor = pref.edit();
			prefEditor.putString(PREF_HAVE_GAS, String.valueOf(have_gas));
			prefEditor.commit();
			
			//報告時間
        	prefEditor = pref.edit();
			prefEditor.putString(PREF_DATE, (String)DateFormat.format("yyyy/MM/dd kk:mm:ss", Calendar.getInstance()));
			prefEditor.commit();

			Log.v(getString(R.string.log),"ReportActivity　onLocationChanged() have_gas  " + have_gas);
			
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
    	
    	//
    	ImageView image_titan_val = (ImageView)findViewById(R.id.image_titan_val);
    	
		//巨人と遭遇
    	StringBuffer sb = new StringBuffer("");
    	need_gas = 0;
		int[] random = {3,8,15,24,35,48,63,69,79,93,111,133,159,189};
		int ran = (int)(Math.random()*350);
		if(ran<random[0]){
			sb.append("15m級奇行種");
			need_gas = getNeedGas(15,false);
			titan = 0;
			setTitan(15);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_kikoshu));
			image_titan_val.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.report_kikoshu));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[1]){
			sb.append("13m級奇行種");
			need_gas = getNeedGas(13,false);
			titan = 1;
			setTitan(13);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_kikoshu));
			image_titan_val.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.report_kikoshu));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[2]){
			sb.append("11m級奇行種");
			need_gas = getNeedGas(11,false);
			titan = 2;
			setTitan(11);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_kikoshu));
			image_titan_val.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.report_kikoshu));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[3]){
			sb.append("9m級奇行種");
			need_gas = getNeedGas(9,false);
			titan = 3;
			setTitan(9);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_kikoshu));
			image_titan_val.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.report_kikoshu));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[4]){
			sb.append("7m級奇行種");
			need_gas = getNeedGas(7,false);
			titan = 4;
			setTitan(7);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_kikoshu));
			image_titan_val.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.report_kikoshu));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[5]){
			sb.append("5m級奇行種");
			need_gas = getNeedGas(5,false);
			titan = 5;
			setTitan(5);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_kikoshu));
			image_titan_val.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.report_kikoshu));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[6]){
			sb.append("3m級奇行種");
			need_gas = getNeedGas(3,false);
			titan = 6;
			setTitan(3);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_kikoshu));
			image_titan_val.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.report_kikoshu));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[7]){
			sb.append("15m級巨人");
			need_gas = getNeedGas(15,true);
			titan = 7;
			setTitan(15);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_titan));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[8]){
			sb.append("13m級巨人");
			need_gas = getNeedGas(13,true);
			titan = 8;
			setTitan(13);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_titan));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[9]){
			sb.append("11m級巨人");
			need_gas = getNeedGas(11,true);
			titan = 9;
			setTitan(11);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_titan));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[10]){
			sb.append("9m級巨人");
			need_gas = getNeedGas(9,true);
			titan = 10;
			setTitan(9);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_titan));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[11]){
			sb.append("7m級巨人");
			need_gas = getNeedGas(7,true);
			titan = 11;
			setTitan(7);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_titan));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[12]){
			sb.append("5m級巨人");
			need_gas = getNeedGas(5,true);
			titan = 12;
			setTitan(5);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_titan));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[13]){
			sb.append("3m級巨人");
			need_gas = getNeedGas(3,true);
			titan = 13;
			setTitan(3);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_titan));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else{
			sb.append("報告しました");
			titan = 99;
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.report_end));
			relative_return.setVisibility(View.VISIBLE);
		}


    	//ガス量
		if(need_gas > have_gas ){

			Button button_battle = (Button)findViewById( R.id.button_battle);
			button_battle.setBackground(getResources().getDrawable(R.drawable.report_battle_ng));
			button_battle.setEnabled(false);
			
		}

	    	boolean istop = setNumDrawable((ImageView)findViewById( R.id.image_needgas_1),need_gas / 1000,true);
	    	istop = setNumDrawable((ImageView)findViewById( R.id.image_needgas_2),(need_gas % 1000) / 100,istop);
	    	setNumDrawable((ImageView)findViewById( R.id.image_needgas_3),(need_gas % 100) / 10,istop);
	    	setNumDrawable((ImageView)findViewById( R.id.image_needgas_4),need_gas % 10,false);
	    	
	    	istop = setNumDrawable((ImageView)findViewById( R.id.image_usegas_1),have_gas / 100,true);
	    	setNumDrawable((ImageView)findViewById( R.id.image_usegas_2),(have_gas % 100) / 10,istop);
	    	setNumDrawable((ImageView)findViewById( R.id.image_usegas_3),have_gas % 10,false);
		
		
    	//アニメーション終了
		animation_alpha.cancel();

    }

    void setTitan(int size){
    	setNumDrawable((ImageView)findViewById( R.id.image_titan_1),size / 10,true);
    	setNumDrawable((ImageView)findViewById( R.id.image_titan_2),size % 10,false);
 	
    }

    boolean setNumDrawable(ImageView imv, int num, boolean istop){
    	
    	boolean result = false;
    	if(istop && num==0){
    		imv.setVisibility(View.GONE);  		
    		result = true;
    	}else{
    		imv.setImageBitmap(BitmapFactory.decodeResource(getResources(), getuNumDrawable(num))); 
    	}
    	
    	return result;

    	
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
