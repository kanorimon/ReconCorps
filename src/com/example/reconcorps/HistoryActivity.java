package com.example.reconcorps;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
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

public class HistoryActivity extends Activity{

	static final String PREF_LAT = "pref_lat";
    static final String PREF_LONG = "pref_long";
    static final String PREF_DIST = "pref_dist";
    static final String PREF_UP_DIST = "pref_up_dist";
    static final String PREF_COUNT = "pref_count";
    static final String PREF_DATE = "pref_date";
    static final String PREF_LEVEL = "pref_level";
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
    
    //Ýèt@CÖA
    SharedPreferences pref;

    //{^
	private View clickReturn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		Log.v(getString(R.string.log),"AchievementsActivity@onCreate() start");

		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

		//Ýèf[^æ¾
    	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    	ImageView image_report_now = (ImageView)findViewById(R.id.image_report_now);
		image_report_now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_history));
		
    	//Xgì¬
        ListView listView = (ListView)HistoryActivity.this.findViewById(R.id.users_listview);
        ArrayAdapter<Employee> adapter = new EmployeesAdaper(this, R.layout.row);
        listView.setAdapter(adapter);
        
        //ññ
        adapter.add(new Employee("yññz", pref.getString(PREF_COUNT, "0") + "ñ"));

        //ÅIñÔ
        adapter.add(new Employee("yÅIñúz", pref.getString(PREF_DATE, "¢ñ")));

        //ÅIñn_
        adapter.add(new Employee("yÅIñÜxz", pref.getString(PREF_LAT, "¢ñ")));
        adapter.add(new Employee("yÅIñoxz", pref.getString(PREF_LONG, "¢ñ")));
        
        //KXcÊ
        adapter.add(new Employee("yKXcÊi10mÚ®²ÆÉ1%â[jz", pref.getString(PREF_HAVE_GAS, "0") + "%"));

        //¢°
        adapter.add(new Employee("y3ml@¢°z", pref.getString(PREF_TITAN_3, "0") + "Ì"));
        adapter.add(new Employee("y5ml@¢°z", pref.getString(PREF_TITAN_5, "0") + "Ì"));
        adapter.add(new Employee("y7ml@¢°z", pref.getString(PREF_TITAN_7, "0") + "Ì"));
        adapter.add(new Employee("y9ml@¢°z", pref.getString(PREF_TITAN_9, "0") + "Ì"));
        adapter.add(new Employee("y11ml@¢°z", pref.getString(PREF_TITAN_11, "0") + "Ì"));
        adapter.add(new Employee("y13ml@¢°z", pref.getString(PREF_TITAN_13, "0") + "Ì"));
        adapter.add(new Employee("y15ml@¢°z", pref.getString(PREF_TITAN_15, "0") + "Ì"));
        adapter.add(new Employee("y3mïsí@¢°z", pref.getString(PREF_KIKO_3, "0") + "Ì"));
        adapter.add(new Employee("y5mïsí@¢°z", pref.getString(PREF_KIKO_5, "0") + "Ì"));
        adapter.add(new Employee("y7mïsí@¢°z", pref.getString(PREF_KIKO_7, "0") + "Ì"));
        adapter.add(new Employee("y9mïsí@¢°z", pref.getString(PREF_KIKO_9, "0") + "Ì"));
        adapter.add(new Employee("y11mïsí@¢°z", pref.getString(PREF_KIKO_11, "0") + "Ì"));
        adapter.add(new Employee("y13mïsí@¢°z", pref.getString(PREF_KIKO_13, "0") + "Ì"));
        adapter.add(new Employee("y15mïsí@¢°z", pref.getString(PREF_KIKO_15, "0") + "Ì"));

        
        
      	//ßé{^
        clickReturn = findViewById(R.id.button_return_ach);
        clickReturn.setOnClickListener(oCLforShowButton);
        
        Log.v(getString(R.string.log),"AchievementsActivity@onCreate() end");

    }
    
    //{^NbNÌXi
    private final OnClickListener oCLforShowButton = new OnClickListener() {
    	
        @Override
        public void onClick(View v) {
            switch(v.getId()){

            //ßé{^
            case R.id.button_return_ach:
            	HistoryActivity.this.finish();
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
