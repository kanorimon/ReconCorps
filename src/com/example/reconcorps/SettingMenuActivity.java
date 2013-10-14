package com.example.reconcorps;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.util.Log;

public class SettingMenuActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
		Log.v(getString(R.string.log),"MenuActivity　start");
	    
		super.onCreate(savedInstanceState);
		
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingPreferencesFragment()).commit();

        Log.v(getString(R.string.log),"MenuActivity　end");
	}
	    
    public static class SettingPreferencesFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState){
    		Log.v(getString(R.string.log),"MenuActivity　SettingPreferencesFragment start");

        	super.onCreate(savedInstanceState);
        
            addPreferencesFromResource(R.xml.pref);

            //キャラクターのPreferenceオブジェクトを得て、Summaryに項目値を設定
            ListPreference genderPref = (ListPreference)findPreference(getString(R.string.char_pref));
            genderPref.setSummary(genderPref.getEntry());

            Log.v(getString(R.string.log),"MenuActivity　SettingPreferencesFragment end");

        }
        
        private OnSharedPreferenceChangeListener onPreferenceChangeListenter = new OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals(getString(R.string.char_pref))) {
                    ListPreference pref = (ListPreference)findPreference(key);
                    pref.setSummary(pref.getEntry());
                }
            }
        };
        
        @Override
        public void onResume() {
            super.onResume();
            SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
            sharedPreferences.registerOnSharedPreferenceChangeListener(onPreferenceChangeListenter);
        }
         
        @Override
        public void onPause() {
            super.onPause();
            SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(onPreferenceChangeListenter);
        }
        
    }
    
}


