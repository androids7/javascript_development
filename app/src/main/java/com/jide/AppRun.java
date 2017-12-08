package com.jide;

public  abstract interface AppRun
{
    public void native_init();
    public void native_event(int type,int p1,int p2);
    public void native_pause();
    public void native_resume();
}
