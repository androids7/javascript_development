package as.mke.jscilent.Util;

import android.app.Application;

//系统工具类
public class SysTool
{
    //退出程序
    public static void exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    
}
