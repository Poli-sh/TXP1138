package com.example.prilogulka.menu.fragments;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.prilogulka.R;
import com.example.prilogulka.menu.MenuActivity;

public class NavigationMoneyFragment extends Fragment {

    int money;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_navigation_money, container, false);
        final EditText editText = rootView.findViewById(R.id.money);
        money = 0;//getArguments().getInt("money",money);
        editText.setText(money+"");//подгружает данные о просмотрах из MenuActivity
        editText.setEnabled(false);

        final Button btnBack = rootView.findViewById(R.id.back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentManager.beginTransaction().replace(R.id.flContent, new WatchingVideoFragment()).commit();
                fragmentTransaction.commitAllowingStateLoss();

                DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                //Intent intent = new Intent(getActivity(), MenuActivity.class);
                //intent.putExtra("money1",money);//возвращает данные в счетчик для дальнейших начислений
                //startActivity(intent);

            }
        });

        return rootView;
    }
}