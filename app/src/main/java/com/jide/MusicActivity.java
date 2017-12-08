//内置音乐播放器
package com.jide;

import android.widget.SeekBar.OnSeekBarChangeListener;
import as.mke.jscilent.R;
import as.mke.jscilent.BaseActivity;
import android.media.MediaPlayer;
import android.app.ActionBar;
import android.widget.TextView;
import android.widget.SeekBar;
import android.os.Handler;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import java.io.File;
import java.io.IOException;
import android.widget.Toast;
import android.widget.VideoView;
import android.content.res.AssetManager;
import android.net.Uri;
import as.mke.jscilent.File.MixPath;

public class MusicActivity extends BaseActivity
{
    MediaPlayer sPlay = new MediaPlayer();
    TextView NowText;
    TextView AllText;
    SeekBar seekBar;
    boolean isPlaying;
    Handler mHandler;
    Button moveLast;
    Button moveNext;
    Button play;
    Bundle getData;
    VideoView mBackground;
    int mProgress;//进度条进度
    int mLoop;//循环标志

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        getData = this.getIntent().getExtras();
        mLoop = getData.getInt("loop");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        NowText=(TextView)findViewById(R.id.musicNowTime);
        AllText=(TextView)findViewById(R.id.musicAllTime);
        seekBar=(SeekBar)findViewById(R.id.musicSeekBar);
        moveLast=(Button)findViewById(R.id.musicMoveLast);
        moveNext=(Button)findViewById(R.id.musicMoveNext);
        play=(Button)findViewById(R.id.musicPlay);
        mBackground = (VideoView) findViewById(R.id.musicBackground);
        mBackground.setVideoPath(MixPath.getRootPath()+".MIDE/MusicBackground.mp4");
        mBackground.start();
        moveLast.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v)
                {
                    mProgress = mProgress-2000;
                    if (sPlay != null && sPlay.isPlaying() ) {
                        // 设置当前播放的位置
                        sPlay.seekTo(mProgress);
                    }
                }
            });
        moveNext.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v)
                {
                    mProgress = mProgress+2000;
                    if (sPlay != null && sPlay.isPlaying()) {
                        // 设置当前播放的位置
                        sPlay.seekTo(mProgress);
                    }
                }
            });
        play.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v)
                {
                    if (sPlay != null && sPlay.isPlaying()) {
                        sPlay.pause();
                        mBackground.pause();
                        play.setText("播放");
                    }
                    else{
                        sPlay.start();
                        mBackground.start();
                        play.setText("暂停");
                    }
                }
            });
        seekBar.setOnSeekBarChangeListener(change);
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch(msg.what){
                        case 1000:setTimeText();
                }
                super.handleMessage(msg);
            }
        };
        play(0);
    }
    
    
    public void play(int i)
    {
        String path = getData.getString("path");
        if(!new File(path).exists()){
            Toast.makeText(this,"音乐路径错误",Toast.LENGTH_SHORT).show();
        }
        else {
        int last = path.lastIndexOf("/");
        String name = path.substring(last+1,path.length());
        getActionBar().setTitle(name);
        //开始播放
        try {
            sPlay.setDataSource(path);
            sPlay.prepare();
        }
        catch(IllegalStateException e) {}
        catch(IOException e) {}
        catch(SecurityException e) {}
        catch(IllegalArgumentException e) {}
            // 循环播放
            if(mLoop == 1)
            {
                sPlay.setLooping(true);
            }
        sPlay.start();
        // 按照初始位置播放
        sPlay.seekTo(i);
        // 设置进度条的最大进度为音频流的最大播放时长
        seekBar.setMax(sPlay.getDuration());
        // 开始线程，更新进度条的刻度
        new Thread() {

            @Override
            public void run() {
                try {
                    isPlaying = true;
                    while (isPlaying) {
                        int current = sPlay
                            .getCurrentPosition();
                        seekBar.setProgress(current);
                        mProgress = current;
                        mHandler.sendEmptyMessage(1000);
                        sleep(500);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
       }
    }

    public void setTimeText(){
        int all = sPlay.getDuration();
        int now = sPlay.getCurrentPosition();
        AllText.setText(tran_time(all));
        NowText.setText(tran_time(now));
    }
    
    public String tran_time(int time){
        int m = time/1000/60;
        int s = time/1000%60;
        if (m<10){
            if (s<10){
                return "0" + m +":" + "0" + s ;
            }else
                return "0" + m +":" + s ;
        }else {
            if (s<10){
                return m +":" + "0" + s ;
            }else
                return m +":" + s ;
        }
    }
    
    private OnSeekBarChangeListener change = new OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // 当进度条停止修改的时候触发
            // 取得当前进度条的刻度
            int progress = seekBar.getProgress();
            mProgress = progress;
            if (sPlay != null && sPlay.isPlaying()) {
                // 设置当前播放的位置
                sPlay.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            
        }
    };
    
    //写结束事件
    @Override
    public void onBackPressed ( )
    {
        sPlay.stop();
        isPlaying = false;
        MusicActivity.this.finish();
        super.onBackPressed ( );
    }
    
}
