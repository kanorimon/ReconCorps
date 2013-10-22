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
    
    //�ݒ�t�@�C���֘A
    SharedPreferences pref;

    //�{�^��
	private View clickReturn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		Log.v(getString(R.string.log),"AchievementsActivity�@onCreate() start");

		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

		//�ݒ�f�[�^�擾
    	pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    	//���X�g�쐬
        ListView listView = (ListView)AchievementsActivity.this.findViewById(R.id.users_listview);
        ArrayAdapter<Employee> adapter = new EmployeesAdaper(this, R.layout.row);
        listView.setAdapter(adapter);
        
        
        //�񍐉�
        adapter.add(new Employee("�񍐉�", ""));

        int kaisu = Integer.parseInt(pref.getString(PREF_COUNT, "0"));
        if(kaisu < 10){
            adapter.add(new Employee("???", "10��񍐂���"));
        }else{
            adapter.add(new Employee("�n���W�������̃T�C��", "10��񍐂���"));
        }
        
        if(kaisu < 50){
            adapter.add(new Employee("???", "50��񍐂���"));
        }else{
            adapter.add(new Employee("�~�P�������̃T�C��", "50��񍐂���"));
        }

        if(kaisu < 100){
            adapter.add(new Employee("???", "100��񍐂���"));
        }else{
            adapter.add(new Employee("�����@�C���m���̃T�C��", "100��񍐂���"));
        }

        if(kaisu < 1000){
            adapter.add(new Employee("???", "1,000��񍐂���"));
        }else{
            adapter.add(new Employee("�G�����B���c���̃T�C��", "1,000��񍐂���"));
        }
        
        //�݌v����
        adapter.add(new Employee("", ""));
        adapter.add(new Employee("�ړ�����", ""));

        int dist = (int)Float.parseFloat(pref.getString(PREF_DIST, "0"));
        if(dist < 10000){
            adapter.add(new Employee("???", "10km�ړ�����"));
        }else{
            adapter.add(new Employee("�V��", "10km�ړ�����"));
        }
        
        if(dist < 100000){
            adapter.add(new Employee("???", "100km�ړ�����"));
        }else{
            adapter.add(new Employee("��蕺", "100km�ړ�����"));
        }

        if(dist < 1000000){
            adapter.add(new Employee("???", "1,000km�ړ�����"));
        }else{
            adapter.add(new Employee("������", "1,000km�ړ�����"));
        }

        if(dist < 10000000){
            adapter.add(new Employee("???", "10,000km�ړ�����"));
        }else{
            adapter.add(new Employee("�x�e������", "10,000km�ړ�����"));
        }
        
        //���l����
    	//�������擾
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
        adapter.add(new Employee("��������", ""));

    	if(titan+kiko < 10){
            adapter.add(new Employee("???", "���l��10�̓�������"));
        }else{
            adapter.add(new Employee("10�̓����@�i�J�܁F�T�V����Ïē��p�[�e�B�j", "���l��10�̓�������"));
        }

    	if(titan+kiko < 100){
            adapter.add(new Employee("???", "���l��100�̓�������"));
        }else{
            adapter.add(new Employee("100�̓����@�i�J�܁F�G�����Ƌ��l�쒀�c�A�[�j", "���l��100�̓�������"));
        }

    	if(titan+kiko < 100){
            adapter.add(new Employee("???", "���l��1,000�̓�������"));
        }else{
            adapter.add(new Employee("1,000�̓����@�i�J�܁F�����@�C���m���Ƌ��{�����|���c�A�[�j", "���l��1,000�̓�������"));
        }

    	if(titan_3 > 0 && titan_5 > 0 && titan_7 > 0 && titan_9 > 0 && titan_11 > 0 && titan_13 > 0 && titan_15 > 0 &&
    			kiko_3 > 0 && kiko_5 > 0 && kiko_7 > 0 && kiko_9 > 0 && kiko_11 > 0 && kiko_13 > 0 && kiko_15 > 0){
    		
            adapter.add(new Employee("�S�퓢���@�i�J�܁F�n���W�������ƒ��܂Ő����W�I�j", "���l��S��ޓ�������"));
        }else{
            adapter.add(new Employee("???", "���l��S��ޓ�������"));
        }

    	//���l������
        adapter.add(new Employee("", ""));
        adapter.add(new Employee("���l������", ""));

    	if(titan_3 < 1){
            adapter.add(new Employee("???", "3m�����l��1�̓�������"));
        }else{
            adapter.add(new Employee("�悤�����������c��", "3m�����l��1�̓�������"));
        }
    	
    	if(titan_5 < 1){
            adapter.add(new Employee("???", "5m�����l��1�̓�������"));
        }else{
            adapter.add(new Employee("�܂��͐�����t", "5m�����l��1�̓�������"));
        }

    	if(titan_7 < 1){
            adapter.add(new Employee("???", "7m�����l��1�̓�������"));
        }else{
            adapter.add(new Employee("�����āA�ӂ����������ǂ���", "7m�����l��1�̓�������"));
        }

    	if(titan_9 < 1){
            adapter.add(new Employee("???", "9m�����l��1�̓�������"));
        }else{
            adapter.add(new Employee("���͓��̃X�[�v��", "9m�����l��1�̓�������"));
        }

    	if(titan_11 < 1){
            adapter.add(new Employee("???", "11m�����l��1�̓�������"));
        }else{
            adapter.add(new Employee("�Ă����Ẵp��������܂���", "11m�����l��1�̓�������"));
        }

    	if(titan_13 < 1){
            adapter.add(new Employee("???", "13m�����l��1�̓�������"));
        }else{
            adapter.add(new Employee("�Q��O�ɂ͎�����t", "13m�����l��1�̓�������"));
        }

    	if(titan_15 < 1){
            adapter.add(new Employee("???", "15m�����l��1�̓�������"));
        }else{
            adapter.add(new Employee("�ł��A�Ō�̔ӎ`�ɂ͂܂�����", "15m�����l��1�̓�������"));
        }
    	
    	int tobatu2 = 10;

        adapter.add(new Employee("", ""));

    	if(titan_3 < tobatu2){
            adapter.add(new Employee("???", "3m�����l��" + tobatu2 + "�̓�������"));
        }else{
            adapter.add(new Employee("NO KUCHIKU, NO LIFE.", "3m�����l��" + tobatu2 + "�̓�������"));
        }
    	
    	if(titan_5 < tobatu2){
            adapter.add(new Employee("???", "5m�����l��" + tobatu2 + "�̓�������"));
        }else{
            adapter.add(new Employee("���̍킬�����V���[�v�ł���", "5m�����l��" + tobatu2 + "�̓�������"));
        }

    	if(titan_7 < tobatu2){
            adapter.add(new Employee("???", "7m�����l��" + tobatu2 + "�̓�������"));
        }else{
            adapter.add(new Employee("�������A�n�����ɍs����", "7m�����l��" + tobatu2 + "�̓�������"));
        }

    	if(titan_9 < tobatu2){
            adapter.add(new Employee("???", "9m�����l��" + tobatu2 + "�̓�������"));
        }else{
            adapter.add(new Employee("���l�ƌ��N���Ȋw����", "9m�����l��" + tobatu2 + "�̓�������"));
        }

    	if(titan_11 < tobatu2){
            adapter.add(new Employee("???", "11m�����l��" + tobatu2 + "�̓�������"));
        }else{
            adapter.add(new Employee("�������A�́@���̐�ցB", "11m�����l��" + tobatu2 + "�̓�������"));
        }

    	if(titan_13 < tobatu2){
            adapter.add(new Employee("???", "13m�����l��" + tobatu2 + "�̓�������"));
        }else{
            adapter.add(new Employee("�r�ŗ͂̕ς��Ȃ�������l�̕��m", "13m�����l��" + tobatu2 + "�̓�������"));
        }

    	if(titan_15 < tobatu2){
            adapter.add(new Employee("???", "15m�����l��" + tobatu2 + "�̓�������"));
        }else{
            adapter.add(new Employee("���ꂢ�D���Ȑl�ލŋ��́A�D���ł����H", "15m�����l��" + tobatu2 + "�̓�������"));
        }
    	
 

    	//��s�퓢����
        adapter.add(new Employee("", ""));
        adapter.add(new Employee("��s�퓢����", ""));
    	
    	if(kiko_3 < 1){
            adapter.add(new Employee("???", "3m����s���1�̓�������"));
        }else{
            adapter.add(new Employee("�C�s�̐V��", "3m����s���1�̓�������"));
        }
    	
    	if(kiko_5 < 1){
            adapter.add(new Employee("???", "5m����s���1�̓�������"));
        }else{
            adapter.add(new Employee("�����ȕ��m", "5m����s���1�̓�������"));
        }

    	if(kiko_7 < 1){
            adapter.add(new Employee("???", "7m����s���1�̓�������"));
        }else{
            adapter.add(new Employee("�쒀�n���m", "7m����s���1�̓�������"));
        }

    	if(kiko_9 < 1){
            adapter.add(new Employee("???", "9m����s���1�̓�������"));
        }else{
            adapter.add(new Employee("�쒀�n���m�@��", "9m����s���1�̓�������"));
        }

    	if(kiko_11 < 1){
            adapter.add(new Employee("???", "11m����s���1�̓�������"));
        }else{
            adapter.add(new Employee("�쒀�n���m�@����", "11m����s���1�̓�������"));
        }

    	if(kiko_13 < 1){
            adapter.add(new Employee("???", "13m����s���1�̓�������"));
        }else{
            adapter.add(new Employee("�쒀�n������", "13m����s���1�̓�������"));
        }

    	if(kiko_15 < 1){
            adapter.add(new Employee("???", "15m����s���1�̓�������"));
        }else{
            adapter.add(new Employee("�l�ލŋ�", "15m����s���1�̓�������"));
        }
    	
    	int tobatu_kiko_2 = 10;

        adapter.add(new Employee("", ""));

    	if(kiko_3 < tobatu_kiko_2){
            adapter.add(new Employee("???", "3m����s���" + tobatu_kiko_2 + "�̓�������"));
        }else{
            adapter.add(new Employee("L is for Levi", "3m����s���" + tobatu_kiko_2 + "�̓�������"));
        }
    	
    	if(kiko_5 < tobatu_kiko_2){
            adapter.add(new Employee("???", "5m����s���" + tobatu_kiko_2 + "�̓�������"));
        }else{
            adapter.add(new Employee("�V�K���V�i�̏t������", "5m����s���" + tobatu_kiko_2 + "�̓�������"));
        }

    	if(kiko_7 < tobatu_kiko_2){
            adapter.add(new Employee("???", "7m����s���" + tobatu_kiko_2 + "�̓�������"));
        }else{
            adapter.add(new Employee("�g���X�g�̒����ߌ�", "7m����s���" + tobatu_kiko_2 + "�̓�������"));
        }

    	if(kiko_9 < tobatu_kiko_2){
            adapter.add(new Employee("???", "9m����s���" + tobatu_kiko_2 + "�̓�������"));
        }else{
            adapter.add(new Employee("�����킪�܁A�ƕ��m�͌�����", "9m����s���" + tobatu_kiko_2 + "�̓�������"));
        }

    	if(kiko_11 < tobatu_kiko_2){
            adapter.add(new Employee("???", "11m����s���" + tobatu_kiko_2 + "�̓�������"));
        }else{
            adapter.add(new Employee("�����߂���T�V���̐푈", "11m����s���" + tobatu_kiko_2 + "�̓�������"));
        }

    	if(kiko_13 < tobatu_kiko_2){
            adapter.add(new Employee("???", "13m����s���" + tobatu_kiko_2 + "�̓�������"));
        }else{
            adapter.add(new Employee("�ǂ��p������", "13m����s���" + tobatu_kiko_2 + "�̓�������"));
        }

    	if(kiko_15 < tobatu_kiko_2){
            adapter.add(new Employee("???", "15m����s���" + tobatu_kiko_2 + "�̓�������"));
        }else{
            adapter.add(new Employee("�������c�̗D�������l", "15m����s���" + tobatu_kiko_2 + "�̓�������"));
        }
    	
    	
    	
    	
      	//�߂�{�^��
        clickReturn = findViewById(R.id.button_return_ach);
        clickReturn.setOnClickListener(oCLforShowButton);
        
        Log.v(getString(R.string.log),"AchievementsActivity�@onCreate() end");

    }
    
    //�{�^���N���b�N���̃��X�i
    private final OnClickListener oCLforShowButton = new OnClickListener() {
    	
        @Override
        public void onClick(View v) {
            switch(v.getId()){

            //�߂�{�^��
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
