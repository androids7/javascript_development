package as.mke.jscilent;

import as.mke.jscilent.R;
import android.webkit.WebView;
import android.app.ActionBar;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceActivity;
import android.graphics.Color;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import java.util.Queue;
import android.preference.PreferenceScreen;
import android.preference.EditTextPreference;

public class SettingActivity extends PreferenceActivity
implements OnSharedPreferenceChangeListener
{
    ActionBar sActionBar;
    SharedPreferences sSp;
    Editor sEdit;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        sActionBar = getActionBar();
        sActionBar.setDisplayHomeAsUpEnabled(true);
        sActionBar.setDisplayUseLogoEnabled(true);
        sActionBar.setDisplayShowTitleEnabled(true);
        sActionBar.setDisplayShowHomeEnabled(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(0xFF4E4E4E));
        sActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.holo_blue_dark)));
        addPreferencesFromResource(R.xml.settings);
        initPrefer();

    }
    
    public void initPrefer()
    {
        sSp = PreferenceManager.getDefaultSharedPreferences(this);
        sSp.registerOnSharedPreferenceChangeListener(this);
        sEdit = sSp.edit();
        EditTextPreference fontSize = (EditTextPreference)findPreference("fontsize");
        //fontSize.setOnPreferenceChangeListener();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp,String key)
    {
    }
    
}
