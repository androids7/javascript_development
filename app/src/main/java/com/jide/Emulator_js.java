package com.jide;
import android.app.*;
import android.os.*;
import android.webkit.*;
import android.view.*;
import android.annotation.*;
import as.mke.jscilent.*;
import android.widget.*;
import java.io.*;
import org.json.*;
import android.util.*;

@SuppressLint("SetJavaScriptEnabled")

public class Emulator_js extends Activity
{

	public static int width,height;
	public Display mDisplay;

	
	
	
	private WebView web;
	
	
	private Env env;
	
	//将网页读取进来
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		
	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.engine);
		
		mDisplay=getWindowManager().getDefaultDisplay();

		width=mDisplay.getWidth();
		height=mDisplay.getHeight();

		web=(WebView)findViewById(R.id.engineWebView1);

		
		
			
		
		web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web.getSettings().setAllowFileAccess(true);
        web.getSettings().setSupportZoom(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setDatabaseEnabled(true);

		web.setWebChromeClient(new WebChromeClient());
		
		
		web.setWebViewClient(new WebViewClient() {
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) 
			{ 
			view.loadUrl(url); return true; 
			
			}
			@Override
			
			public WebResourceResponse shouldInterceptRequest(WebView view, String url)
			
			{ // TODO Auto-generated method stub //       
			//if (Build.VERSION.SDK_INT < 21) {
				
				
				
				if (url.contains("jquery.js"))

				{ 

					Log.i("result", url);
					return editResponse2();
				} //           
				//}
				//return super.shouldInterceptRequest(view, url);
			
			
				
				else if (url.contains("android.js"))
			
				{ 
			
				Log.i("result", url);
				return editResponse();
				} //           
				//}
				return super.shouldInterceptRequest(view, url);
				} 
				
				
				
				
				private WebResourceResponse editResponse()
				
				{ 
				
				try 
				{ 
				Log.i("result", "加载本地android.js"); 
				
				
				InputStream in=getAssets().open("android.js");
				
					byte[] byt = new byte[in.available()];
					in.read(byt);
				
				return new WebResourceResponse("application/x-javascript", "utf-8",in);
			
				
				} 
				catch (Exception e) 
				{ e.printStackTrace(); 
				
				Log.i("result", "加载本地js错误："+e.toString());
				
				} //需处理特殊情况 
			
				
				
				
				
		return null; 
		
				
			}	
			
			
			
				private WebResourceResponse editResponse2()

				{ 

					try 
					{ 
						Log.i("result", "加载本地jquery.js"); 


						InputStream in=getAssets().open("jquery.js");

						byte[] byt = new byte[in.available()];
						in.read(byt);
						
						return new WebResourceResponse("application/x-javascript", "utf-8",in);
						//getAssets().open("android.js"));

					} 
					catch (Exception e) 
					{ e.printStackTrace(); 

						Log.i("result", "加载本地js错误："+e.toString());

					} //需处理特殊情况 

					return null;
					}
			
			
			
			});
		
	//	web.setWebViewClient(new WebViewClient());
		web.loadUrl("file:///"+getRunPath("runfile"));

		//toast(getRunPath("runfile"));
		web.addJavascriptInterface(this,"app");


	


	
		
		
	}
	
	
	
	
	@JavascriptInterface
    @SuppressLint("JavascriptInterface")
    public void Toastshow(String str, int i) {
        Toast.makeText(Emulator_js.this,str,i).show();
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
		Toast.makeText(Emulator_js.this,text,0).show();
	}
	
	public String getRunPath(String name)
	{
		return getIntent().getStringExtra(name);
	}
	
	
}
