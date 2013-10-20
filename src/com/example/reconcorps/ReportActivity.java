package com.example.reconcorps;

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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ReportActivity extends Activity{
    
    //�萔
	static final String PREF_LAT = "pref_lat";
    static final String PREF_LONG = "pref_long";
    static final String PREF_DIST = "pref_dist";
    static final String PREF_UP_DIST = "pref_up_dist";
    static final String PREF_COUNT = "pref_count";
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

    // ���P�[�V�����}�l�[�W���[
    LocationManager mLocationManager;

    //�ݒ�t�@�C���֘A
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    
    //�{�^��
	private View clickReturn;
	private View clickBattle;
	private View clickEscape;
	ImageView image_report_now;
	View relative_return;
	View relative_titan;
	View relative_sogu;
	
    //�A�j���[�V����
    AlphaAnimation animation_alpha;
    
    //�K�X��
    int gas;
	int need_gas;
	int used_gas;
	
    //���x��
    int level;
    
    //���l�̎��
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
		Log.v(getString(R.string.log),"ReportActivity�@onCreate() start");

		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

		//�ݒ�f�[�^�擾
    	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    	//��\��
    	image_report_now = (ImageView)findViewById(R.id.image_report_now);
    	relative_return = findViewById(R.id.relative_return);
    	relative_titan = findViewById(R.id.relative_titan);
    	relative_sogu = findViewById(R.id.relative_sogu);
    	
    	relative_return.setVisibility(View.GONE);
    	relative_titan.setVisibility(View.GONE);
    	relative_sogu.setVisibility(View.GONE);
    	
    	//�A�j���[�V�����ݒ�
		flashAnimation();
		
		//�A�j���[�V�����K�p
        image_report_now.startAnimation( animation_alpha );
    	
      	//�߂�{�^��
        clickReturn = findViewById(R.id.button_return);
        clickReturn.setOnClickListener(oCLforShowButton);

      	//�߂�{�^��
        clickBattle = findViewById(R.id.button_battle);
        clickBattle.setOnClickListener(oCLforShowButton);

      	//�߂�{�^��
        clickEscape = findViewById(R.id.button_escape);
        clickEscape.setOnClickListener(oCLforShowButton);

        
      	//�ʒu���擾
        getLocation();
        
   		Log.v(getString(R.string.log),"ReportActivity�@onCreate() end");

    }

    
    //�{�^���N���b�N���̃��X�i
    private final OnClickListener oCLforShowButton = new OnClickListener() {
    	
        @Override
        public void onClick(View v) {
            switch(v.getId()){

            //�߂�{�^��
            
            case R.id.button_return:
                ReportActivity.this.finish();
                startActivity(new Intent( getApplicationContext(), MainActivity.class ));
                overridePendingTransition(0, 0);
                break;
             
            //���������{�^��
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
                startActivity(new Intent( getApplicationContext(), MainActivity.class ));
                overridePendingTransition(0, 0);

                break;

            //�ɂ���{�^��
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
    
    
    //�_�ŃA�j���[�V�����ݒ�
    private void flashAnimation(){
        animation_alpha = new AlphaAnimation( 1, 0 );
        animation_alpha.setDuration( 1000 );
        animation_alpha.setRepeatCount( Animation.INFINITE );
    }
    
    //�ʒu���擾
    private void getLocation(){
   		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
   		// Criteria�I�u�W�F�N�g�𐶐�
   		Criteria criteria = new Criteria();
   		// Accuracy���w��(�ᐸ�x)
   		//criteria.setAccuracy(Criteria.ACCURACY_COARSE);
   		// PowerRequirement���w��(�����d��)
   		//criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setBearingRequired(false);	// ���ʕs�v
		criteria.setSpeedRequired(false);	// ���x�s�v
		criteria.setAltitudeRequired(false);	// ���x�s�v
   		// ���P�[�V�����v���o�C�_�̎擾
   		String provider = mLocationManager.getBestProvider(criteria, true);
   		// LocationListener��o�^
   		mLocationManager.requestLocationUpdates(provider, 0, 0, listener);
    }
    
    //�ʒu���擾���̃��X�i
    private final LocationListener listener = new LocationListener(){

        @Override
        public void onLocationChanged(Location location) {
        	//�ʒu���擾���X�i�𖳌��ɂ���
        	mLocationManager.removeUpdates(this);

        	//���|�C���g�𒊏o
        	Double old_lat =Double.parseDouble(pref.getString(PREF_LAT, "0"));
        	Double old_long =Double.parseDouble(pref.getString(PREF_LONG, "0"));
        	
        	//�������擾
        	Double new_lat = location.getLatitude();
        	Double new_long = location.getLongitude();
        	
        	//����
        	float old_dist =Float.parseFloat(pref.getString(PREF_DIST, "0"));
        	float new_dist = 0f;
        	float[] result = new float[3];
        	Location.distanceBetween(old_lat, old_long, new_lat, new_long, result);
        	
        	if(old_lat!=0d && old_long!=0d){
        		new_dist = old_dist + result[0];
        	}

    		Log.v(getString(R.string.log),"ReportActivity�@onLocationChanged() old_lat  " + old_lat);
    		Log.v(getString(R.string.log),"ReportActivity�@onLocationChanged() old_long " + old_long);
    		Log.v(getString(R.string.log),"ReportActivity�@onLocationChanged()�@new_lat  " + new_lat);
    		Log.v(getString(R.string.log),"ReportActivity�@onLocationChanged()�@new_long " + new_long);
    		Log.v(getString(R.string.log),"ReportActivity�@onLocationChanged()�@old_dist " + old_dist);
    		Log.v(getString(R.string.log),"ReportActivity�@onLocationChanged()�@new_dist " + new_dist);

    		//�ʒu���ۑ�
        	prefEditor = pref.edit();
			prefEditor.putString(PREF_LAT, String.valueOf(new_lat));
			prefEditor.putString(PREF_LONG, String.valueOf(new_long));
			prefEditor.putString(PREF_DIST, String.valueOf(new_dist));
			prefEditor.commit();

			//�񍐉�
			int cnt = Integer.parseInt(pref.getString(PREF_COUNT, "0"));
        	prefEditor = pref.edit();
			prefEditor.putString(PREF_COUNT, String.valueOf(cnt + 1));
			prefEditor.commit();
			
			//���x��
			float keisu = 5000f;
	    	level = 1;
	    	if(new_dist > keisu){
	   		    level = (int)(new_dist / keisu);
	    	}
        	prefEditor = pref.edit();
			prefEditor.putString(PREF_LEVEL, String.valueOf(level));
			prefEditor.commit();
			
			//�c�苗��
        	prefEditor = pref.edit();
			prefEditor.putString(PREF_UP_DIST, String.valueOf(((level+1)*keisu) - (new_dist)));
			prefEditor.commit();
			
			//�K�X��
			used_gas =Integer.parseInt(pref.getString(PREF_USED_GAS, "0"));
			gas = (int)(new_dist / 10f);
			gas = gas - used_gas;
			if(gas > 100){
				gas = 100;
			}

			Log.v(getString(R.string.log),"ReportActivity�@onLocationChanged() gas  " + gas);
    		Log.v(getString(R.string.log),"ReportActivity�@onLocationChanged() new_dist * 10.0f  " + (new_dist * 10.0f));
			
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

    	//�������擾
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
    	
		//���l�Ƒ���
    	StringBuffer sb = new StringBuffer("");
    	need_gas = 0;
		int[] random = {3,8,15,24,35,48,63,69,79,93,111,133,159,189};
		int ran = (int)(Math.random()*350);
		if(ran<random[0]){
			sb.append("15m����s��");
			need_gas = getNeedGas(15,false);
			titan = 0;
			setTitan(15);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_kikoshu));
			image_titan_val.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.report_kikoshu));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[1]){
			sb.append("13m����s��");
			need_gas = getNeedGas(13,false);
			titan = 1;
			setTitan(13);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_kikoshu));
			image_titan_val.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.report_kikoshu));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[2]){
			sb.append("11m����s��");
			need_gas = getNeedGas(11,false);
			titan = 2;
			setTitan(11);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_kikoshu));
			image_titan_val.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.report_kikoshu));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[3]){
			sb.append("9m����s��");
			need_gas = getNeedGas(9,false);
			titan = 3;
			setTitan(9);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_kikoshu));
			image_titan_val.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.report_kikoshu));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[4]){
			sb.append("7m����s��");
			need_gas = getNeedGas(7,false);
			titan = 4;
			setTitan(7);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_kikoshu));
			image_titan_val.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.report_kikoshu));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[5]){
			sb.append("5m����s��");
			need_gas = getNeedGas(5,false);
			titan = 5;
			setTitan(5);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_kikoshu));
			image_titan_val.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.report_kikoshu));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[6]){
			sb.append("3m����s��");
			need_gas = getNeedGas(3,false);
			titan = 6;
			setTitan(3);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_kikoshu));
			image_titan_val.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.report_kikoshu));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[7]){
			sb.append("15m�����l");
			need_gas = getNeedGas(15,true);
			titan = 7;
			setTitan(15);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_titan));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[8]){
			sb.append("13m�����l");
			need_gas = getNeedGas(13,true);
			titan = 8;
			setTitan(13);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_titan));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[9]){
			sb.append("11m�����l");
			need_gas = getNeedGas(11,true);
			titan = 9;
			setTitan(11);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_titan));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[10]){
			sb.append("9m�����l");
			need_gas = getNeedGas(9,true);
			titan = 10;
			setTitan(9);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_titan));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[11]){
			sb.append("7m�����l");
			need_gas = getNeedGas(7,true);
			titan = 11;
			setTitan(7);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_titan));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[12]){
			sb.append("5m�����l");
			need_gas = getNeedGas(5,true);
			titan = 12;
			setTitan(5);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_titan));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else if(ran<random[13]){
			sb.append("3m�����l");
			need_gas = getNeedGas(3,true);
			titan = 13;
			setTitan(3);
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_titan));
			relative_titan.setVisibility(View.VISIBLE);
			relative_sogu.setVisibility(View.VISIBLE);
		}else{
			sb.append("�񍐂��܂���");
			titan = 99;
			image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.report_end));
			relative_return.setVisibility(View.VISIBLE);
		}


    	//�K�X��
		if(need_gas > gas ){

			Button button_battle = (Button)findViewById( R.id.button_battle);
			button_battle.setBackground(getResources().getDrawable(R.drawable.report_battle_ng));
			button_battle.setEnabled(false);
			
		}

	    	boolean istop = setNumDrawable((ImageView)findViewById( R.id.image_needgas_1),need_gas / 1000,true);
	    	istop = setNumDrawable((ImageView)findViewById( R.id.image_needgas_2),(need_gas % 1000) / 100,istop);
	    	setNumDrawable((ImageView)findViewById( R.id.image_needgas_3),(need_gas % 100) / 10,istop);
	    	setNumDrawable((ImageView)findViewById( R.id.image_needgas_4),need_gas % 10,false);
	    	
	    	istop = setNumDrawable((ImageView)findViewById( R.id.image_usegas_1),gas / 100,true);
	    	setNumDrawable((ImageView)findViewById( R.id.image_usegas_2),(gas % 100) / 10,istop);
	    	setNumDrawable((ImageView)findViewById( R.id.image_usegas_3),gas % 10,false);
		
		
    	//�A�j���[�V�����I��
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
