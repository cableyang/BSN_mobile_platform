package iflab.test;

import iflab.model.ECG;
 

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;


/*
 * httpecgservice extends a time task to send message to port
 */
public class HttpECGservice extends Service
{
	 String urlString="http://223.3.61.67/bsn/ecg2mysql.php";
	 ECG ecg;
	 TimerTask httpTimerTask;
	 Timer timer;
	 private Looper mServiceLooper;
	 private ServiceHandler mServiceHandler;
	 GraphicsData graphicsData;
	 boolean httpStart;
	// ArrayList <NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
	 public HttpECGservice(ECG ecg,GraphicsData gData)
	 {
		 httpStart=false;
		this.ecg=ecg;
		graphicsData=gData;
		
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



	private final class ServiceHandler extends Handler 
		{
			  public ServiceHandler(Looper looper)
			  {
				//super(looper);  
			  }

			@Override
			public void handleMessage(Message msg)
			{	 
				//httpECGservice = new HttpECGservice();
			    switch (msg.arg1)
				{
				case 1:
					
					break;
					
				case 2:   //DEAL WITH DATA ON NETWORK
					try
					{
						Ecg2php(); 
						Log.i("storing", "....");
					} catch (Exception e)
					{
						// TODO: handle exception
					}
 		
					break;
				default:
					break;
				}	 
			}	 	    
		}
	 
	 
	 public void StartSending()
	{
		 httpTimerTask = new TimerTask()
			{	
				@Override
				public void run()
				{
					// TODO Auto-generated method stub
					if (httpStart)
					{
					Message message = mServiceHandler.obtainMessage();
	        	    message.arg1=2;
	        	    mServiceHandler.sendMessage(message);
	        	    Log.i("httpecgservice", "is  ready");
					}  
					else {
						Log.i("httpecgservice", "is not ready");
					}
				}
			};
			
			timer.schedule(httpTimerTask,300,100); 
	}
	 /*
	  * 将ECG数据打包json格式进行发送
	  */
	 public void Ecg2php()
	{
		 //装载数据
		 ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 nameValuePairs.add(new BasicNameValuePair("id",String.valueOf(ecg.getid())));
		 nameValuePairs.add(new BasicNameValuePair("name",ecg.getname()));
		 
		 for(int i=0; i<50; i++)
		 {
	     nameValuePairs.add(new BasicNameValuePair("ecg["+i+"]",String.valueOf(graphicsData.data[449+i]))); 
		 }
		
		 nameValuePairs.add(new BasicNameValuePair("bpm", String.valueOf(ecg.getbpm())));
		 
		 try{
			 HttpClient httpclient = new DefaultHttpClient();
	         HttpPost httppost = new HttpPost(urlString);
	         httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	         HttpResponse response = httpclient.execute(httppost);
	        
	        }catch(Exception e)
	        {
	          Log.e("log_tag", "Error in http connection"+e.toString());
	        }
		 
	}
	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}
 
}
