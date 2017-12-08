package as.mke.jscilent;

import android.os.Bundle;

public class OnlineActivity extends BaseActivity
{
    // Bomb APPID
    public static String APPID = "b9279145de3766b47414de54103c69ed";
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        //第二：默认初始化
        //Bmob.initialize(this, APPID);
    }
    
 }
