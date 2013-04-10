package iflab.test;

import java.util.Random;

import android.app.NotificationManager;
 
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class HttpBindService extends Service
{
	private Bundle thresholdData = new Bundle();
	 private NotificationManager mNM;
	 private final Random mGenerator = new Random();
	    // Unique Identification Number for the Notification.
	    // We use it on Notification start, and to cancel it.
	    

	    /**
	     * Class for clients to access.  Because we know this service always
	     * runs in the same process as its clients, we don't need to deal with
	     * IPC.
	     */
	    public class LocalBinder extends Binder {
	        HttpBindService getService() {
	            return HttpBindService.this;
	        }
	    }
	    
	    @Override
	    public void onCreate() {
	        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            Log.i("log_tag", "bindg on create");
	        // Display a notification about us starting.  We put an icon in the status bar.
	        showNotification();
	    }

	    @Override
	    public int onStartCommand(Intent intent, int flags, int startId) {
	    	thresholdData= intent.getExtras();
	    	
	        Log.i("LocalService", "Received start id " + startId + ": " + intent);
	        // We want this service to continue running until it is explicitly
	        // stopped, so return sticky.
	        return START_STICKY;
	    }

	    @Override
	    public void onDestroy() {
	        // Cancel the persistent notification.
	     //   mNM.cancel(NOTIFICATION);

	        // Tell the user we stopped.
	        Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
	    }

	    @Override
	    public IBinder onBind(Intent intent) {
	    	
	        return mBinder;
	    }

	    
	    /** method for clients */
	    public int getRandomNumber() {
	      return mGenerator.nextInt(100);
	    }
	    
	    // This is the object that receives interactions from clients.  See
	    // RemoteService for a more complete example.
	    private final IBinder mBinder = new LocalBinder();
	    /**
	     * Show a notification while this service is running.
	     */
	    private void showNotification() {
	        // In this sample, we'll use the same text for the ticker and the expanded notification
	     //   CharSequence text = getText(R.string.local_service_started);

	        // Set the icon, scrolling text and timestamp
	      //  Notification notification = new Notification(R.drawable.stat_sample, text,
	             //   System.currentTimeMillis());

	    	Context context = getApplicationContext();
	    	CharSequence text = "service is running";
	    	int duration = Toast.LENGTH_SHORT;

	    	Toast toast = Toast.makeText(context, text, duration);
	    	toast.show();
	    	
	        // The PendingIntent to launch our activity if the user selects this notification
	      //  PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
	        //        new Intent(this, LocalServiceActivities.Controller.class), 0);

	        // Set the info for the views that show in the notification panel.
	       // notification.setLatestEventInfo(this, getText(R.string.local_service_label),
	                    //   text, contentIntent);
//
	        // Send the notification.
	        //mNM.notify(NOTIFICATION, notification);
	    }
}
