/**************************

  安卓接口

 **************************/

package com.jide;

import android.app.ActionBar;
import android.widget.AbsoluteLayout;
import android.widget.Toast;
import android.content.res.AssetManager;
import java.io.InputStream;
import org.apache.http.util.EncodingUtils;
import java.io.IOException;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.content.pm.ActivityInfo;
import android.view.WindowManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;
import android.widget.EditText;
import java.security.Identity;
import android.widget.ImageView;
import android.widget.Button;
import android.webkit.WebView;
import android.widget.VideoView;

public class Android
{
	Emulator emuActivity;
	ActionBar emuActionBar;
	AbsoluteLayout emuLayout;
    UIOnTouchListener emuClick;

	// 初始化
	public Android(Emulator activity,AbsoluteLayout layout)
	{
		emuActivity = activity;
        emuClick = new UIOnTouchListener(emuActivity);
		emuActionBar = emuActivity.getActionBar();
		emuLayout = layout;
	}

	// 设置title名字
	public void N2J_setTitle(String name)
	{
		emuActionBar.setTitle(name);
	}

	// 设置title
	public void N2J_setShowTitle(boolean isShow)
	{
		emuActionBar.setDisplayShowTitleEnabled(isShow);
	}

	// 设置logo
	public void N2J_setShowLogo(boolean isShow)
	{
		emuActionBar.setDisplayShowHomeEnabled(isShow);
	}

	// 获取Assets里面的c文件，以备以后做运行器做准备
	public String N2J_getCFromAssets(String fileString)
	{
		String text;
		String s = "";
		AssetManager mAssetManager = emuActivity.getResources().getAssets();
		try
		{
			InputStream InputStream = mAssetManager.open(fileString);
			byte[] buf = new byte[InputStream.available()];
			InputStream.read(buf);
			text=EncodingUtils.getString(buf, "UTF-8");
			InputStream.close();
			return text;
		}
		catch(IOException errIOException)
		{
			text=s;
		}

		return text;
	}

	// 获取apk包完整路径
	String N2J_getPackagePath()
	{
		return  emuActivity.getPackageResourcePath();
	}



	// linux命令，需要root权限
	int N2J_rootCmd(String cmd)
	{
		if(Cmd.RootCmd(cmd))
		{
			return 0;
		}
		else
		{
			return -1;
		}
	}

	// 运行linux命令
	int N2J_runCmd(String cmd)
	{
		if(Cmd.runCommand(cmd))
		{
			return 0;
		}
		else
		{
			return -1;
		}
	}

    // 获取总字符串中的尾字符串，不包含起始字符串
	public String N2J_getEndStr(String allStr,String startStr)
	{
		int last=allStr.lastIndexOf(startStr);
		String name = allStr.substring(last+1,allStr.length());
		return name;
	}

	// 获取总字符串中的尾字符串，包含起始字符串
	public String N2J_getEndStr2(String allStr,String startStr)
	{
		int last=allStr.lastIndexOf(startStr);
		String name = allStr.substring(last,allStr.length());
		return name;
	}

	// 删除指定id对应的控件
	int N2J_removeView(int id)
	{
		emuLayout.removeView(emuActivity. findViewById(id));
		return 0;
	}


	// 设置屏幕方向 -1竖屏 0横屏
	public void N2J_setOrientation(int type)
	{
		//设置竖屏模式
		emuActivity.setRequestedOrientation(type);
	}

	//是否隐藏状态栏
	public void N2J_setNoLimits(int isNo)
	{
		WindowManager.LayoutParams attrs;
		attrs=emuActivity. getWindow().getAttributes();
		attrs.flags|=WindowManager.LayoutParams.FLAG_FULLSCREEN;
		emuActivity.getWindow().setAttributes(attrs); 
		if(isNo!=0) //设置全屏
		{
			emuActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
		else
		{
			emuActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS); 
		}
	}

	public void N2J_setBackground(int color)
	{
		emuLayout.setBackground(new ColorDrawable(color));
	}

    // 增加一个文本控件，id，是否滚动:0滚动，宽，高
    public void N2J_addTextView(int id,String text,int x,int y,int width,int height,int isScroll)
    {
        LayoutParams dis = new LayoutParams(width,height,x,y);
        if(isScroll==0){
        ScrollView sView = new ScrollView(emuActivity);
        TextView emuText = new TextView(emuActivity);
        sView.addView(emuText);
        emuText.setId(id);
        emuText.setText(text);
        emuLayout.addView(sView,dis);
        }
        else{
            TextView emuText = new TextView(emuActivity);
            emuText.setId(id);
            emuText.setText(text);
            emuLayout.addView(emuText,dis);
        }
    }
    
    public void N2J_addImageView(int id,int x,int y,int w,int h)
    {
        LayoutParams dis = new LayoutParams(w,h,x,y);
        ImageView emuImage = new ImageView(emuActivity);
        emuImage.setId(id);
        
        emuLayout.addView(emuImage,dis);
    }
    
    public void N2J_addButton(int id,String text,int x,int y,int w,int h)
    {
        LayoutParams dis = new LayoutParams(w,h,x,y);
        Button emuButton = new Button(emuActivity);
        emuButton.setText(text);
        emuButton.setId(id);
        emuButton.setOnTouchListener(emuClick);
        emuLayout.addView(emuButton,dis);
    }
    
    public void N2J_addWebView(int id,String url,int x,int y,int w,int h)
    {
        LayoutParams dis = new LayoutParams(w,h,x,y);
        WebView emuWeb = new WebView(emuActivity);
        emuWeb.setId(id);
        emuWeb.loadUrl(url);
        emuLayout.addView(emuWeb,dis);
    }
	
    public void N2J_addVideoView(int id,String url,int x,int y,int w,int h)
    {
        LayoutParams dis = new LayoutParams(w,h,x,y);
        VideoView emuVideo = new VideoView(emuActivity);
        emuVideo.setId(id);
        
        emuLayout.addView(emuVideo,dis);
    }
    
    // 设置控件的text
    public void N2J_setText(int id,String text,int type)
    {
        switch(type)
        {
                case 0:
                TextView textView = (TextView)emuActivity.findViewById(id);
                textView.setText(text);
                break;
                case 1:
                Button button = (Button)emuActivity.findViewById(id);
                button.setText(text);
        }
    }
    
    // 获取控件的text
    public String N2J_getText(int id,int type)
    {
        String text = null;
        switch(type)
        {
                case 0:
                TextView textView = (TextView)emuActivity.findViewById(id);
                text = textView.getText().toString();
                break;
                case 1:
                Button button = (Button)emuActivity.findViewById(id);
                text = button.getText().toString();
        }
        return text;
    }
    
}
