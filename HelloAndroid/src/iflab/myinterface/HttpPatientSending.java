package iflab.myinterface;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.util.Base64;
import android.util.Log;
import iflab.model.elder;

public class HttpPatientSending
{
	//服务器地址
	 String urlString="http://223.3.61.67/patient2mysql.php";
	 elder elder;
	 public HttpPatientSending(elder elder)
	{
		this.elder=elder;
	}
	 
	 @SuppressLint("NewApi")
	public void httpsending()
	{
		 ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 nameValuePairs.add(new BasicNameValuePair("id",String.valueOf(elder.getid())));
		 nameValuePairs.add(new BasicNameValuePair("name",elder.getname()));
		 nameValuePairs.add(new BasicNameValuePair("age",String.valueOf(elder.getage())));
		 nameValuePairs.add(new BasicNameValuePair("address",elder.getaddress()));
		 nameValuePairs.add(new BasicNameValuePair("phone",elder.getphone()));
		 nameValuePairs.add(new BasicNameValuePair("decription",elder.getdescripiton()));
		// String imgString=new String(elder.getimg());  //出错了 。。注意。。。
		 Log.i("tag", "is sending");
		 
		// nameValuePairs.add(new BasicNameValuePair("img",imgString));
        
		 try{
			 byte[]b=elder.getimg();
			 /*StringBuffer buff = new StringBuffer();
             for (int i = 0; i < b.length; i++)
             {
                     buff.append(b[i] + " ");
             }
             String imgString=buff.toString();
               */
			 String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
			 nameValuePairs.add(new BasicNameValuePair("img",encodedImage));
			 
			 HttpClient httpclient = new DefaultHttpClient();
	         HttpPost httppost = new HttpPost(urlString);
	         httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	         HttpResponse response = httpclient.execute(httppost); 
	        }catch(Exception e)
	        {
	          Log.e("log_tag", "Error in http connection"+e.toString());
	        }
		 
	}
}
