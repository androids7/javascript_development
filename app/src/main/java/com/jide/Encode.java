package com.jide;

public class Encode
{
	
	public static String decode(String str)
	{
		String key="10086";
		char chs[]=str.toCharArray();
		int ch[]=new int[chs.length];
		for(int i=0;i<chs.length;i++)
		{
			ch[i]=(chs[i]^20)>>2;
			chs[i]=(char)ch[i];
		}

		return new String(chs);
	}
}
