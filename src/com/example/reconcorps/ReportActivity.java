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
    
	// ���P�[�V�����}�l�[�W���[
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
    	
		//�ݒ�f�[�^�擾
    	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    	
      	// LocationManager���擾
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
        	Toast.makeText(getApplicationContext(), "�񍐂��Ă��܂�",
                    Toast.LENGTH_LONG).show();
        	*/
        	
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

    		Log.v(getString(R.string.log),"onLocationChanged " + old_lat);
    		Log.v(getString(R.string.log),"onLocationChanged " + old_long);
    		Log.v(getString(R.string.log),"onLocationChanged�@" + new_lat);
    		Log.v(getString(R.string.log),"onLocationChanged�@" + new_long);
    		Log.v(getString(R.string.log),"onLocationChanged�@" + old_dist);
    		Log.v(getString(R.string.log),"onLocationChanged�@" + new_dist);

        	prefEditor = pref.edit();
        	
			// savedCount��ۑ�
			prefEditor.putString(PREF_LAT, String.valueOf(new_lat));
			prefEditor.putString(PREF_LONG, String.valueOf(new_long));
			prefEditor.putString(PREF_DIST, String.valueOf(new_dist));
			// �Ō�commit
			prefEditor.commit();

			animation_alpha.cancel();
			TextView text_lat = (TextView)findViewById( R.id.text_report );

			int ran = (int)(Math.random()*10);
			if(ran<5){
				text_lat.setText("�񍐂��܂���");
			}else{
				text_lat.setText("3m���l�ɑ���");
			}
	    	
			/*
            Toast.makeText(getApplicationContext(), "�񍐂��܂���",
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
