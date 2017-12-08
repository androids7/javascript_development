//内置视频播放器
package com.jide;

import java.io.File;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.app.ActionBar;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.view.WindowManager;
import android.graphics.drawable.ColorDrawable;
import android.content.Intent;
import android.os.PowerManager;
import android.os.Handler;
import android.os.Message;
import as.mke.jscilent.R;
import android.widget.VideoView;
import java.io.IOException;
import android.net.Uri;
import android.widget.TextView;

public class VideoActivity extends Activity
{
    private VideoView mVideoView;
    private TextView mTextView;
    private LinearLayout mToolBar;
    private PowerManager.WakeLock wl;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getWindow().setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));
        //设置屏幕不熄灭
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        //MyTag可以随便写
        wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "MyTag");
        mVideoView = (VideoView) findViewById(R.id.videoView);
        mTextView = (TextView) findViewById(R.id.videoName);
        mToolBar = (LinearLayout) findViewById(R.id.videotoolbar);
        // 获取视频文件地址
        Bundle mBundle = this.getIntent().getExtras();
        String path = mBundle.getString("path");
        int last = path.lastIndexOf("/");
        String name = path.substring(last+1,path.length());
        mTextView.setText(name);
        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(this, "视频文件路径错误", 0).show();
            return;
        }
            // 设置播放的视频源
            mVideoView.setVideoPath(file.getAbsolutePath());
            mVideoView.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {

            case MotionEvent.ACTION_DOWN:
                mToolBar.setVisibility(View.VISIBLE);
                break;
            case MotionEvent.ACTION_MOVE:
                mToolBar.setVisibility(View.GONE);
        }
        return true;
    }
     
    @Override
    protected void onResume()
    {
        wl.acquire();
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        wl.release();
        super.onPause();
    }
    
}

