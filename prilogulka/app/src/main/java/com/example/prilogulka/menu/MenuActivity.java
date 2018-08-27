package com.example.prilogulka.menu;


import android.net.Uri;
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
import android.widget.VideoView;

import com.example.prilogulka.R;
import com.example.prilogulka.menu.fragments.ConnectUsFragment;
import com.example.prilogulka.menu.fragments.GiftsManagerFragment;
import com.example.prilogulka.menu.fragments.HelpWithAppFragment;
import com.example.prilogulka.menu.fragments.NavigationMoneyFragment;
import com.example.prilogulka.menu.fragments.PersonalDataFragment;
import com.example.prilogulka.menu.fragments.WatchingVideoFragment;


//данный класс содержит выпадающее меню (с фрагментами) и VideoView с кнопками управления воспроизведением (в content_menu).

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Uri uriList[];
    int money = 0;

    /**
     *
     * TODO: см. письмо.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.flContent, new WatchingVideoFragment());
        tx.commit();

/**
 * TODO: нужно сделать функцию выбора отображаемых экранов.
 */

       // DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
//        final VideoView videoView = findViewById(R.id.videoPlayerMain);
//        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.gu));
//
//        setUriList();
//
//        final TextView tvVideoCounter = findViewById(R.id.textViewVideoCounter);//фиксирует просмотры видео, на базе которых идет начисление средств на счет пользователя
//        int money1 = getIntent().getIntExtra("money1",money);//переменная получает данные со счета если начисления уже были или передает значение переменной money=0
//        tvVideoCounter.setText(money1 + "");
//
//        Button btnStart = findViewById(R.id.start);
//        btnStart.setEnabled(true);
//        btnStart.setOnClickListener(new View.OnClickListener() {
//            short j = 0; // changing video in order
//
//            @Override
//            public void onClick(View view) {
//                Random random = new Random();
//                final int uriListIndex = random.nextInt(uriList.length);
//                for (int i = 0; i <= uriList.length; i++) {
//                    videoView.setVideoURI(uriList[uriListIndex]);
//                    videoView.start();
//                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//
//                        @Override
//                        public void onCompletion(MediaPlayer mediaPlayer) {
//                            Toast.makeText(getApplicationContext(), "Воспроизведение видео закончено",
//                                    Toast.LENGTH_LONG).show();
//                            int money1 = getIntent().getIntExtra("money1",money);
//                            if (money == money1) {
//                                money++;
//                                tvVideoCounter.setText(money + "");
//                            } else {
//                                money1++;
//                                tvVideoCounter.setText(money1 + "");
//                            }
//                        }
//                    });
//                }
//                switch (j) {
//                    case 0:
//                        j++;
//                        break;
//                    case 1:
//                        j--;
//                        break;
//                }
//                videoView.stopPlayback();
//                videoView.setVideoURI(uriList[j]);
//                videoView.start();
//            }
//        });
//
//        Button btnStop = findViewById(R.id.stop);
//        btnStop.setEnabled(true);
//        btnStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                videoView.stopPlayback();
//            }
//        });
//        Button btnNext = findViewById(R.id.nextV);
//        btnNext.setEnabled(true);
//
//
//        btnNext.setOnClickListener(new View.OnClickListener() {
//            int i = 0; // changing video in order
//            int a = 2;
//
//            public void onClick(View view) {
//                Random random = new Random();
//                final int uriListIndex = random.nextInt(uriList.length);
//                for (int i = 0; i <= uriList.length; i++) {
//                    videoView.setVideoURI(uriList[uriListIndex]);
//                    videoView.start();
//                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//
//                        @Override
//                        public void onCompletion(MediaPlayer mediaPlayer) {
//                            Toast.makeText(getApplicationContext(), "Воспроизведение видео закончено",
//                                    Toast.LENGTH_LONG).show();
//                            int money1 = getIntent().getIntExtra("money1",money);
//                            if (money == money1) {
//                                money++;
//                                tvVideoCounter.setText(money + "");
//                            } else {
//                                money1++;
//                                tvVideoCounter.setText(money1 + "");
//                            }
//                        }
//                });
//                }
//                switch (i) {
//                    case 0:
//                        i++;
//                        break;
//                    case 1:
//                        i--;
//                        i=i+a;
//                        break;
//
//                }
//                videoView.stopPlayback();
//                videoView.setVideoURI(uriList[i]);
//                videoView.start();
//            }
//        });
//
//        Button btnMoney = findViewById(R.id.moneyCase);//для обновления счета после всех просмотров. Кнопка передает данные во фрагмент "мой счет"
//        btnMoney.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                FragmentManager fm = getSupportFragmentManager();
//                android.support.v4.app.Fragment fragment1 = fm.findFragmentById(R.id.container);
//                if (fragment1 == null) {
//                    fragment1 = new NavigationMoneyFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("money", money);
//                    fragment1.setArguments(bundle);
//                    fm.beginTransaction().add(R.id.container, fragment1).commit();
//                }
//            }
//        });


    }

//    public void setUriList() {
//        Uri myUri1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.gu);
//        Uri myUri2 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video1);
//        Uri myUri3 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video2);
//        Uri myUri4 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video3);
//        uriList = new Uri[]{myUri1, myUri2, myUri3, myUri4};
//    }

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
        //VideoView videoView = findViewById(R.id.videoPlayerMain);
        //videoView.stopPlayback(); //в случае открытия меню видео останавливается

        Fragment fragment = null;
        //Class fragmentClass = null;


        int id = item.getItemId();

        switch (id){
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
            default:
                fragment = new WatchingVideoFragment();
                break;
/**
 * TODO: надо изменить названия фрагментов, а то студия почему-то не дает пока что это сделать.
 */
        }

//        if (id == R.id.nav_person) {
//
//            fragmentClass = PersonalDataFragment.class;
//
//        } else if (id == R.id.nav_money) {
//            fragmentClass = NavigationMoneyFragment.class;
//           if (fragment!=null){
//               fragment = new NavigationMoneyFragment();
//               Bundle bundle = new Bundle();
//               bundle.putInt("money", money);
//               fragment.setArguments(bundle);
//               fm.beginTransaction().add(R.id.container, fragment).commit();
//           }
//
//
//    } else if (id == R.id.nav_video) {
//            videoView.setVisibility(View.GONE);
//            fragmentClass = WatchingVideoFragment.class;
//
//        } else if (id == R.id.nav_manage) {
//            fragmentClass = GiftsManagerFragment.class;
//
//        } else if (id == R.id.nav_share) {
//            fragmentClass = FifthFragment.class;
//
//        } else if (id == R.id.nav_send) {
//            fragmentClass = SixthFragment.class;
//
//        }
//        try {
//            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

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






