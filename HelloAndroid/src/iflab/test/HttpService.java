package iflab.test;

import iflab.model.ECG;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
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
	private TimerTask httpTimerTask;
	private Timer httpTimer = new Timer();
	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;
	HttpECGservice httpECGservice; //申请httpecg远程服务器

	 
	private final class ServiceHandler extends Handler 
	{
		  public ServiceHandler(Looper looper)
		  {
			super(looper);  
		  }

		@Override
		public void handleMessage(Message msg)
		{	 
		 
		    switch (msg.arg1)
			{
			case 1:
				
				break;
				
			case 2:   //DEAL WITH DATA ON NETWORK
				
				// ECG ecg=new ECG(1, "杨华", null, null, 10, 0);  
			 
				 Log.i("storing", "....");
				break;
			default:
				break;
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
		   Log.i("handler", "the service is on create");
		    mServiceLooper = thread.getLooper();
		    mServiceHandler = new ServiceHandler(mServiceLooper);
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO Auto-generated method stub
		Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
		
		httpTimerTask = new TimerTask()
		{
			
			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				Message message = mServiceHandler.obtainMessage();
        	    message.arg1=2;
        	    mServiceHandler.sendMessage(message);
        	 
			}
		};
		
		httpTimer.schedule(httpTimerTask,300,100); 
		//Message msg = mServiceHandler.obtainMessage();
	  //  msg.arg1 = startId;
	  //  mServiceHandler.sendMessage(msg);      
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
		
		//create a new time thread for mysql data exchange
		/*
		httpTimerTask = new TimerTask()
		{
			
			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				Message message = mServiceHandler.obtainMessage();
        	    message.arg1=2;
        	    Log.i("msg", ""+httpTimer);
			}
		};
		
		httpTimer.schedule(httpTimerTask,300,100); 
		*/
		super.onStart(intent, startId);
	}
	
	

}
