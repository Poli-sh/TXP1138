package com.example.prilogulka.menu.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.prilogulka.R;

import static android.content.Context.MODE_PRIVATE;


public class PersonalDataFragment extends Fragment{

    //добавить подгрузку из БД
    public static String SHARED_PREFERENCES_NAME = "userInfo";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    EditText editName, editSurname, editCity, editDay, editMonth, editYear;
    Spinner spinner;

    final String LOG_TAG = "PERSONAL_DATA_FRAGMENT";

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initUserInfoStorer();

        String info = getStringFromSharedPreferences("имя");
        String info1 = getStringFromSharedPreferences("фамилия");
        String info2 = getStringFromSharedPreferences("город");
        String info3 = getStringFromSharedPreferences("день рождения");
        String info4 = getStringFromSharedPreferences("месяц рождения");
        String info5 = getStringFromSharedPreferences("год рождения");

        Log.i(LOG_TAG, "ПОЛ -------------- " + getStringFromSharedPreferences("пол"));

        String buf = getStringFromSharedPreferences("пол");
        int info6 = buf.equals("") ? 0 : Integer.parseInt(buf);

        Log.i("PERSONAL_DATA_FRAGMENT", info6 + "");

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_personal_data, container, false);
        editName = rootView.findViewById(R.id.name);
        editName.setText(info);
        editName.setKeyListener(null);
        //editName.setEnabled(false);

        editSurname = rootView.findViewById(R.id.surname);
        editSurname.setText(info1);
        editSurname.setKeyListener(null);//.setEnabled(false);

        editCity = rootView.findViewById(R.id.city);
        editCity.setText(info2);
        editCity.setKeyListener(null);//.setEnabled(false);

        editDay= rootView.findViewById(R.id.day);
        editDay.setText(info3);
        editDay.setKeyListener(null);//.setEnabled(false);

        editMonth = rootView.findViewById(R.id.month);
        editMonth.setText(info4);
        editMonth.setKeyListener(null);//.setEnabled(false);

        editYear = rootView.findViewById(R.id.year);
        editYear.setText(info5);
        editYear.setKeyListener(null);//.setEnabled(false);

        spinner = rootView.findViewById(R.id.sex);
        spinner.setSelection(info6);

        return rootView;
    }

    private void initUserInfoStorer(){
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getStringFromSharedPreferences(String keyInSharedPreferences) {
        return sharedPreferences.getString(keyInSharedPreferences, "");
    }

}