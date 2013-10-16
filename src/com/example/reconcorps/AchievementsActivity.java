package com.example.reconcorps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class AchievementsActivity extends Activity{

    //�ݒ�t�@�C���֘A
    SharedPreferences pref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
		Log.v(getString(R.string.log),"AchievementsActivity�@onCreate() start");

		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        //�A�N�V�����o�[��\��
        ActionBar actionBar = getActionBar();
		actionBar.hide();

		//�ݒ�f�[�^�擾
    	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        
   		Log.v(getString(R.string.log),"AchievementsActivity�@onCreate() end");

    }
}
