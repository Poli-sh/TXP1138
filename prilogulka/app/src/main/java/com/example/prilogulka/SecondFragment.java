package com.example.prilogulka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SecondFragment extends Fragment {

    int money;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_second, container, false);
        final EditText editText = rootView.findViewById(R.id.money);
        money = getArguments().getInt("money",money);
        editText.setText(money+"");//подгружает данные о просмотрах из MainActivity
        editText.setEnabled(false);

        final Button btnBack = rootView.findViewById(R.id.back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("money1",money);//возвращает данные в счетчик для дальнейших начислений
                startActivity(intent);

            }
        });

        return rootView;
    }
}