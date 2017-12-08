package com.jide;

import android.view.View.*;
import android.view.*;

public class UIOnTouchListener implements OnTouchListener
{
   Emulator emuActivity;
	
	public static final int
	ACTION_DOWN=20,
	ACTION_UP=21,
	ACTION_MOVE=22;
	
	public UIOnTouchListener(Emulator activity)
	{
		this.emuActivity = activity;
	}
	
	@Override
    public boolean onTouch(View view, MotionEvent event)
    {
        switch(event.getAction())
        {
                case MotionEvent.ACTION_DOWN:
                emuActivity.native_event(ACTION_DOWN,view.getId(),0);
                break;
                case MotionEvent.ACTION_UP:
                emuActivity.native_event(ACTION_UP,view.getId(),0);
                break;
        }
        return false;
	}
	
}
