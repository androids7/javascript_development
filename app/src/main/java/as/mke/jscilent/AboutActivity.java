package as.mke.jscilent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;
import as.mke.jscilent.R;

/**
 *“关于”对话框
 * 
 * @author 小智
 *
 */
public class AboutActivity extends Dialog {

    TextView sTextMessage;
    Button sButtonOne;
    Button sButtonTwo;
    String sAboutText = "关于JIDE(Javascript IDE)，这是一款混合型编程软件，既支持html5语言(标准js开发界面库暂不支持)，也同时提供了许多安卓方法，帮你快速开发程序！目前程序在测试中，难免有不少bug，欢迎反馈。制作:andorids7 制作灵感来源：Mix Angel，API网页模版支持：小炫";
    
	public AboutActivity(Activity context) {
		super(context);
		init(context);
	}

	public AboutActivity(Activity context, int theme) {
		super(context, theme);
		init(context);
	}

	public AboutActivity(Activity context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init(context);
	}

	/**
	 * 构造函数之间的可共享代码
	 */
	private void init(final Activity context) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
       
		setContentView(R.layout.about);
        sTextMessage=(TextView)findViewById(R.id.aboutTextView);
        sTextMessage.setText(sAboutText);
        sButtonOne=(Button)findViewById(R.id.FeedButton);
        sButtonTwo=(Button)findViewById(R.id.CancelButton);
        sButtonOne.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v)
                {
                    
                }
            });
        sButtonTwo.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v)
                {
                    AboutActivity.this.dismiss();
                }
            });
	}

}
