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

        ListView listView = (ListView)AchievementsActivity.this.findViewById(R.id.users_listview);
        ArrayAdapter<Employee> adapter = new EmployeesAdaper(this, R.layout.row);
        listView.setAdapter(adapter);
        
        for (int i = 0; i < 100; i++) {
            adapter.add(new Employee("employee" + (i + 1), 20 + i));
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
        private int age;
        public Employee(String name, int age) {
            this.name = name;
            this.age = age;
        }
        public String getName() {
            return this.name;
        }
        public int getAge() {
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
            ((TextView)view.findViewById(R.id.second_textview)).setText("age");
            return view;
        }
        @Override
        public void add(Employee object) {
            super.add(object);
            this.employees.add(object);
        }
    }
}
