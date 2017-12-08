/****************************

 Class Name BaseActivity
 Author Mix Angel
 QQ 1948416296
 Activity 继承基本类

 ****************************/
package as.mke.jscilent;

import android.app.Activity;
import android.os.Bundle;
import android.app.ActionBar;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import as.mke.jscilent.R;

public class BaseActivity extends Activity
{
    ActionBar mActionBar;
    SharedPreferences mPreferences;
    Editor mEditor;
    
    public static boolean isLight;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        mActionBar = getActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.holo_blue_dark)));
        mActionBar.setIcon(R.drawable.javascript);
        mPreferences = getSharedPreferences("mide_setting",MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }
    
}
