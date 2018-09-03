package com.example.prilogulka.menu.fragments;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.prilogulka.R;

import java.util.Random;


public class WatchingVideoFragment extends Fragment implements View.OnClickListener,
        MediaPlayer.OnCompletionListener{

    /**
     * TODO: Сделать сохранение денег при переходе между фрагментами и выходом из приложения.
     * TODO: Добавить БД, в которой отображены id просмотренных видеозаписей.
     */


    String CLASS_TAG = "Watching_Video_Fragment".toUpperCase();

    int money = 0;
    Uri uriList[];
    int playingVideoIndex = 0;

    Button btnStart, btnStop, btnNext;
    VideoView videoView;
    TextView textViewVideoCounter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_watching_video, container, false);

        initUIReference(rootView);
        textViewVideoCounter.setText(money + "");
        videoView.setVideoURI(Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.gu));

        Uri myUri1 = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.gu);
        Uri myUri2 = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.video1);
        Uri myUri3 = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.video2);
        Uri myUri4 = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.video3);


        uriList = new Uri[]{myUri1, myUri2, myUri3, myUri4};

        return rootView;
    }

    private void initUIReference(ViewGroup rootView){
        textViewVideoCounter = rootView.findViewById(R.id.textViewVideoCounter);

        videoView = rootView.findViewById(R.id.videoPlayer);
        videoView.stopPlayback();
        videoView.setOnCompletionListener(this);

        btnStart = rootView.findViewById(R.id.button_play);
        btnStart.setEnabled(true);
        btnStart.setOnClickListener(this);

        btnStop = rootView.findViewById(R.id.button_stop);
        btnStop.setEnabled(true);
        btnStop.setOnClickListener(this);

        btnNext = rootView.findViewById(R.id.button_next);
        btnNext.setEnabled(true);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.button_play:
                Log.i(CLASS_TAG, "BTN_PLAY was pushed.");
                playVideo();
                break;
            case R.id.button_next:
                nextVideo();
                break;
            case R.id.button_stop:
                stopVideo();
                break;
        }
    }

    private void playVideo(){
        videoView.start();
    }
    private void nextVideo(){
        if (playingVideoIndex == uriList.length - 1)
            playingVideoIndex = 0;
        else
            playingVideoIndex ++;

        stopVideo();
        videoView.setVideoURI(uriList[playingVideoIndex]);
        playVideo();
    }
    private void stopVideo(){
        videoView.stopPlayback();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.i(CLASS_TAG, "VIDEO #" + playingVideoIndex + " was over, STARTING new video");
        money ++;

        textViewVideoCounter.setText(money+"");
        nextVideo();
    }
}



