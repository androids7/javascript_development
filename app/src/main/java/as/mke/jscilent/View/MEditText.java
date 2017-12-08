package as.mke.jscilent.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.widget.TextView.BufferType;
import android.widget.TextView;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.text.AutoText;
import android.inputmethodservice.Keyboard;


import as.mke.jscilent.R;
/*
 * 自定义代码高亮编辑框
 */
public class MEditText
extends EditText {
    private int end;
    private Handler handler;
    private int l1;
    private int l2 = 0;
    private int lineColor;
    private SpannableString spannable;
    private int star;
    private String temp;

    //正则匹配关键字
    private String StringOne="\\b(return|function|while|for|break|continue|var|document|else|elseif|script|char|int|int32|int16|int8|uint32|uint16|uint8|void|short|long|unsigned)\\b";
    private String StringTwo="\\b(VERSION|DEBUG|SUCCESS|FAILED|IGNORE|WAITING|RDONLY|WRONLY|RDWR|CREAT|SHARE|SEEK_SET|SEEK_CUR|SEEK_END|IS_FILE|IS_DIR|IS_INVALID|rectst|colorst|datetime|SCRW|SCRH)\\b";
    private String StringThree="\\b(android.h|sound.h|file.h)\\b";
    private String StringFour="//(.*)|/\\*(.|[\r\n])*?\\*/|=|==";
    //"(?<!:)\\/\\/.*|\\/\\*(\\s|.)*?\\*\\/|=|==";
    private String StringFive="\\b(TRUE|FALSE)\\b";
    private String StringSix="";
    
    public MEditText(Context context) {
        super(context);
        this.setBackgroundColor(0x00FFFFFF);
        this.setTextColor(0xFFCCCCCC);
        this.setPadding(this.getLineHeight()+20, 5, 5, 5);
        this.lineColor = getResources().getColor(R.color.codeBlue);
        this.setHorizontallyScrolling(false);
        this.setGravity(51);
        this.handler = new Handler();
    }

    public MEditText(Context context,AttributeSet attrs) {
        super(context,attrs);
        this.setBackgroundColor(0x00FFFFFF);
        this.setTextColor(0xFFCCCCCC);
        this.setPadding(this.getLineHeight()+20, 5, 5, 5);
        this.lineColor = getResources().getColor(R.color.codeBlue);
        this.setHorizontallyScrolling(false);
        this.setGravity(51);
        this.handler =  new Handler();
    }
    
    public MEditText(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        this.setBackgroundColor(0x00FFFFFF);
        this.setTextColor(0xFFCCCCCC);
        this.setPadding(this.getLineHeight()+20, 5, 5, 5);
        this.lineColor = getResources().getColor(R.color.codeBlue);
        this.setHorizontallyScrolling(false);
        this.setGravity(51);
        this.handler = new Handler();
    }
    
    public static void setEditable(MEditText editor, SpannableString spannableString) {
        editor.spannable = spannableString;
    }

    public static void setEnd(MEditText editor, int n) {
        editor.end = n;
    }

    public static void setStart(MEditText editor, int n) {
        editor.star = n;
    }

    public void highlight(int n, String string) {
        Matcher matcher = Pattern.compile((String)string).matcher((CharSequence)this.temp);
        while (matcher.find()) {
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(n);
            this.spannable.setSpan((Object)foregroundColorSpan, matcher.start(), matcher.end(), 33);
        }
        return;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int n = this.getLineHeight();
        TextPaint textPaint = this.getPaint();
        textPaint.setColor(this.lineColor);
        float f = this.getTextSize();
        textPaint.setTextSize(f / (float)3 * (float)2);
        int n2 = this.getPaddingTop();
        int n3 = this.getPaddingLeft();
        int n4 = (int)(f + (float)n2);
        int n5 = this.getScrollX();
        int n6 = -4 + this.getRight();
        int n7 = 0;
        do {
            if (n7 >= this.getLineCount()) {
                canvas.translate(false ? 1 : 0, false ? 1 : 0);
                textPaint.setTextSize(f);
                super.onDraw(canvas);
                return;
            }
            //canvas.drawRect((float)(n5 + n3), (float)(2 + n4), (float)(n5 + n6), (float)(2 + n4), (Paint)textPaint);
            canvas.drawText(String.valueOf((int)(1 + n7)), (float)(n5 + 2), (float)n4, (Paint)textPaint);
            n4 = n + n4;
            ++n7;
        } while (true);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        
        this.temp = this.getText().toString();
        this.spannable = new SpannableString((CharSequence)this.temp);
        changeColor cc = new changeColor(this);
        
        this.l1 = this.length();
        if (this.l1 != this.l2 ) {
            this.star = this.getSelectionStart();
            this.end = this.getSelectionEnd();
            handler.post(cc);
            this.l2 = this.l1;
        }
    }

    public void setLineColor(int n) {
        this.lineColor = n;
        this.invalidate();
    }

    /*
     * Failed to analyse overrides
     */
    class changeColor
    implements Runnable {
        private final MEditText mEditor;

        public changeColor(MEditText editor) {
            this.mEditor = editor;
        }

        @Override
        public void run() {
            
            this.mEditor.highlight(getResources().getColor(R.color.codeBlue),StringOne);
            this.mEditor.highlight(getResources().getColor(R.color.codeBlue2),StringTwo);
            this.mEditor.highlight(getResources().getColor(R.color.codeYellow),StringThree);
            this.mEditor.highlight(getResources().getColor(R.color.codeGreen),StringFour);
            this.mEditor.highlight(getResources().getColor(R.color.codeRed),StringFive);
            this.mEditor.setText((CharSequence)this.mEditor.spannable);
            this.mEditor.setSelection(this.mEditor.star, this.mEditor.end);
        }
    }

}

