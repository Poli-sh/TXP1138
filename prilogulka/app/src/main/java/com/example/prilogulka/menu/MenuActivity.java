package com.example.prilogulka.menu;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.prilogulka.ListOfUserActionsFragment;
import com.example.prilogulka.ListOfUsersFragment;
import com.example.prilogulka.R;
import com.example.prilogulka.SharedPreferencesManager;
import com.example.prilogulka.data.User;
import com.example.prilogulka.data_base.UserInfoDataBase;
import com.example.prilogulka.data_base.UserInfoDataBaseImpl;
import com.example.prilogulka.login_signin.LoginActivity;
import com.example.prilogulka.menu.fragments.ConnectUsFragment;
import com.example.prilogulka.menu.fragments.GiftsManagerFragment;
import com.example.prilogulka.menu.fragments.HelpWithAppFragment;
import com.example.prilogulka.menu.fragments.NavigationMoneyFragment;
import com.example.prilogulka.menu.fragments.PersonalDataFragment;
import com.example.prilogulka.menu.fragments.WatchingVideoFragment;

import java.util.List;


//данный класс содержит выпадающее меню (с фрагментами) и VideoView с кнопками управления воспроизведением (в content_menu).

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferencesManager spManager;
    UserInfoDataBase userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        spManager = new SharedPreferencesManager();
        spManager.initUserInfoStorer(this);
        userDB = new UserInfoDataBaseImpl(this);
        String email = spManager.getStringFromSharedPreferences("active_user");
        List<User> userList = userDB.findUserInfo("email", email);//new User();
        User user = new User();
        if (userList.size() == 1)
            user = userList.get(0);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        TextView userNameHeader = headerView.findViewById(R.id.nav_header_user_name);
        userNameHeader.setText(user.getName() + " " + user.getSurname());

        TextView userEmailHeader = headerView.findViewById(R.id.nav_header_user_email);
        userEmailHeader.setText(user.getEmail());

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.flContent, new WatchingVideoFragment());
        tx.commit();
    }

    @Override
    public void onBackPressed() {
        /**
         * TODO: разобраться с нажатием кнопки НАЗАД на панели телефона
         * Выход из логина должен осуществляться либо по кнопке в меню
         * либо по кнопке "назад".
         */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_right_corner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.nav_person:
                fragment = new PersonalDataFragment();
                break;
            case R.id.nav_money:
                fragment = new NavigationMoneyFragment();
                break;
            case R.id.nav_video:
                fragment = new WatchingVideoFragment();
                break;
            case R.id.nav_gifts:
                fragment = new GiftsManagerFragment();
                break;
            case R.id.nav_help:
                fragment = new HelpWithAppFragment();
                break;
            case R.id.nav_connect_us:
                fragment = new ConnectUsFragment();
                break;
            /**
             * TODO: протестировать и убрать.
             */
            case R.id.list_of_users:
                fragment = new ListOfUsersFragment();
                break;
            case R.id.list_of_user_actions:
                fragment = new ListOfUserActionsFragment();
                break;

            default:
                fragment = new WatchingVideoFragment();
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        fragmentTransaction.commitAllowingStateLoss();
        item.setChecked(true);
        setTitle(item.getTitle());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}






