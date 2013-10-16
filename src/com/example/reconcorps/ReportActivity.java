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
    
    //�萔
	static final String PREF_LAT = "pref_lat";
    static final String PREF_LONG = "pref_long";
    static final String PREF_DIST = "pref_dist";

    // ���P�[�V�����}�l�[�W���[
    LocationManager mLocationManager;

    //�ݒ�t�@�C���֘A
    SharedPreferences pref;
    SharedPreferences.Editor prefEditor;
    
    //�A�j���[�V����
    AlphaAnimation animation_alpha;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
		Log.v(getString(R.string.log),"ReportActivity�@onCreate() start");

		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        //�A�N�V�����o�[��\��
        ActionBar actionBar = getActionBar();
		actionBar.hide();

		//�ݒ�f�[�^�擾
    	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    	//�A�j���[�V�����ݒ�
		flashAnimation();
		
		//�A�j���[�V�����K�p
        TextView text_report = (TextView)findViewById( R.id.text_report );
        text_report.startAnimation( animation_alpha );
    	
      	//�ʒu���擾
        getLocation();
        
   		Log.v(getString(R.string.log),"ReportActivity�@onCreate() end");

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
   		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
   		// PowerRequirement���w��(�����d��)
   		criteria.setPowerRequirement(Criteria.POWER_LOW);
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
		//�A�j���[�V�����I��
		animation_alpha.cancel();
		
		//�e�L�X�g�\���ҏW
		TextView text_lat = (TextView)findViewById( R.id.text_report );

		int ran = (int)(Math.random()*10);
		if(ran<5){
			text_lat.setText("�񍐂��܂���");
		}else{
			text_lat.setText("3m���l�ɑ���");
		}
    }
    


}
