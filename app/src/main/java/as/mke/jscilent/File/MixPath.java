package as.mke.jscilent.File;

import android.content.*;
import java.io.*;
import android.os.*;
import android.widget.Toast;

public class MixPath
{
    static String SD_PATH=null;
    static String ROOT_DIR="JIDE/";
    static String APP_DIR=".JIDE/";
    static String DEBUG="debug.txt";
    
    static boolean isLoad=false;
    static boolean isRUN=false;


    //创建所需目录
    public static void createDir()
    {
        if(SD_PATH!=null && ROOT_DIR!=null)
        {
            File root = new File(getRootPath());
            if(!root.exists())
            {
                root.mkdirs();
            }
        }
        // 创建Data目录
        File data=new File(getFullName(APP_DIR));
        if(!data.exists())
        {
            data.mkdirs();
            File debug=new File(getFullName(APP_DIR+DEBUG));
            try {
                debug.createNewFile();
            }
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }
        
        //创建例程目录
        File lib=new File(getFullName("例程"));
        if(!lib.exists())
        {
            lib.mkdirs();
        }
    }

    public static void load()
    {
        if(SD_PATH==null)
        {
            SD_PATH=getSDPath();
            SD_PATH+="/";
        }
        if(ROOT_DIR==null)
            ROOT_DIR="JIDE/";
        isLoad=true;
    }

    public static String getSDPath()
    {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取sd卡目录
        }
        else 
        {
            return null;
        }
        return sdDir.toString();
    }

    //设置平台目录
    public static void setRootDir(String dir)
    {
        ROOT_DIR=dir;
    }

    //生成绝对路径名
    public static String getFullName(String path)
    {
        if(!isLoad) load();
        return SD_PATH+ROOT_DIR+path;
    }

    //获取平台目录
    public static String getRootPath()
    {
        if(!isLoad)load();
        return SD_PATH+ROOT_DIR;
    }

    //获取工程目录下的文本
    public static String getProjectText(String filename)
    {
        String text=null;
        String path= MixPath.getRootPath()+"/"+filename;

        int filelen;
        File file = new File(path);
        byte [] bytes;
        if(!file.isFile())
        {

        }
        try
        {
            FileInputStream input= new FileInputStream(path);
            try
            {
                filelen=input.available();
                bytes=new byte[filelen];
                input.read(bytes, 0, filelen);
                text=new String(bytes,"UTF-8");
                return text;
            }
            catch(IOException e)
            {}
        }
        catch(FileNotFoundException e)
        {}
        return null;
    }
    
    
}
