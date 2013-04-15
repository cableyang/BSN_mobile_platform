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

import android.os.Looper;
import android.util.Log;


/*
 * httpecgservice extends a time task to send message to port
 */
public class HttpECGservice 
{
	 String urlString="http://223.3.61.67/ecg2mysql.php";
	 ECG ecg;
	 TimerTask httpTimerTask;
	 Timer timer;
	 private Looper mServiceLooper;
	 int num2send=500;  //设置传输的数据个数
	 GraphicsData graphicsData;
	 boolean httpStart;
	 boolean isready;
	// ArrayList <NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
	 public HttpECGservice(ECG ecg,GraphicsData gData)
	 {
		 graphicsData=new GraphicsData(500);
		 httpStart=false;
		 isready=true;
		this.ecg=ecg;
		graphicsData.data=gData.data;	
		 Log.i("httpservice", "class is running");
		 
	 }
	
   public void StartThread()
{
	httpSendingThread.start();
}
	
	 
	 public void StartSending(GraphicsData gData)
	{
		 httpStart=true;	 
		graphicsData.data=gData.data;
		
	}
	 /*
	  * 将ECG数据打包json格式进行发送
	  */
	 public void Ecg2php()
	{
		 //装载数据
		 
		 ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 nameValuePairs.add(new BasicNameValuePair("id",String.valueOf(ecg.getid())));
		 nameValuePairs.add(new BasicNameValuePair("name","tester"));
		// nameValuePairs.add(new BasicNameValuePair("ecg",String.valueOf(100)));
		 //nameValuePairs.add(new BasicNameValuePair("name",ecg.getname()));
		 
		 
		 for(int i=0; i<num2send; i++)
		 {
	     nameValuePairs.add(new BasicNameValuePair("ecg"+i,String.valueOf(graphicsData.data[500-num2send+i]))); 
		// Log.i("ECG", "ecg["+i+"]="+String.valu eOf(graphicsData.data[449+i]));
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

	 Thread httpSendingThread = new Thread()
	 {
		 
		public void run() 
		{
		  while(!currentThread().isInterrupted())
		  {
			  if (httpStart)
				{
				  if (isready)
				{
					  httpStart=false;
					  isready=false;
					  Ecg2php();
					  isready=true;
					  Log.i("Thread", "is sending");
				}
				  
					
				}
			  
		  }
			
			
		};
		
	 };
 
}
