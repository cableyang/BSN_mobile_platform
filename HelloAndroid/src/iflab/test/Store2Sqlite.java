package iflab.test;

import java.sql.Date;
import java.sql.Time;

import android.content.Context;
import android.util.Log;

import iflab.model.ECG;
import iflab.myinterface.EcgDAO;

public class Store2Sqlite
{
  ECG ecg;
  EcgDAO ecgDAO;
  boolean isReady;
  boolean start;
  public GraphicsData graphicsData;
  

  
  public Store2Sqlite(EcgDAO ecgDAO2, GraphicsData graphicsECGData)
{
	// TODO Auto-generated constructor stub	  
	  ecgDAO=ecgDAO2;
	  graphicsData=graphicsECGData;
	  isReady=true;
	  start=false;
	 
}

public void StartStroing()
{
	  storeThread.start();
}
	
	
Thread storeThread = new Thread()
{
	public void run()
	{
		while(!currentThread().interrupted())
		{
			
	if(isReady&start)
	{
		isReady=false;
	   /*for (int i = 0; i < 14*2; i++)
	   {
		ecg= new ECG(1, "yanghua", null,null, graphicsData.data[485+i], 0);
	    ecgDAO.add(ecg);
	    }*/
		ecg= new ECG(1, "yanghua", null,null, graphicsData.data[485], 0);
		ecgDAO.bulkstore(graphicsData, ecg);
	    Log.i("STROING2SQLITE", "is storing,,");
	    isReady=true;
	    start=false;
	    
	}
	
	}
		}

	
	 
};

}
