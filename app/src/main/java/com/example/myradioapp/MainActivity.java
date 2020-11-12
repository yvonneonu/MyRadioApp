package com.example.myradioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {
    SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playBackPosition = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = findViewById(R.id.textName);
        PlayerView playerView = findViewById(R.id.lineVisualiser);
        text.setText("coolFm");

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24){
            releasePlayer();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24){
            releasePlayer();
        }
    }



    private void releasePlayer(){
        if (player !=null){
            playWhenReady = player.getPlayWhenReady();
            playBackPosition = player.getContentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }

    }
    private void initializePlayer() {
        player = new SimpleExoPlayer.Builder(this).build();
        MediaItem mediaItem = MediaItem.fromUri(getString(R.string.url));
        player.setMediaItem(mediaItem);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playBackPosition);
        player.prepare();

    }


}

