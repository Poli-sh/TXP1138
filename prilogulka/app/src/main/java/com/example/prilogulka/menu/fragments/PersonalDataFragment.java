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
import com.example.prilogulka.SharedPreferencesManager;
import com.example.prilogulka.data.User;
import com.example.prilogulka.data_base.UserInfoDataBase;
import com.example.prilogulka.data_base.UserInfoDataBaseImpl;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class PersonalDataFragment extends Fragment{

    SharedPreferencesManager spManager;
    UserInfoDataBase userDB;

    EditText editName, editSurname, editCity, editDay, editMonth, editYear;
    Spinner spinner;

    final String LOG_TAG = "PERSONAL_DATA_FRAGMENT";

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        spManager = new SharedPreferencesManager();
        spManager.initUserInfoStorer(getContext());

        userDB = new UserInfoDataBaseImpl(getContext());
        String email = spManager.getStringFromSharedPreferences("active_user");
        List<User> userList = userDB.findUserInfo(userDB.COLUMN_EMAIL, email);
        User user;

        String info = "", info1 = "", info2 = "", info3 = "", info4 = "", info5 = "", info6 = "";
        if (userList.size() == 1) {
            user = userList.get(0);

            info = user.getName();
            info1 = user.getSurname();
            info2 = user.getCity();

            String birthday = user.getBirthday();
            info3 = birthday.substring(0,2);
            info4 = birthday.substring(3,5);
            info5 = birthday.substring(6, birthday.length());

            info6 = user.getSex();

            Log.i("PERSONAL_DATA_FRAGMENT", info6 + "");
        }
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
        spinner.setSelection(info6.equals("муж") ? 0 : 1);

        return rootView;
    }
}