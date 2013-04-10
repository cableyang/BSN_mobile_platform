package iflab.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service
{
	protected static final String TAG = "IntentActivity";
	@Override
	public void onCreate()
	{
		super.onCreate();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		new MyThread().start();
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	
	private class MyThread extends Thread
	{

		@Override
		public void run()
		{
			try
			{
				Log.i(TAG, "MyService线程ID："+Thread.currentThread().getId());
				Log.i(TAG, "文件下载....");
				Thread.sleep(2000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
		}
		
	}

}
