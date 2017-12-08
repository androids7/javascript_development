package com.jide;

public class Env
{
	
	static
	{
		System.loadLibrary("native");
	}
	
	
	public native String getA();
}
