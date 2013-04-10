package iflab.test;

import java.sql.Date;
import java.sql.Time;

import iflab.model.ECG;
import iflab.myinterface.EcgDAO;

public class Store2Sqlite
{
  ECG ecg;
  EcgDAO ecgDAO;
  boolean isReady;
  public GraphicsData graphicsData;
  
  public  Store2Sqlite(GraphicsData gData)
{
	  graphicsData=gData;
	 
}
  
  public void StartStroing()
{
	  if (isReady)
	{
		storeThread.start();
	}
	
}
	
	
Thread storeThread = new Thread()
{
	public void run()
	{
	if(isReady)
	{
		isReady=false;
	   for (int i = 0; i < 14*3; i++)
	   {
		ecg= new ECG(1, "yanghua", null,null, graphicsData.data[485+i], 0);
	    ecgDAO.add(ecg);
	    }
	    isReady=true;
	}
    
     	
		
	}
	
	 
};

}
