package com.example.reconcorps;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class AchievementsActivity extends Activity{

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
    
    //設定ファイル関連
    SharedPreferences pref;

    //ボタン
	private View clickReturn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		Log.v(getString(R.string.log),"AchievementsActivity　onCreate() start");

		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

		//設定データ取得
    	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    	//リスト作成
        ListView listView = (ListView)AchievementsActivity.this.findViewById(R.id.users_listview);
        ArrayAdapter<Employee> adapter = new EmployeesAdaper(this, R.layout.row);
        listView.setAdapter(adapter);
        
        //報告回数
        int kaisu = Integer.parseInt(pref.getString(PREF_COUNT, "0"));
        if(kaisu < 10){
            adapter.add(new Employee("???", "10回以上報告した"));
        }else{
            adapter.add(new Employee("ハンジ分隊長のサイン", "10回以上報告した"));
        }
        
        if(kaisu < 50){
            adapter.add(new Employee("???", "50回以上報告した"));
        }else{
            adapter.add(new Employee("ミケ分隊長のサイン", "50回以上報告した"));
        }

        if(kaisu < 100){
            adapter.add(new Employee("???", "100回以上報告した"));
        }else{
            adapter.add(new Employee("リヴァイ兵士長のサイン", "100回以上報告した"));
        }

        if(kaisu < 1000){
            adapter.add(new Employee("???", "1000回以上報告した"));
        }else{
            adapter.add(new Employee("エルヴィン団長のサイン", "1000回以上報告した"));
        }
        
        //累計距離
        int dist = (int)Float.parseFloat(pref.getString(PREF_DIST, "0"));
        if(dist < 10000){
            adapter.add(new Employee("???", "10km以上移動した"));
        }else{
            adapter.add(new Employee("新兵", "10km以上移動した"));
        }
        
        if(dist < 100000){
            adapter.add(new Employee("???", "100km以上移動した"));
        }else{
            adapter.add(new Employee("若手兵", "100km以上移動した"));
        }

        if(dist < 1000000){
            adapter.add(new Employee("???", "1000km以上移動した"));
        }else{
            adapter.add(new Employee("中堅兵", "1000km以上移動した"));
        }

        if(dist < 10000000){
            adapter.add(new Employee("???", "10000km以上移動した"));
        }else{
            adapter.add(new Employee("ベテラン兵", "10000km以上移動した"));
        }

        
        
      	//戻るボタン
        clickReturn = findViewById(R.id.button_return_ach);
        clickReturn.setOnClickListener(oCLforShowButton);
        
        Log.v(getString(R.string.log),"AchievementsActivity　onCreate() end");

    }
    
    //ボタンクリック時のリスナ
    private final OnClickListener oCLforShowButton = new OnClickListener() {
    	
        @Override
        public void onClick(View v) {
            switch(v.getId()){

            //戻るボタン
            case R.id.button_return_ach:
                AchievementsActivity.this.finish();
                break;

            }
        }
    };
    
    @Override
    public void finish() {
        super.finish();
 
        overridePendingTransition(0, 0);
    }
    
    class Employee {
        private String name;
        private String age;
        public Employee(String name, String age) {
            this.name = name;
            this.age = age;
        }
        public String getName() {
            return this.name;
        }
        public String getAge() {
            return this.age;
        }
    }

    class EmployeesAdaper extends ArrayAdapter<Employee> {
       private ArrayList<Employee> employees = new ArrayList<Employee>();
       private LayoutInflater inflater;
       private int layout;
       public EmployeesAdaper(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            this.inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            this.layout = textViewResourceId;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                view = this.inflater.inflate(this.layout, null);
            }
            Employee employee = this.employees.get(position);
             
            ((TextView)view.findViewById(R.id.first_textview)).setText(employee.getName());
            ((TextView)view.findViewById(R.id.second_textview)).setText(employee.getAge());
            return view;
        }
        @Override
        public void add(Employee object) {
            super.add(object);
            this.employees.add(object);
        }
    }
}
