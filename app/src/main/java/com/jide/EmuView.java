package com.jide;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.os.HandlerThread;
import android.os.Handler;
import android.graphics.Color;
import android.os.Message;
import android.util.Log;
import android.graphics.Typeface;
import android.graphics.Rect;
import android.widget.Toast;
import android.view.WindowManager;
import android.graphics.RectF;

public class EmuView extends SurfaceView 
implements SurfaceHolder.Callback, Handler.Callback, Runnable
{
    int runType;
    @Override
    public void run()
    {
        switch(runType){
            case REFRESH:N2J_refresh(0,0,ScreenWidth,ScreenHeight);
            break;
            case PAUSE:mActivity.onPause();
            break;
            case RESUME:mActivity.onResume();
            break;
            case INIT://mActivity.native_init();
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch(msg.what){
            case REFRESH:
                reDraw();
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 视图创建
        createThread();
        isRunning = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,int p2,int p3,int p4) {
        // 视图改变
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // 视图销毁
        isRunning = false;
    }
    
    public EmuView(android.content.Context context) {
        super(context);
        initView();
    }

    public EmuView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context,attrs);
        initView();
    }

    public EmuView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr)  {
        super(context,attrs,defStyleAttr);
        initView();
    }
    
    public void setActivity(Emulator activity) {
        mActivity = activity;
    }

    
    SurfaceHolder mHolder;
    Bitmap mBitmap;
    Canvas mCanvas;
    Paint mPaint;
    HandlerThread mThread;
    Handler mHandler;
    boolean isRunning;
    Emulator mActivity;
    Typeface mTypeface;
    Rect mBack, mSrc, mDst, mRef;
    String TAG = "EmuView";
    
    public static final int 
    INIT = 1000,
    PAUSE = 1001,
    RESUME = 1002,
    REFRESH = 1003;
    
    int ScreenWidth;
    int ScreenHeight;

    int SCRW, SCRH;
    int Font_W, Font_H;
    Rect fontRect;
    
    // 初始化
    public void initView(){
        setDrawingCacheEnabled(false);
        setWillNotDraw(true);
        ScreenWidth = getWidth();
        ScreenHeight = getHeight();
        if(ScreenWidth == 0 && ScreenHeight == 0)
            {
                
            }
        mHolder = getHolder();
        mHolder.addCallback(this);
        mActivity = new Emulator();
        Log.i(TAG,"初始化画布成功");
        createBitmap();
        runType = INIT;
    }
    
    // 创建双缓冲画布
    public void createBitmap(){
        mCanvas = new Canvas();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(24);
        mPaint.setStyle(Paint.Style.FILL);
        // 创建屏幕区域
        mSrc = new Rect(0,0,540,960);
        mDst = new Rect(0,0,540,540*960/540);
        mRef = new Rect(0,0,0,0);
        mBack = new Rect(0,mDst.bottom,540,960);
        mBitmap = Bitmap.createBitmap(540,960,Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);
        Log.i(TAG,"创建双缓冲画布成功");
    }
    
    // 创建线程
    public void createThread(){
        mThread = new HandlerThread("drawView");
        mThread.start();
        mHandler = new Handler(mThread.getLooper(),this);
        post(this);
        mHandler.sendEmptyMessage(INIT);
        Log.i(TAG,"创建线程成功");
    }
    
    // 设置字体样式
    public void setTypeface(Paint paint){
        mTypeface = Typeface.createFromAsset(mActivity.getAssets(), "fonts/COUR.TTF");
        paint.setTypeface(mTypeface);
    }
    
    // 重绘画布
    public void reDraw()
    {
        Canvas canvas;
        canvas = mHolder.lockCanvas(mRef);
        if(canvas != null){
            canvas.drawRect(mBack,mPaint);
            canvas.drawBitmap(mBitmap,mSrc,mDst,mPaint);
        }
        mHolder.unlockCanvasAndPost(canvas);
    }
    
    // 绘制颜色清屏
    public void N2J_drawRGB(int r,int g,int b){
        mCanvas.drawRGB(r,g,b);
    }
    
    // 画矩形
    public void N2J_drawRect(int x,int y,int w,int h,int r,int g,int b){
        mPaint.setARGB(255,r,g,b);
        mCanvas.drawRect(x,y,x+w,y+h,mPaint);
    }
    
    // 画线
    public void N2J_drawLine(int x,int y,int xx,int yy,int r,int g,int b){
        mPaint.setARGB(255,r,g,b);
        mCanvas.drawLine(x,y,xx,yy,mPaint);
    }
    
    // 画点
    public void N2J_drawPoint(int x,int y,int r,int g,int b){
        mPaint.setARGB(255,r,g,b);
        mCanvas.drawPoint(x,y,mPaint);
    }
    
    // 画圆
    public void N2J_drawCircle(int x,int y,int round,int r,int g,int b){
        mPaint.setARGB(255,r,g,b);
        mCanvas.drawCircle(x,y,round,mPaint);
    }
    
    // 画透明矩形
    public void N2J_drawEffsetion(int x,int y,int w,int h,int r,int g,int b){
        mPaint.setARGB(128,r,g,b);
        mCanvas.drawRect(x,y,x+w,y+h,mPaint);
    }
    
    // 画字符
    public void N2J_drawText(String text,int x,int y,int r,int g,int b,int font){
        switch(font){
            case 0:mPaint.setTextSize(24);
            break;
            case 1:mPaint.setTextSize(28);
            break;
            case 2:mPaint.setTextSize(32);
        }
        if(font<0 || font >2){
            mPaint.setTextSize(28);
        }
        mPaint.setARGB(255,r,g,b);
        mCanvas.drawText(text,0,text.length(),x,y,mPaint);
    }
    
    // 获取字符宽高
    public void N2J_textWH(String text,int font,int w,int h){
        switch(font){
                case 0:mPaint.setTextSize(24);
                break;
                case 1:mPaint.setTextSize(28);
                break;
                case 2:mPaint.setTextSize(32);
        }
        if(font<0 || font >2){
            mPaint.setTextSize(28);
        }
        mPaint.getTextBounds(text,0,text.length(),fontRect);
        Font_W = fontRect.width();
        Font_H = fontRect.height();
        w = Font_W;
        h = Font_H;
    }
    
    // 刷屏
    public void N2J_refresh(int x,int y,int w,int h){
        mRef = new Rect(x,y,x+w,y+h);
        mHandler.sendEmptyMessage(REFRESH);
    }

}
