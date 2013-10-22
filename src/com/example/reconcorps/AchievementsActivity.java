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
        adapter.add(new Employee("報告回数", ""));

        int kaisu = Integer.parseInt(pref.getString(PREF_COUNT, "0"));
        if(kaisu < 10){
            adapter.add(new Employee("???", "10回報告した"));
        }else{
            adapter.add(new Employee("ハンジ分隊長のサイン", "10回報告した"));
        }
        
        if(kaisu < 50){
            adapter.add(new Employee("???", "50回報告した"));
        }else{
            adapter.add(new Employee("ミケ分隊長のサイン", "50回報告した"));
        }

        if(kaisu < 100){
            adapter.add(new Employee("???", "100回報告した"));
        }else{
            adapter.add(new Employee("リヴァイ兵士長のサイン", "100回報告した"));
        }

        if(kaisu < 1000){
            adapter.add(new Employee("???", "1,000回報告した"));
        }else{
            adapter.add(new Employee("エルヴィン団長のサイン", "1,000回報告した"));
        }
        
        //累計距離
        adapter.add(new Employee("", ""));
        adapter.add(new Employee("移動距離", ""));

        int dist = (int)Float.parseFloat(pref.getString(PREF_DIST, "0"));
        if(dist < 10000){
            adapter.add(new Employee("???", "10km移動した"));
        }else{
            adapter.add(new Employee("新兵", "10km移動した"));
        }
        
        if(dist < 100000){
            adapter.add(new Employee("???", "100km移動した"));
        }else{
            adapter.add(new Employee("若手兵", "100km移動した"));
        }

        if(dist < 1000000){
            adapter.add(new Employee("???", "1,000km移動した"));
        }else{
            adapter.add(new Employee("中堅兵", "1,000km移動した"));
        }

        if(dist < 10000000){
            adapter.add(new Employee("???", "10,000km移動した"));
        }else{
            adapter.add(new Employee("ベテラン兵", "10,000km移動した"));
        }
        
        //巨人討伐
    	//討伐数取得
    	int titan_3 = Integer.parseInt(pref.getString(PREF_TITAN_3, "0"));
    	int titan_5 = Integer.parseInt(pref.getString(PREF_TITAN_5, "0"));
    	int titan_7 = Integer.parseInt(pref.getString(PREF_TITAN_7, "0"));
    	int titan_9 = Integer.parseInt(pref.getString(PREF_TITAN_9, "0"));
    	int titan_11 = Integer.parseInt(pref.getString(PREF_TITAN_11, "0"));
    	int titan_13 = Integer.parseInt(pref.getString(PREF_TITAN_13, "0"));
    	int titan_15 = Integer.parseInt(pref.getString(PREF_TITAN_15, "0"));
    	int kiko_3 = Integer.parseInt(pref.getString(PREF_KIKO_3, "0"));
    	int kiko_5 = Integer.parseInt(pref.getString(PREF_KIKO_5, "0"));
    	int kiko_7 = Integer.parseInt(pref.getString(PREF_KIKO_7, "0"));
    	int kiko_9 = Integer.parseInt(pref.getString(PREF_KIKO_9, "0"));
    	int kiko_11 = Integer.parseInt(pref.getString(PREF_KIKO_11, "0"));
    	int kiko_13 = Integer.parseInt(pref.getString(PREF_KIKO_13, "0"));
    	int kiko_15 = Integer.parseInt(pref.getString(PREF_KIKO_15, "0"));

    	int titan = titan_3 + titan_5 + titan_7 + titan_9 + titan_11 + titan_13 + titan_15;
    	int kiko = kiko_3 + kiko_5 + kiko_7 + kiko_9 + kiko_11 + kiko_13 + kiko_15;

        adapter.add(new Employee("", ""));
        adapter.add(new Employee("総討伐数", ""));

    	if(titan+kiko < 10){
            adapter.add(new Employee("???", "巨人を10体討伐した"));
        }else{
            adapter.add(new Employee("10体討伐　（褒賞：サシャ主催焼肉パーティ）", "巨人を10体討伐した"));
        }

    	if(titan+kiko < 100){
            adapter.add(new Employee("???", "巨人を100体討伐した"));
        }else{
            adapter.add(new Employee("100体討伐　（褒賞：エレンと巨人駆逐ツアー）", "巨人を100体討伐した"));
        }

    	if(titan+kiko < 100){
            adapter.add(new Employee("???", "巨人を1,000体討伐した"));
        }else{
            adapter.add(new Employee("1,000体討伐　（褒賞：リヴァイ兵士長と旧本部お掃除ツアー）", "巨人を1,000体討伐した"));
        }

    	if(titan_3 > 0 && titan_5 > 0 && titan_7 > 0 && titan_9 > 0 && titan_11 > 0 && titan_13 > 0 && titan_15 > 0 &&
    			kiko_3 > 0 && kiko_5 > 0 && kiko_7 > 0 && kiko_9 > 0 && kiko_11 > 0 && kiko_13 > 0 && kiko_15 > 0){
    		
            adapter.add(new Employee("全種討伐　（褒賞：ハンジ分隊長と朝まで生ラジオ）", "巨人を全種類討伐した"));
        }else{
            adapter.add(new Employee("???", "巨人を全種類討伐した"));
        }

    	//巨人討伐数
        adapter.add(new Employee("", ""));
        adapter.add(new Employee("巨人討伐数", ""));

    	if(titan_3 < 1){
            adapter.add(new Employee("???", "3m級巨人を1体討伐した"));
        }else{
            adapter.add(new Employee("ようこそ調査兵団へ", "3m級巨人を1体討伐した"));
        }
    	
    	if(titan_5 < 1){
            adapter.add(new Employee("???", "5m級巨人を1体討伐した"));
        }else{
            adapter.add(new Employee("まずは水を一杯", "5m級巨人を1体討伐した"));
        }

    	if(titan_7 < 1){
            adapter.add(new Employee("???", "7m級巨人を1体討伐した"));
        }else{
            adapter.add(new Employee("そして、ふかした芋をどうぞ", "7m級巨人を1体討伐した"));
        }

    	if(titan_9 < 1){
            adapter.add(new Employee("???", "9m級巨人を1体討伐した"));
        }else{
            adapter.add(new Employee("次は豆のスープを", "9m級巨人を1体討伐した"));
        }

    	if(titan_11 < 1){
            adapter.add(new Employee("???", "11m級巨人を1体討伐した"));
        }else{
            adapter.add(new Employee("焼きたてのパンもありますよ", "11m級巨人を1体討伐した"));
        }

    	if(titan_13 < 1){
            adapter.add(new Employee("???", "13m級巨人を1体討伐した"));
        }else{
            adapter.add(new Employee("寝る前には酒を一杯", "13m級巨人を1体討伐した"));
        }

    	if(titan_15 < 1){
            adapter.add(new Employee("???", "15m級巨人を1体討伐した"));
        }else{
            adapter.add(new Employee("でも、最後の晩餐にはまだ早い", "15m級巨人を1体討伐した"));
        }
    	
    	int tobatu2 = 10;

        adapter.add(new Employee("", ""));

    	if(titan_3 < tobatu2){
            adapter.add(new Employee("???", "3m級巨人を" + tobatu2 + "体討伐した"));
        }else{
            adapter.add(new Employee("NO KUCHIKU, NO LIFE.", "3m級巨人を" + tobatu2 + "体討伐した"));
        }
    	
    	if(titan_5 < tobatu2){
            adapter.add(new Employee("???", "5m級巨人を" + tobatu2 + "体討伐した"));
        }else{
            adapter.add(new Employee("この削ぎ方がシャープでしょ", "5m級巨人を" + tobatu2 + "体討伐した"));
        }

    	if(titan_7 < tobatu2){
            adapter.add(new Employee("???", "7m級巨人を" + tobatu2 + "体討伐した"));
        }else{
            adapter.add(new Employee("そうだ、地下室に行こう", "7m級巨人を" + tobatu2 + "体討伐した"));
        }

    	if(titan_9 < tobatu2){
            adapter.add(new Employee("???", "9m級巨人を" + tobatu2 + "体討伐した"));
        }else{
            adapter.add(new Employee("巨人と健康を科学する", "9m級巨人を" + tobatu2 + "体討伐した"));
        }

    	if(titan_11 < tobatu2){
            adapter.add(new Employee("???", "11m級巨人を" + tobatu2 + "体討伐した"));
        }else{
            adapter.add(new Employee("くちく、の　その先へ。", "11m級巨人を" + tobatu2 + "体討伐した"));
        }

    	if(titan_13 < tobatu2){
            adapter.add(new Employee("???", "13m級巨人を" + tobatu2 + "体討伐した"));
        }else{
            adapter.add(new Employee("殲滅力の変わらないただ一人の兵士", "13m級巨人を" + tobatu2 + "体討伐した"));
        }

    	if(titan_15 < tobatu2){
            adapter.add(new Employee("???", "15m級巨人を" + tobatu2 + "体討伐した"));
        }else{
            adapter.add(new Employee("きれい好きな人類最強は、好きですか？", "15m級巨人を" + tobatu2 + "体討伐した"));
        }
    	
 

    	//奇行種討伐数
        adapter.add(new Employee("", ""));
        adapter.add(new Employee("奇行種討伐数", ""));
    	
    	if(kiko_3 < 1){
            adapter.add(new Employee("???", "3m級奇行種を1体討伐した"));
        }else{
            adapter.add(new Employee("気鋭の新兵", "3m級奇行種を1体討伐した"));
        }
    	
    	if(kiko_5 < 1){
            adapter.add(new Employee("???", "5m級奇行種を1体討伐した"));
        }else{
            adapter.add(new Employee("屈強な兵士", "5m級奇行種を1体討伐した"));
        }

    	if(kiko_7 < 1){
            adapter.add(new Employee("???", "7m級奇行種を1体討伐した"));
        }else{
            adapter.add(new Employee("駆逐系兵士", "7m級奇行種を1体討伐した"));
        }

    	if(kiko_9 < 1){
            adapter.add(new Employee("???", "9m級奇行種を1体討伐した"));
        }else{
            adapter.add(new Employee("駆逐系兵士　改", "9m級奇行種を1体討伐した"));
        }

    	if(kiko_11 < 1){
            adapter.add(new Employee("???", "11m級奇行種を1体討伐した"));
        }else{
            adapter.add(new Employee("駆逐系兵士　改二", "11m級奇行種を1体討伐した"));
        }

    	if(kiko_13 < 1){
            adapter.add(new Employee("???", "13m級奇行種を1体討伐した"));
        }else{
            adapter.add(new Employee("駆逐系分隊長", "13m級奇行種を1体討伐した"));
        }

    	if(kiko_15 < 1){
            adapter.add(new Employee("???", "15m級奇行種を1体討伐した"));
        }else{
            adapter.add(new Employee("人類最強", "15m級奇行種を1体討伐した"));
        }
    	
    	int tobatu_kiko_2 = 10;

        adapter.add(new Employee("", ""));

    	if(kiko_3 < tobatu_kiko_2){
            adapter.add(new Employee("???", "3m級奇行種を" + tobatu_kiko_2 + "体討伐した"));
        }else{
            adapter.add(new Employee("L is for Levi", "3m級奇行種を" + tobatu_kiko_2 + "体討伐した"));
        }
    	
    	if(kiko_5 < tobatu_kiko_2){
            adapter.add(new Employee("???", "5m級奇行種を" + tobatu_kiko_2 + "体討伐した"));
        }else{
            adapter.add(new Employee("シガンシナの春を愛す", "5m級奇行種を" + tobatu_kiko_2 + "体討伐した"));
        }

    	if(kiko_7 < tobatu_kiko_2){
            adapter.add(new Employee("???", "7m級奇行種を" + tobatu_kiko_2 + "体討伐した"));
        }else{
            adapter.add(new Employee("トロストの長い午後", "7m級奇行種を" + tobatu_kiko_2 + "体討伐した"));
        }

    	if(kiko_9 < tobatu_kiko_2){
            adapter.add(new Employee("???", "9m級奇行種を" + tobatu_kiko_2 + "体討伐した"));
        }else{
            adapter.add(new Employee("流れよわが涙、と兵士は言った", "9m級奇行種を" + tobatu_kiko_2 + "体討伐した"));
        }

    	if(kiko_11 < tobatu_kiko_2){
            adapter.add(new Employee("???", "11m級奇行種を" + tobatu_kiko_2 + "体討伐した"));
        }else{
            adapter.add(new Employee("芋をめぐるサシャの戦争", "11m級奇行種を" + tobatu_kiko_2 + "体討伐した"));
        }

    	if(kiko_13 < tobatu_kiko_2){
            adapter.add(new Employee("???", "13m級奇行種を" + tobatu_kiko_2 + "体討伐した"));
        }else{
            adapter.add(new Employee("壁を継ぐもの", "13m級奇行種を" + tobatu_kiko_2 + "体討伐した"));
        }

    	if(kiko_15 < tobatu_kiko_2){
            adapter.add(new Employee("???", "15m級奇行種を" + tobatu_kiko_2 + "体討伐した"));
        }else{
            adapter.add(new Employee("調査兵団の優しい巨人", "15m級奇行種を" + tobatu_kiko_2 + "体討伐した"));
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
