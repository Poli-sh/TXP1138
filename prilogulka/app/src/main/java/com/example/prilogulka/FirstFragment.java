package com.example.prilogulka;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;



public class FirstFragment extends Fragment{

    //добавить подгрузку из БД

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String info = getActivity().getIntent().getStringExtra("имя");
        String info1 = getActivity().getIntent().getStringExtra("фамилия");
        String info2 = getActivity().getIntent().getStringExtra("город");
        String info3 = getActivity().getIntent().getStringExtra("день рождения");
        String info4 = getActivity().getIntent().getStringExtra("месяц рождения");
        String info5 = getActivity().getIntent().getStringExtra("год рождения");
//        String info6 = getActivity().getIntent().getStringExtra("пол"); //пока не реализовано

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_first, container, false);
        final EditText editText = rootView.findViewById(R.id.name1);
        editText.setText(info);
        editText.setEnabled(false);
        final EditText editText1 = rootView.findViewById(R.id.surname1);
        editText1.setText(info1);
        editText1.setEnabled(false);
        final EditText editText2 = rootView.findViewById(R.id.city1);
        editText2.setText(info2);
        editText2.setEnabled(false);
        final EditText editText3 = rootView.findViewById(R.id.day1);
        editText3.setText(info3);
        editText3.setEnabled(false);
        final EditText editText4 = rootView.findViewById(R.id.month1);
        editText4.setText(info4);
        editText4.setEnabled(false);
        final EditText editText5 = rootView.findViewById(R.id.year1);
        editText5.setText(info5);
        editText5.setEnabled(false);
        final EditText editText6 = rootView.findViewById(R.id.editText6);
        editText6.setEnabled(false);
        final EditText editText7 = rootView.findViewById(R.id.editText7);
        editText7.setEnabled(false);
//        final EditText editText8 = rootView.findViewById(R.id.sex1);
//        editText8.setText(info6);
//        editText8.setEnabled(false);
        return rootView;
    }
    
}