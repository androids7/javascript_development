//内置浏览器
package com.jide;

import android.app.Activity;
import android.app.ActionBar;
import android.webkit.WebView;
import android.os.Bundle;
import android.graphics.drawable.ColorDrawable;
import android.widget.ProgressBar;
import android.webkit.WebChromeClient;
import android.view.View;
import android.webkit.WebViewClient;
import android.view.SubMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import as.mke.jscilent.R;
import as.mke.jscilent.EditActivity;
import as.mke.jscilent.BaseActivity;

public class WebActivity extends BaseActivity
{
    WebView mWebView;
    ProgressBar mProgressBar;
    String http_url;
  
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    public void initView()
    {
        Bundle mBundle = getIntent().getExtras();
        http_url = mBundle.getString("http");
        mProgressBar = (ProgressBar)findViewById(R.id.webProgressBar);
        mWebView = (WebView)findViewById(R.id.webWebView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        WebChromeClient mWebChrome = new WebChromeClient();
        mWebView.setWebChromeClient(mWebChrome);
        mProgressBar.setVisibility(View.GONE);
        if(!http_url.equals(""))mWebView.loadUrl(http_url);
        mWebView.setWebViewClient(new mWebViewClient(this));
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu subOptions = menu.addSubMenu(1, 1, 1, "菜单");
        subOptions.setIcon(EditActivity.isLightTheme() ? R.drawable.ic_menu_moreoverflow_normal_holo_light : R.drawable.ic_menu_moreoverflow_normal_holo_dark);
        subOptions.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        int i = 0;
        subOptions.add(1, 2, i++, "刷新");
        subOptions.add(1, 3, i++, "前进");
        subOptions.add(1, 4, i++, "后退");
        subOptions.add(1, 5, i++, "退出");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()){
            case 2:mWebView.reload();
                break;
            case 3:if(mWebView.canGoForward())mWebView.goForward();
                break;
            case 4:if(mWebView.canGoBack())mWebView.goBack();
                break;
            case 5:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    
    class mWebViewClient extends WebViewClient {
        private final WebActivity mActivity;

        public mWebViewClient(WebActivity webActivity) {
            this.mActivity = webActivity;
        }

        public WebActivity setActivity(WebActivity webActivity) {
            return webActivity;
        }

        public void onProgressChanged(WebView webView, int n) {
            this.mActivity.mProgressBar.setVisibility(View.VISIBLE);
            this.mActivity.mProgressBar.setProgress(n);
            this.mActivity.mProgressBar.postInvalidate();
            if (n == 100) {
                this.mActivity.mProgressBar.setVisibility(View.GONE);
            }
        }

        public void onReceivedTitle(WebView webView, String string) {
            this.mActivity.getActionBar().setTitle((CharSequence)string);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String string) {
            webView.loadUrl(string);
            return true;
        }
    }

    
}
