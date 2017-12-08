package as.mke.jscilent;

import android.webkit.WebView;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.drawable.ColorDrawable;
import android.content.Context;
import android.widget.ArrayAdapter;
import as.mke.jscilent.R;


public class ApiActivity extends BaseActivity
{
    WebView mWebView;
    String ApiMessage;
    String[] ArrayApi;
    String[] ArrayShow;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        initView();
    }
    
    public void initView()
    {
        mWebView = (WebView)findViewById(R.id.ApiWebView);
        ArrayApi = getResources().getStringArray(R.array.ApiItemFile);
        ArrayShow = getResources().getStringArray(R.array.ApiItemShow);
        Context context = mActionBar.getThemedContext();
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context,R.array.ApiItemShow,android.R.layout.simple_spinner_dropdown_item);
        list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        mActionBar.setListNavigationCallbacks(list, new ActionBar.OnNavigationListener(){

                @Override
                public boolean onNavigationItemSelected(int p1, long p2)
                {
                    ApiMessage = "file:///android_asset/"+ArrayApi[p1];
                    mWebView.loadUrl(ApiMessage);
                    return false;
                }
            });
        for(int i=0; i<ArrayApi.length; ++i){
            if(ArrayShow[i].equals("base.h")){
                mActionBar.setSelectedNavigationItem(i);
                break;
            }
        }
    }
}
