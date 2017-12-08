/**************************

 声音接口

 **************************/
 
package com.jide;

import android.media.MediaPlayer;
import android.os.Vibrator;
import android.content.Context;
import java.io.IOException;
import android.util.Log;
import java.io.File;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.content.res.*;
import android.text.format.Time;


public class Audio implements OnErrorListener, OnCompletionListener
{
	@Override
	public void onCompletion(MediaPlayer p)
	{
		//检查是哪个设备停止了
		for (int i=0;i < 5;i++)
		{
			if (mp3Player[i] == p)
			{
				stat[i] = Defines.MR_MEDIA_LOADED;
				((Emulator)context).native_event(32,i,0);
			}
		}

	}

	@Override
	public boolean onError(MediaPlayer p1, int p2, int p3)
	{

		return false;
	}

	int stat[]; //播放状态
	int len[];
	private MediaPlayer mp3Player[];
	//private MediaPlayer mediaPlayer;
	//private boolean audioPaused = false;
	private Vibrator vibrator;
    private Context context;
    
	public Audio(Context context) 
	{
		this.context=context;
        stat = new int[5];
		len = new int[5];
		mp3Player = new MediaPlayer[5];
		for (int i=0;i < 5;i++)
		{
			mp3Player[i] = null;

			stat[i] = Defines.MR_MEDIA_NULL;
		}
		vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
	}

	//音乐播放
	int N2J_playSoundExInit(int type)
	{
		if (mp3Player[type] == null)
			mp3Player[type] = new MediaPlayer();
    stat[type] = Defines.MR_MEDIA_INITED;
		return 0;
	}


	//加载音频文件
	int N2J_playSoundExLoadFile( int type, String filename)
	{
	    File file=new File(filename);
		try
		{
			len[type] = (int)file.length();
			mp3Player[type].setDataSource(filename);
			mp3Player[type].prepare();
			stat[type] = Defines.MR_MEDIA_LOADED;
		}
		catch (IllegalArgumentException e)
		{
			return -1;
		}
		catch (IllegalStateException e)
		{
			return -1;
		}
		catch (IOException e)
		{
			return -1;
		}
		catch (SecurityException e)
		{
			return -1;
		}
		//	mediaPlayer.setLooping(loop==1);
		//audio.N2J_playSound(filename,loop);
		return 0;
	}
	

	//播放 设备类型 是否阻塞 是否循环
	int N2J_playSoundEx(int type, int block, int loop)
	{

		if (block == 0)//同步
		{
			try
			{
				//mp3Player[type].deselectTrack(type);
			//	mp3Player[type].prepare();

			}
			catch (IllegalStateException e)
			{}
			


		}
		else //异步
		{
			try
			{
				/*
				 如果有音乐在播放，那么停止

				 */

				for (int i=0;i < 5;i++)
				{
					if (mp3Player[i] != null)

					{
						if (i != type && mp3Player[i].isPlaying())
						{

							mp3Player[i].stop();
							//mp3Player[i].release();
							//mp3Player[i].reset();
							//mp3Player[i].seekTo(0);
						}
					}
				}

				// 准备

			// mp3Player[type].prepare();

			}
			catch (IllegalStateException e)
			{
				return -1;
			}
			

		}

		mp3Player[type].setLooping(loop == 1);
		
        mp3Player[type].start();
		//mp3Player[type].seekTo(0);
		stat[type] = Defines.MR_MEDIA_PLAYING;

		return 0;
	}

	/*
	 暂停播放音频文件
	 [in]
	 type:设备类型
	 [return]:	MR_SUCCESS 初始化成功
	 MR_FAILED 初始化失败
	 MR_IGNORE 不支持该功能
	 */
	int N2J_pauseSoundEx(int type)
	{
		if (mp3Player[type] == null)
			return -1;
		mp3Player[type].pause();
		stat[type] = Defines.MR_MEDIA_PAUSED;
		return 0;
	}
	/*
	 继续播放音频文件
	 [in]
	 type:设备类型
	 [return]:	MR_SUCCESS 初始化成功
	 MR_FAILED 初始化失败
	 MR_IGNORE 不支持该功能
	 */
	int N2J_resumeSoundEx(int type)
	{
		if (mp3Player[type] == null)
			return -1;
		mp3Player[type].start();
		stat[type] = Defines.MR_MEDIA_PLAYING;
		return 0;
	}
	/*
	 停止播放音频文件
	 [in]
	 type:设备类型
	 [return]:	MR_SUCCESS 初始化成功
	 MR_FAILED 初始化失败
	 MR_IGNORE 不支持该功能
	 */
	int N2J_stopSoundEx(int type)
	{
		if (mp3Player[type] == null)
			return -1;
		mp3Player[type].stop();
		stat[type] = Defines.MR_MEDIA_LOADED;
		return 0;
	}
	/*
	 关闭设备
	 [in]
	 type:设备类型
	 [return]:	MR_SUCCESS 初始化成功
	 MR_FAILED 初始化失败
	 MR_IGNORE 不支持该功能
	 */
	int N2J_closeSoundEx(int type)
	{
		if (mp3Player[type] == null)
			return -1;
		mp3Player[type].release();
	  mp3Player[type] = null;
    stat[type] = Defines.MR_MEDIA_NULL;
		return 0;
	}
	/*
	 音量调节
	 [in]
	 volume:音量大小0~5
	 [return]:	MR_SUCCESS 初始化成功
	 MR_FAILED 初始化失败
	 MR_IGNORE 不支持该功能
	 */
	int N2J_setVolume(int volume)
	{
		int i=0; 
		for (i = 0;i < 5;i++)
		{
			if (mp3Player[i] != null)
				mp3Player[i].setVolume(volume / 5.0f, volume / 5.0f);
		}

		return 0;
	}


	int N2J_getSoundTotalTime(int type)
	{
		if (mp3Player[type] == null)
			return -1;
		return mp3Player[type].getDuration() / 1000;
	}
	int N2J_getSoundCurTime(int type)
	{
		if (mp3Player[type] == null)
			return -1;
		return mp3Player[type].getCurrentPosition() / 1000;
		//return 0;
	}
	int N2J_getSoundCurTimeMs(int type)
	{
		if (mp3Player[type] == null)
			return -1;
		return mp3Player[type].getCurrentPosition();
	}


	int N2J_setPlayPos(int type, int pos)
	{
		if (mp3Player[type] == null)
			return -1;

		int time=mp3Player[type].getDuration();
		int curtime= time *  pos / len[type];

		//计算比例
		mp3Player[type].seekTo(curtime);

		return 0;
	}

	int N2J_setPlayTime(int type, int time)
	{
		if (mp3Player[type] == null)
			return -1;
		mp3Player[type].seekTo(time);
		return 0;
	}

	int N2J_getDeviceState(int type)
	{
		/*
		 if(mp3Player[type]==null)
		 return -1;
		 if(mp3Player[type].isPlaying())
		 return 0;
		 else
		 return 2;
		 */
		return stat[type];
		//	return 0;
	}

	public void N2J_startShake(int ms)
	{
		if (vibrator != null) 
		{
			vibrator.vibrate(ms);
		}
	}

	public void N2J_stopShake()
	{
		if (vibrator != null) 
		{
			vibrator.cancel();
		}
	}



}
