package httpservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


/*
 * delieve the data to mysql
 */
public class HttpService extends Service
{
	private Looper mServiceLooper;
	  private ServiceHandler mServiceHandler;

	 
	private final class ServiceHandler extends Handler 
	{
		  public ServiceHandler(Looper looper)
		  {
			super(looper);  
		  }

		@Override
		public void handleMessage(Message msg)
		{
			// TODO Auto-generated method stub
             //working service
			while(true)
			{
				Log.i("handler", "handler is working");
				try
				{
					Thread.sleep(500);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		}	    
	}
	
	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		 HandlerThread thread = new HandlerThread("ServiceStartArguments",1 );
		   thread.start(); 
		    // Get the HandlerThread's Looper and use it for our Handler 
		    mServiceLooper = thread.getLooper();
		    mServiceHandler = new ServiceHandler(mServiceLooper);
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO Auto-generated method stub
		Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
		Message msg = mServiceHandler.obtainMessage();
	    msg.arg1 = startId;
	    mServiceHandler.sendMessage(msg);      
	      // If we get killed, after returning from here, restart
	   return START_STICKY;
	}

	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId)
	{
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}
	
	

}
