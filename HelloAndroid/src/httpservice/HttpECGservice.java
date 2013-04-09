package httpservice;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import iflab.model.ECG;

public class HttpECGservice
{
	 String urlString="http://223.3.61.67/ecg2mysql.php";
	 InputStream isInputStream;
	// ArrayList <NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
	 public HttpECGservice()
	 {
		 super();
	 }
	 
	 /*
	  * 将ECG数据打包json格式进行发送
	  */
	 public void set2mysql(ECG ecg)
	{
		 //装载数据
		 ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 nameValuePairs.add(new BasicNameValuePair("id",String.valueOf(ecg.getid())));
		 nameValuePairs.add(new BasicNameValuePair("name",ecg.getname()));
		 nameValuePairs.add(new BasicNameValuePair("ecg", String.valueOf(ecg.getecg())));
		 nameValuePairs.add(new BasicNameValuePair("bpm", String.valueOf(ecg.getbpm())));
		 
		 try{
			 HttpClient httpclient = new DefaultHttpClient();
	         HttpPost httppost = new HttpPost(urlString);
	         httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	         HttpResponse response = httpclient.execute(httppost);
	         HttpEntity entity = response.getEntity();
	         isInputStream = entity.getContent();		 
	        }catch(Exception e)
	        {
	          Log.e("log_tag", "Error in http connection"+e.toString());
	        }
		 
	}
}
