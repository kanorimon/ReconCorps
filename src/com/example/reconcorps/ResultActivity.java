package com.example.reconcorps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ResultActivity extends Activity{

    //ボタン
	private View clickReturn;
	private View clickBattle;
	private View clickEscape;
	ImageView image_report_now;
	View relative_return;
	View relative_titan;
	View relative_sogu;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		Log.v(getString(R.string.log),"ReportActivity　onCreate() start");

		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

    	image_report_now = (ImageView)findViewById(R.id.image_report_now);
		image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_kuchiku));
    	
    	//非表示
    	relative_titan = findViewById(R.id.relative_titan);
    	relative_sogu = findViewById(R.id.relative_sogu);
    	
    	relative_titan.setVisibility(View.GONE);
    	relative_sogu.setVisibility(View.GONE);
    	
      	//戻るボタン
        clickReturn = findViewById(R.id.button_return);
        clickReturn.setOnClickListener(oCLforShowButton);

   		Log.v(getString(R.string.log),"ReportActivity　onCreate() end");

    }

    
    //ボタンクリック時のリスナ
    private final OnClickListener oCLforShowButton = new OnClickListener() {
    	
        @Override
        public void onClick(View v) {
            switch(v.getId()){

            //戻るボタン
            case R.id.button_return:
            	ResultActivity.this.finish();
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
}
