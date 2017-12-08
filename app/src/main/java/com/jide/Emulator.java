/**************************

 运行器主文件

 **************************/
 
package com.jide;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import android.view.WindowManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.widget.AbsoluteLayout;
import android.view.MotionEvent;
import android.view.KeyEvent;
import android.view.Display;
import java.io.File;
import android.os.Environment;
import android.content.SharedPreferences;
import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.widget.FrameLayout;
import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.TextView;
import android.telephony.gsm.SmsManager;
import android.app.PendingIntent;
import java.util.Iterator;
import android.os.Message;
import as.mke.jscilent.BaseActivity;
import as.mke.jscilent.File.MixPath;


public class Emulator extends BaseActivity 
{
   //加载库
    static {
       System.loadLibrary("MIDE");
    }
    
    //声明底层方法
   public native void native_create(EmuView screen,Audio audio);
   public native void native_createAndroid(Android android);
   public native void native_exit();
   public native void native_init();
   public native void native_pause();
   public native void native_resume();
   public native void native_event(int type,int p,int q);
   public native void native_setRunC(String file);
   public native void native_setRootDir(String path);
   public native void native_setScreen(int width,int height);
   public native void native_lockBitmap();
   public native void native_unLockBitmap();
   
   
   //一些全局变量
   String TAG = "Emulator";
   Emulator emuActivity;
   Thread emuThread;
   viewHandler emuHandler;
   Android emuAndroid;
   AbsoluteLayout emuLayout;
   FrameLayout emuFrameLayout;
   EmuView emuView;
   Audio emuAudio;
   
   SharedPreferences emuSp;
   SharedPreferences.Editor emuEditor;
   
    public static final int 
    INIT = 1000,
    PAUSE = 1001,
    RESUME = 1002,
    REFRESH = 1003;
    
    int width;
    int height;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		Display wm = getWindowManager().getDefaultDisplay();
        emuActivity = this;
        emuAudio = new Audio(this);
        emuLayout = new AbsoluteLayout(this);
        emuFrameLayout = new FrameLayout(this);
        emuAndroid = new Android(this,emuLayout);
        emuView = new EmuView(this);
		emuView.setActivity(this);
		width = wm.getWidth();
		height = wm.getHeight();
        native_setScreen(width,height);
        emuFrameLayout.addView(emuView);
        emuFrameLayout.addView(emuLayout);
        native_create(emuView,emuAudio);
        native_createAndroid(emuAndroid);
        //设置运行的文件
        native_setRootDir(MixPath.getSDPath());
        native_setRunC(getPath("runfile"));
        setContentView(emuFrameLayout);   
        emuHandler = new viewHandler(this);
        emuHandler.sendEmptyMessage(INIT);
    }
    
    
    class viewHandler extends Handler
    {

        Emulator activity;
        public viewHandler(Emulator activity )
        {
            super();
            this.activity = activity;
        }

        public void handleMessage(Message msg) {
            switch(msg.what){
                    case INIT:native_init();
                    break;
                    case PAUSE:native_pause();
                    break;
                    case RESUME:native_resume();
                    break;
                    case REFRESH:emuView.N2J_refresh(0,0,width,height);
            }
            super.handleMessage(msg);
        }

    }

    @Override
    protected void onPause ( )
    {
       emuHandler.sendEmptyMessage(PAUSE);
       super.onPause( );
    }

    @Override
    protected void onResume ( )
    {
       emuHandler.sendEmptyMessage(RESUME);
       super.onResume( );
    }
   
   //获取Intent路径
   public String getPath(String name)
   {
      String path = getIntent().getStringExtra(name);
      return path;
   }
   
   static final int
   KY_DOWN=0, //按键按下
   KY_UP=1, //按键释放 
   MS_DOWN=2, //鼠标按下 
   MS_UP=3, //鼠标释放 
   MENU_SLT=4, //菜单选择 
   MENU_RET=5, //菜单返回 
   MR_DIALOG=6, //对话框
   MS_MOVE=12 ;//鼠标移动

   
   //重写触屏事件
   public boolean onTouchEvent(MotionEvent event)
   {
      // 获得触摸的坐标

      int p1 = (int)event.getX();
      int p2 = (int)event.getY()-getActionBar().getHeight()-20;
      int type=-1;
      //触屏事件
      switch(event.getAction())
      {

         case MotionEvent.ACTION_DOWN:
            type=MS_DOWN;
            emuActivity.native_event(type, p1 , p2);
            break;
         case MotionEvent.ACTION_MOVE:
            type=MS_MOVE;
            emuActivity.native_event(type, p1, p2);
            break;
         case MotionEvent.ACTION_UP:
            type=MS_UP;
            emuActivity.native_event(type, p1, p2);
            break;

      }
      return true;
   }
   

   
    
        
    public int KEY_TYPE=1;
    public boolean onKeyDown(int keycode, KeyEvent event)
    {
        int key=keycode;
        KEY_TYPE=0;
        switch(keycode)
        {
            case KeyEvent.KEYCODE_BACK:
                key=26;
                break;
            case KeyEvent.KEYCODE_MENU:
                key=25;
                break;
  
            case KeyEvent.KEYCODE_0:
                key=0;
                break;
            case KeyEvent.KEYCODE_1:
                key=1;
                break;
            case KeyEvent.KEYCODE_2:
                key=2;
                break;
            case KeyEvent.KEYCODE_3:
                key=3;
                break;
            case KeyEvent.KEYCODE_4:
                key=4;
                break;
            case KeyEvent.KEYCODE_5:
                key=5;
                break;
            case KeyEvent.KEYCODE_6:
                key=6;
                break;
            case KeyEvent.KEYCODE_7:
                key=7;
                break;
            case KeyEvent.KEYCODE_8:
                key=8;
                break;
            case KeyEvent.KEYCODE_9:
                key=9;
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                key=10;
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                key=11;
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                key=12;
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                key=13;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                key=14;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                key=15;
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                key=16;
                break;
        }
        emuActivity.native_event(KEY_TYPE, key,0);
        return true;
    }

   public boolean onKeyUp(int keycode, KeyEvent event)
   {
      int key=keycode;
      KEY_TYPE=1;
      switch(keycode)
      {
         case KeyEvent.KEYCODE_BACK:
            key=26;
            break;
         case KeyEvent.KEYCODE_MENU:
            key=25;
            break;
         
         case KeyEvent.KEYCODE_0:
            key=0;
            break;
         case KeyEvent.KEYCODE_1:
            key=1;
            break;
         case KeyEvent.KEYCODE_2:
            key=2;
            break;
         case KeyEvent.KEYCODE_3:
            key=3;
            break;
         case KeyEvent.KEYCODE_4:
            key=4;
            break;
         case KeyEvent.KEYCODE_5:
            key=5;
            break;
         case KeyEvent.KEYCODE_6:
            key=6;
            break;
         case KeyEvent.KEYCODE_7:
            key=7;
            break;
         case KeyEvent.KEYCODE_8:
            key=8;
            break;
         case KeyEvent.KEYCODE_9:
            key=9;
            break;
         case KeyEvent.KEYCODE_VOLUME_UP:
            key=10;
            break;
         case KeyEvent.KEYCODE_VOLUME_DOWN:
            key=11;
            break;
         case KeyEvent.KEYCODE_DPAD_UP:
            key=12;
            break;
         case KeyEvent.KEYCODE_DPAD_DOWN:
            key=13;
            break;
         case KeyEvent.KEYCODE_DPAD_LEFT:
            key=14;
            break;
         case KeyEvent.KEYCODE_DPAD_RIGHT:
            key=15;
            break;
         case KeyEvent.KEYCODE_DPAD_CENTER:
            key=16;
            break;
      }
      emuActivity.native_event(KEY_TYPE, key,0);
      return true;
   }
   
   
   
   /////////////////////////////////////////
   
   // 关闭Activity
   public void N2J_finish(){
      if(!emuActivity.isFinishing())
      emuActivity.finish();
   }
   
   // 调用系统浏览器访问网址
   void N2J_web(String http)
   {
      Uri uri = Uri.parse(http);
      Intent intent= new Intent(Intent.ACTION_VIEW,uri); 
      emuActivity.startActivity(intent);
   }
   
   // 获取系统时间
   public long N2J_getUptime()
   {
      return System.currentTimeMillis();
   }
   
   // 背光常亮
   public void N2J_lcdLong(int type)
   {
      if(type==1)
         emuActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
      else if(type==0)
         emuActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
   }

   // Toast显示
   public void N2J_toast(String msg){
      Toast.makeText(emuActivity,msg,Toast.LENGTH_SHORT).show();
   }
   
   //创建SharedPreferences
   public void N2J_spCreate(String name)
   {
      emuSp = getSharedPreferences(name,MODE_PRIVATE);
      emuEditor = emuSp.edit();
   }
   
   //写入int数据
   public void N2J_putInt(String name,int eint)
   {
      emuEditor.putInt(name,eint).commit();
   }
   
   //写入String数据
   public void N2J_putString(String name,String estring)
   {
      emuEditor.putString(name,estring).commit();
   }
   
   //获取int数据
   public int N2J_getInt(String name)
   {
      return emuSp.getInt(name,-1);
   }
   
   //获取int数据
   public String N2J_getString(String name)
   {
      return emuSp.getString(name,"");
   }
   
    // 内置浏览器
    public void N2J_webBrowser(String path)
    {
        Intent into=new Intent();
        into.setClass(Emulator.this,WebActivity.class);
        Bundle mBundle=new Bundle();
        mBundle.putString("http",path);
        into.putExtras(mBundle);
        startActivity(into);
    }
    
   // 内置视频播放器
   public void N2J_videoPlayer(String path)
   {
       Intent into=new Intent();
       into.setClass(Emulator.this,VideoActivity.class);
       Bundle mBundle=new Bundle();
       mBundle.putString("path",path);
       into.putExtras(mBundle);
       startActivity(into);
   }
   
   // 内置音乐播放器
   public void N2J_musicPlayer(String path,int loop)
   {
       Intent into=new Intent();
       into.setClass(Emulator.this,MusicActivity.class);
       Bundle mBundle=new Bundle();
       mBundle.putString("path",path);
       mBundle.putInt("loop",loop);
       into.putExtras(mBundle);
       startActivity(into);
   }
   
    // 发送短信 
    public int N2J_sendSms(String string, String string2, int flag) 
    {
        String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        SmsManager smsManager = SmsManager.getDefault();
        Intent intent = new Intent(SENT_SMS_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int)0, intent, (int)0);
        Intent intent2 = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, (int)0, intent2, (int)0);
        //隐藏状态报告
        if((flag>>3 & 1)==0)
        {
            N2J_toast("正在发送短信");
        }
        if (string2.length() > 70) 
        {
            Iterator iterator = smsManager.divideMessage(string2).iterator();
            do
            {
                if (!iterator.hasNext()) 
                {
                    return -1;
                }
                smsManager.sendTextMessage(string, (String)null, (String)iterator.next(), pendingIntent, pendingIntent2);
            } while (true);
        }
        smsManager.sendTextMessage(string, (String)null, string2, pendingIntent, pendingIntent2);
        return 0;
    }
    
}
