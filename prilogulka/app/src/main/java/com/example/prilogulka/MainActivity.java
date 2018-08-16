package com.example.prilogulka;


import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import java.util.Random;


//данный класс содержит выпадающее меню (с фрагментами) и VideoView с кнопками управления воспроизведением (в content_two).

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Uri uriList[];
    int money = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final VideoView videoView = findViewById(R.id.videoPlayerMain);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.gu));

        setUriList();

        final TextView tvVideoCounter = findViewById(R.id.textViewVideoCounter);//фиксирует просмотры видео, на базе которых идет начисление средств на счет пользователя
        int money1 = getIntent().getIntExtra("money1",money);//переменная получает данные со счета если начисления уже были или передает значение переменной money=0
        tvVideoCounter.setText(money1 + "");

        Button btnStart = findViewById(R.id.start);
        btnStart.setEnabled(true);
        btnStart.setOnClickListener(new View.OnClickListener() {
            short j = 0; // changing video in order

            @Override
            public void onClick(View view) {
                Random random = new Random();
                final int uriListIndex = random.nextInt(uriList.length);
                for (int i = 0; i <= uriList.length; i++) {
                    videoView.setVideoURI(uriList[uriListIndex]);
                    videoView.start();
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Toast.makeText(getApplicationContext(), "Воспроизведение видео закончено",
                                    Toast.LENGTH_LONG).show();
                            int money1 = getIntent().getIntExtra("money1",money);
                            if (money == money1) {
                                money++;
                                tvVideoCounter.setText(money + "");
                            } else {
                                money1++;
                                tvVideoCounter.setText(money1 + "");
                            }
                        }
                    });
                }
                switch (j) {
                    case 0:
                        j++;
                        break;
                    case 1:
                        j--;
                        break;
                }
                videoView.stopPlayback();
                videoView.setVideoURI(uriList[j]);
                videoView.start();
            }
        });

        Button btnStop = findViewById(R.id.stop);
        btnStop.setEnabled(true);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.stopPlayback();
            }
        });
        Button btnNext = findViewById(R.id.nextV);
        btnNext.setEnabled(true);


        btnNext.setOnClickListener(new View.OnClickListener() {
            int i = 0; // changing video in order
            int a = 2;

            public void onClick(View view) {
                Random random = new Random();
                final int uriListIndex = random.nextInt(uriList.length);
                for (int i = 0; i <= uriList.length; i++) {
                    videoView.setVideoURI(uriList[uriListIndex]);
                    videoView.start();
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Toast.makeText(getApplicationContext(), "Воспроизведение видео закончено",
                                    Toast.LENGTH_LONG).show();
                            int money1 = getIntent().getIntExtra("money1",money);
                            if (money == money1) {
                                money++;
                                tvVideoCounter.setText(money + "");
                            } else {
                                money1++;
                                tvVideoCounter.setText(money1 + "");
                            }
                        }
                });
                }
                switch (i) {
                    case 0:
                        i++;
                        break;
                    case 1:
                        i--;
                        i=i+a;
                        break;

                }
                videoView.stopPlayback();
                videoView.setVideoURI(uriList[i]);
                videoView.start();
            }
        });

        Button btnMoney = findViewById(R.id.moneyCase);//для обновления счета после всех просмотров. Кнопка передает данные во фрагмент "мой счет"
        btnMoney.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                android.support.v4.app.Fragment fragment1 = fm.findFragmentById(R.id.container);
                if (fragment1 == null) {
                    fragment1 = new SecondFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("money", money);
                    fragment1.setArguments(bundle);
                    fm.beginTransaction().add(R.id.container, fragment1).commit();
                }
            }
        });


    }

    public void setUriList() {
        Uri myUri1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.gu);
        Uri myUri2 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video1);
        Uri myUri3 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video2);
        Uri myUri4 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video3);
        uriList = new Uri[]{myUri1, myUri2, myUri3, myUri4};
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.two, menu);
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
        VideoView videoView = findViewById(R.id.videoPlayerMain);
        videoView.stopPlayback(); //в случае открытия меню видео останавливается

        android.support.v4.app.Fragment fragment = null;
        Class fragmentClass = null;


        int id = item.getItemId();
        FragmentManager fm = getSupportFragmentManager();


        if (id == R.id.nav_person) {

            fragmentClass = FirstFragment.class;

        } else if (id == R.id.nav_money) {
            fragmentClass = SecondFragment.class;
           if (fragment!=null){
               fragment = new SecondFragment();
               Bundle bundle = new Bundle();
               bundle.putInt("money", money);
               fragment.setArguments(bundle);
               fm.beginTransaction().add(R.id.container, fragment).commit();
           }


        } else if (id == R.id.nav_video) {
            videoView.setVisibility(View.GONE);
            fragmentClass = ThirdFragment.class;

        } else if (id == R.id.nav_manage) {
            fragmentClass = FourthFragment.class;

        } else if (id == R.id.nav_share) {
            fragmentClass = FifthFragment.class;

        } else if (id == R.id.nav_send) {
            fragmentClass = SixthFragment.class;

        }
        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}






