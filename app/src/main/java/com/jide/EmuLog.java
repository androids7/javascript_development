package com.jide;

import java.io.File;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

/**
 * 日志打印工具
 * 未完善，暂时不用
 */
public class EmuLog {
    public static boolean isShowLog = false;
    private static Toast m_toast = null;
    private static Context mContext;
    private static String bugFile;

    public EmuLog(Context context,boolean isShow) {

        isShowLog = isShow;
        mContext = context;
        bugFile = Environment.getExternalStorageDirectory().toString()+"/MIDE/debug.txt";
    }

    // 路径，内容，编码
    public static void writeFile(String path, String content, String encoding)
    throws IOException {
        File file = new File(path);
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                                                       new FileOutputStream(file), encoding));
        writer.write(content);
        writer.close();
    }

    public static void log(String Tag,String Msg)
    {
        try {
            writeFile(bugFile,"MIDE正在运行，bug\n","UTF-8");
            writeFile(bugFile,Tag + ":" + Msg+"\n","UTF-8");
        }
        catch(IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void i(String tag, String msg) {
        if (isShowLog) {
            log(tag, msg!=null? msg : "");
        }
    }

    public static void d(String tag, String msg) {
        if (isShowLog) {
            log(tag, msg!=null? msg : "");
        }
    }

    public static void e(String tag, String msg) {
        log(tag, msg!=null? msg : "");
    }

    public static void v(String tag, String msg) {
        log(tag, msg!=null? msg : "");
    }

    public static void w(String tag, String msg) {
        if (isShowLog) {
            log(tag, msg!=null? msg : "");
        }
    }

    public static void showScreenLog(final Activity activity, final String info) {
        if (isShowLog) {
            activity.runOnUiThread(new Runnable() {
                    public void run() {
                        if (m_toast == null) {
                            //避免每次新建 Toast
                            m_toast = Toast.makeText(activity, info, Toast.LENGTH_LONG);
                        } else {
                            m_toast.setText(info);
                        }
                        m_toast.show();
                    }
                });
        }
	}
	
}
