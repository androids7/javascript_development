package com.jide;

import java.io.DataOutputStream;

/***************************

linux命令 , Android.java调用

***************************/


public class Cmd
{
   public static boolean RootCmd(String cmd)
   {
      // 运行需要root权限的命令
      Process process = null;  
      DataOutputStream os = null;  
      try
      {  
         process=Runtime.getRuntime().exec("su");  
         os=new DataOutputStream(process.getOutputStream());  
         os.writeBytes(cmd+"\n");  
         os.writeBytes("exit\n");  
         os.flush();  
         process.waitFor();  

      }
      catch(Exception e)
      {  
         return false;  
      }
      finally
      {  

         try
         {  
            if(os!=null)
            {  
               os.close();  
            }  
            process.destroy();  
         }
         catch(Exception e)
         {  
         }  
      }  
      return true;  
   }


   public static boolean runCommand(String command)
   {
      // 运行普通linux命令  
      Process process = null;  
      try
      {  
         process=Runtime.getRuntime().exec(command);  
         process.waitFor();  
      }
      catch(Exception e)
      {  
         return false;  
      }
      finally
      {  
         try
         {  
            process.destroy();  
         }
         catch(Exception e)
         {  
            // 这里处理异常
         }  
      }  
      return true;  
   }

   
	
}
