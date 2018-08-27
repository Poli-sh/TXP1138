package com.example.prilogulka.menu.fragments;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import com.example.prilogulka.R;

import java.util.Random;


public class WatchingVideoFragment extends Fragment {

    int money = 0;
// так как данные счета и видео должны совпадать подгружает MenuActivity.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_watching_video, container, false);
        //startActivity(new Intent(getActivity(), MenuActivity.class));


        //изначально был проработан отдельный фрагмент со своими View.
        final VideoView videoView = rootView.findViewById(R.id.videoPlayer);
        videoView.stopPlayback();
        videoView.setVideoURI(Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.gu));
        final Button button = rootView.findViewById(R.id.start1);
        button.setEnabled(true);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.start();
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        money++;

                    }

                });
            }
        });
        final Button button1 = rootView.findViewById(R.id.stop1);
        button1.setEnabled(true);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.stopPlayback();
            }
        });

        final Random random = new Random();
        Uri myUri1 = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.gu);
        Uri myUri2 = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.video1);
        Uri myUri3 = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.video2);
        Uri myUri4 = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.video3);


        final Uri uriList[] = {myUri1, myUri2, myUri3, myUri4};
        final int uriListIndex = random.nextInt(uriList.length);

        final Button button2 = rootView.findViewById(R.id.next);
        button2.setEnabled(true);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i <= uriList.length; i++) {
                    videoView.setVideoURI(uriList[uriListIndex]);
                    videoView.start();
                }
            }
        });


        return rootView;
    }
}






