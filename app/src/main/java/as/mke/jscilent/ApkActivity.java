package as.mke.jscilent;
import android.app.*;
import android.os.*;
import java.io.*;
import android.content.*;
import dalvik.system.*;
import java.lang.reflect.*;
import android.widget.*;
import java.util.*;

public class ApkActivity extends Activity
{
	public static Method hello=null;

	static Object lib = null;
	
	ArrayList list;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		
		
		list=new ArrayList();
	}
	
	public void complier()
	{

		DexClassLoader loader = new DexClassLoader("/data/data/"+getBaseContext().getPackageName()+"/cache/mao.dex", 
												   "/data/data/"+getBaseContext().getPackageName()+"/cache/", null, getClassLoader());

		try {
			Class<?> AXmlDecoder = loader.loadClass("mao.res.AXmlDecoder");

			
				lib = (Object) AXmlDecoder.newInstance();
				Class[] pTypes1 = new Class[1];
				pTypes1[0] = String.class;
				
				
					hello = AXmlDecoder.getDeclaredMethod("read", pTypes1);
				} catch (NoSuchMethodException e)
				{
					e.printStackTrace();
				}


			
		 catch (Exception e) {
			// TODO Auto-generated catch block
			 Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		 
		}
		//Toast.makeText(this, ""+hello("android动态加载jar"), Toast.LENGTH_LONG).show();
	}


	public String hello(String str) {
		try {
			return (String) hello.invoke(lib, str);
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		
		}
		return str;
	}
	
	
	
	
	public static void copyJar(Context c){

	    try{

			File solocalFile = new File("/data/data/"+c.getPackageName()+"/cache/mao.dex");

			byte[] arrayOfByte = new byte[65536];
			BufferedInputStream localBufferedInputStream = new 
				BufferedInputStream(c.getAssets().open("mao.dex"));
			BufferedOutputStream localBufferedOutputStream = new 
				BufferedOutputStream(new FileOutputStream(solocalFile));
			while (true)
			{
				int i = localBufferedInputStream.read(arrayOfByte);
				if (i <= 0)
				{
					localBufferedOutputStream.flush();
					localBufferedOutputStream.close();
					localBufferedInputStream.close();
					return;
				}
				localBufferedOutputStream.write(arrayOfByte, 0, i);
			}
	    }
	    catch (Exception localException)
	    {
			localException.printStackTrace();
	    }
	}
	
	
}
