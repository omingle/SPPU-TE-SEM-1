package com.sdlproject.mediaplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayerActivity extends AppCompatActivity {

    Button btnNext, btnPrevious, btnPause;
    TextView songLabel, runningTimeLabel, totalTimeLabel;
    SeekBar songSeekbar;

    static MediaPlayer myMediaPlayer;
    int position;

    String sname;

    int totalDuration = 0;
    int currentPosition = 0;

    ArrayList<File> mySongs;
    Thread updateSeekBar;

    private Handler myHandler = new Handler();
    Thread updateSongTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnNext = (Button)findViewById(R.id.next);
        btnPrevious = (Button)findViewById(R.id.previous);
        btnPause = (Button)findViewById(R.id.pause);
        songLabel = (TextView)findViewById(R.id.songLabel);
        songSeekbar = (SeekBar)findViewById(R.id.seekBar);
        runningTimeLabel = (TextView)findViewById(R.id.runningTimeLabel);
        totalTimeLabel = (TextView)findViewById(R.id.totalTimeLabel);

        // update seekbar
        updateSeekBar = new Thread(){
            @Override
            public void run() {
//                totalDuration = myMediaPlayer.getDuration();  // know selected song duration.
                currentPosition = 0;

                while(currentPosition < totalDuration){
                    try {
                        sleep(500); // step of seek bar
                        currentPosition = myMediaPlayer.getCurrentPosition();
                        songSeekbar.setProgress(currentPosition);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        // update Song time
        updateSongTime = new Thread() {
            @Override
            public void run() {
                currentPosition = myMediaPlayer.getCurrentPosition();

                runningTimeLabel.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) currentPosition),
                        TimeUnit.MILLISECONDS.toSeconds((long) currentPosition) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) currentPosition)))
                );

                myHandler.postDelayed(this, 100);
            }
        };

        if(myMediaPlayer != null){
            myMediaPlayer.stop();
            myMediaPlayer.release();
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");

        sname = mySongs.get(position).getName().toString();

        String songName = intent.getStringExtra("songname");

        songLabel.setText(songName); // display song title
        songLabel.setSelected(true);

        position = bundle.getInt("pos", 0);

        Uri u = Uri.parse(mySongs.get(position).toString());

        myMediaPlayer = MediaPlayer.create(getApplicationContext(), u);
        myMediaPlayer.start();
        songSeekbar.setMax(myMediaPlayer.getDuration());

        totalDuration = myMediaPlayer.getDuration();
        currentPosition = 0;

        runningTimeLabel.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) currentPosition),
                TimeUnit.MILLISECONDS.toSeconds((long) currentPosition) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                toMinutes((long) currentPosition)))
        );

        totalTimeLabel.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) totalDuration),
                TimeUnit.MILLISECONDS.toSeconds((long) totalDuration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                toMinutes((long) totalDuration)))
        );

        updateSeekBar.start();  // increasing seekBar(made seekBar movable, when song play) <------

        myHandler.postDelayed(updateSongTime, 100);

        // change color of seek-bar
        songSeekbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.MULTIPLY);
        songSeekbar.getThumb().setColorFilter(getResources().getColor(R.color.colorPink), PorterDuff.Mode.SRC_IN);

        // After playing song, Now apply listener on seekBar
        songSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myMediaPlayer.seekTo(seekBar.getProgress()); // apply change on duration, when seek bar move.
            }
        });


        // At last, Apply listener on pause,next and previous button.
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songSeekbar.setMax(myMediaPlayer.getDuration());

                if(myMediaPlayer.isPlaying()){
                    btnPause.setBackgroundResource(R.drawable.icon_play);
                    myMediaPlayer.pause();
                }else{
                    btnPause.setBackgroundResource(R.drawable.icon_pause);
                    myMediaPlayer.start();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myMediaPlayer.stop(); // first stop the music
                myMediaPlayer.release();
                // then, increase position of song
                position = (position+1) % mySongs.size(); // also consider boundary cases.

                Uri u = Uri.parse(mySongs.get(position).toString());
                myMediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                sname = mySongs.get(position).getName().toString();
                songLabel.setText(sname);

                myMediaPlayer.start(); // then, start song

                songSeekbar.setMax(myMediaPlayer.getDuration());

                totalDuration = myMediaPlayer.getDuration();
                currentPosition = 0;

                runningTimeLabel.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) currentPosition),
                        TimeUnit.MILLISECONDS.toSeconds((long) currentPosition) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) currentPosition)))
                );

                totalTimeLabel.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) totalDuration),
                        TimeUnit.MILLISECONDS.toSeconds((long) totalDuration) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) totalDuration)))
                );

                myHandler.postDelayed(updateSongTime, 100);

            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myMediaPlayer.stop(); // first stop the music
                myMediaPlayer.release();
                // then, decrease position of song
                position = ((position-1) < 0) ? (mySongs.size()-1):position-1; // also consider boundary case less than 0.

                Uri u = Uri.parse(mySongs.get(position).toString());
                myMediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                sname = mySongs.get(position).getName().toString();
                songLabel.setText(sname);

                myMediaPlayer.start(); // then, start song

                songSeekbar.setMax(myMediaPlayer.getDuration());

                totalDuration = myMediaPlayer.getDuration();
                currentPosition = 0;

                runningTimeLabel.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) currentPosition),
                        TimeUnit.MILLISECONDS.toSeconds((long) currentPosition) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) currentPosition)))
                );

                totalTimeLabel.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) totalDuration),
                        TimeUnit.MILLISECONDS.toSeconds((long) totalDuration) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) totalDuration)))
                );

                myHandler.postDelayed(updateSongTime, 100);

            }
        });

    }


    // Perform action on go back
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}