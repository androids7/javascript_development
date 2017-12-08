package as.mke.jscilent;

import android.app.*;
import android.os.*;
import android.annotation.SuppressLint;
import android.webkit.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import org.json.*;



@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity 
{
	
	public static int width,height;
	public Display mDisplay;
	
	
	
	private WebView web;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
        setContentView(R.layout.main);
		
		mDisplay=getWindowManager().getDefaultDisplay();
		
		width=mDisplay.getWidth();
		height=mDisplay.getHeight();
		
		web=(WebView)findViewById(R.id.mainWebView1);
		
		this.web.getSettings().setJavaScriptEnabled(true);
        this.web.getSettings().setBuiltInZoomControls(true);
        this.web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.web.getSettings().setAllowFileAccess(true);
        this.web.getSettings().setSupportZoom(true);
        this.web.getSettings().setBuiltInZoomControls(true);
        this.web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.web.getSettings().setDomStorageEnabled(true);
        this.web.getSettings().setDatabaseEnabled(true);
		
		web.setWebChromeClient(new WebChromeClient());
		web.setWebViewClient(new WebViewClient());
		web.loadUrl("file:///sdcard/.0/index.html");
		
		web.addJavascriptInterface(this,"app");
		
    }
	
	
	

	
	
	@JavascriptInterface
    @SuppressLint("JavascriptInterface")
    public void Toastshow(String str, int i) {
        Toast.makeText(MainActivity.this,str,i).show();
    }
	
	
	@JavascriptInterface
    @SuppressLint("JavascriptInterface")
    public String ReadFile(String str) {
        String str2="";
        
		try
		{
			ByteArrayOutputStream bao=new ByteArrayOutputStream();
			InputStream in=new FileInputStream(str);
			int temp=0;
			byte buf[]=new byte[1024];
			while((temp=in.read(buf))!=-1)
			{
				bao.write(buf,0,temp);
			}
			
			str2=new String(bao.toByteArray());
			in.close();
			bao.close();
		}
		catch (Exception e)
		{}
		
		return str2;
    }
	
	
	
	@JavascriptInterface
    @SuppressLint("JavascriptInterface")
    public void WriteFile(String str, String obj) {
		
		try
		{
			
		
        File file=new File(str);
		if(file.exists())
		{
			file.delete();
			file.createNewFile();
		}

		else{
			
			file.createNewFile();
		}
		
		FileWriter fw=new FileWriter(file);
		fw.write(obj);
		fw.close();
		
		
		}catch(Exception e)
		{
			toast(e.toString());
		}
		
    }
	
	
	@JavascriptInterface
    @SuppressLint("JavascriptInterface")
    public String FileList(String str) {
        
        
		
        
        JSONObject jSONObject = new JSONObject();
       
        
        JSONArray jSONArray = new JSONArray();
        
        
        JSONArray jSONArray2 = new JSONArray();
        
        File file = new File(str);
       
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (int i = 0; i < listFiles.length; i++) {
                    try {
                        if (listFiles[i].isDirectory()) {
							//文件夹
                            jSONArray2.put(listFiles[i]);
                        } else {
							//文件
                            jSONArray.put(listFiles[i]);
                        }
                         jSONObject.put("file", jSONArray);
                         jSONObject.put("dir", jSONArray2);
                    } catch (Exception e) {
                        toast(e.toString());
                    }
                }
            }
        }
        return jSONObject.toString();
    }
	
	
	
	
	public void toast(String text)
	{
		Toast.makeText(MainActivity.this,text,0).show();
	}
	
}
