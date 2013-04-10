package iflab.test;

import iflab.model.ECG;
import iflab.model.Student;
import iflab.model.elder;
import iflab.myinterface.EcgDAO;
import iflab.myinterface.ElderDAO;
import iflab.myinterface.StudentDAO;
import iflab.test.R.id;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.sql.Time;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.security.auth.PrivateCredentialPermission;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
 



import android.R.bool;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.StaticLayout;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class firstActivity extends Activity 
{
	private Timer bttimer = new Timer();
	private TimerTask bttask;
	private boolean bttimeflag=true;
	private boolean updataflag=false;
	
    private static Handler drhandler;
	
    /*
     * 
     */
    Store2Sqlite store2Sqlite;
	
	/*
	 * 特殊字符定义段
	 */
	public static final int RATE250 = 250;
	public static final int RATE500 = 500;
	public static final int RATE1000 = 1000;
	
	/*
	 * 蓝牙信息定义段，其中MU_UUID为SPP服务的ID
	 */
	private final static int REQUEST_CONNECT_DEVICE = 1;    //宏定义查询设备句	
    private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";   //SPP服务UUID号	
    private InputStream blueStream;    //输入流，用来接收蓝牙数据
    private OutputStream blueoutOutputStream;
	private static TextView dis,rnds;       //接收数据显示句柄
	private static  String smsg = "";    //显示用数据缓存
	private static String readMessage="";
	private static String firstmessage;//存放第一个信息
	private static String  secondmessage;//存放第二个信息
	private static String transimitString; //传输字符串
	
	private HttpBindService mBindService;
	
	boolean mIsBound;
	
	
	BluetoothDevice _device = null;     //蓝牙设备
    BluetoothSocket _socket = null;      //蓝牙通信socket
    boolean _discoveryFinished = false;    
    boolean bRun = true;
    boolean bThread = false;
    private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();    //获取本地蓝牙适配器，即蓝牙设备
	
	/*
	 * 显示界面定义和 数据
	 */
	public GraphicsData graphicsECGData;
	public GraphicsData graphicsPluseData;
	
	private MyGraphics myGraphics1=null;			//声明自定义View对象
	private MyGraphics myGraphics2=null;
	ElderDAO elderDAO;  //创建老人数据对象
	EcgDAO ecgDAO;
	HttpECGservice httpECGservice; //申请httpecg远程服务器
	/*
	 * 按钮定义
	 */
	Button blueStartButton;  //启动蓝牙
	Button bindButton;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {	//重载onCreate方法
		final int packetnum = 14;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	    LinearLayout layout_ecg = (LinearLayout)findViewById(R.id.ECG_SINGAL);
	    LinearLayout layout_pluse = (LinearLayout)findViewById(R.id.Pluse);
	    
	  //  studentDAO=new StudentDAO(this.getBaseContext());
	     ecgDAO = new EcgDAO(getBaseContext()); 
	     elderDAO= new ElderDAO(getBaseContext());
  
	     //建立表
	     ecgDAO.createtable();
	     elderDAO.creattable();
	     
	    graphicsECGData = new GraphicsData(RATE500); //分别对ECG和PLUSE进行频率设定
	    graphicsPluseData = new GraphicsData(RATE500);
	 
	    store2Sqlite=new Store2Sqlite(ecgDAO,graphicsECGData);
	    
	    //   String urlString="http://223.3.61.67/ecg2mysql.php";
	    //数据库写入
	     for(int i=1; i<10; i++)
	    {
   
	    //	 ECG ecg=new ECG(1, "杨华", null, null, graphicsECGData.data[499], 0);
			 //	ecgDAO.add(ecg);
			// httpECGservice.set2mysql(ecg);
	    	 
	    	 //ECG ecg=new ECG(1, "cc", null, null, graphicsECGData.data[499], 0);
			 //	ecgDAO.add(ecg);
			// httpECGservice.set2mysql(ecg);
              // long time=c.getTimeInMillis();
	    	/*
	    		 ECG ecg=new ECG(i, "add", (java.sql.Date) date, null, 2.0011+i, i/10.0);
	    	     ecgDAO.add(ecg);
	    		 elder elder=new elder(i, "dd", 22, "aa", "dddd");
	    	 	 elderDAO.addelder(elder);
	    	*/
	    }  
	          
	     
	     /*
	      * 后台进行数据传输
	      */
	     
	     try
		{
	    	doBindService();	 
	    	 //Intent intent=new Intent(firstActivity.this, HttpService.class);
	      //  startService(intent);
	       // Log.i("handler", "the service is on create");
		 	 
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	     
	    double []p= new double [500];
	    for (int i = 0; i < RATE500; i++)
		{
		 p[i] = 100*Math.sin(i/100.0)+50;
		 graphicsECGData.adddata(p[i]);
		}
	    
	  //  graphicsECGData.putdata(p);
	    
	    this.myGraphics1=new MyGraphics(this, RATE500,  graphicsECGData);		//创建自定义View对象
	    layout_ecg.addView(myGraphics1, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
	 
	    
	    for (int i = 0; i < RATE500; i++)
		{
		 p[i] = 50*Math.cos(i/10.0)+50;
		}
	    
	    graphicsPluseData.putdata(p);
	    
	    this.myGraphics2=new MyGraphics(this, RATE500, graphicsPluseData);		//创建自定义View对象
	    layout_pluse.addView(myGraphics2, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
         
	    bindButton =(Button)findViewById(R.id.Bind);
	    bindButton.setOnClickListener(listener);
	  
	    blueStartButton = (Button)findViewById(R.id.bluestart);
	    blueStartButton.setOnClickListener(listener);
	    
	    BluetoothCheck(); //check that device
	     
	       
	    drhandler = new Handler() 
		{
               
			@Override
        	public void handleMessage(Message msg) 
        	{
        		 switch (msg.what) 
        		 {   
	        	 	case 1:// updateChart(); 		
	        		 break;
	        	 	case 2:  //定时器中断
	        	    byte []bytes=new byte[1024];
	        	 	int num;	
					try
					{
					Log.i("BUFFER IS", "firstmessage is "+firstmessage);
					num = blueStream.read(bytes);
					readMessage = new String(bytes, 0, num);
					//secondmessage=readMessage; //得到的数据传输至第二个
					//transimitString=firstmessage+secondmessage;
					graphicsECGData.dealwithstring(readMessage);
					Log.i("BUFFER IS", "readMessage is "+readMessage);
				//	firstmessage=secondmessage;
                   // store2Sqlite.StartStroing();	
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
		        	break;
		        	/*
		        	 * 数据处理handler
		        	 */
	        	 	case 3:
	        	 		int i=0;
	        	 		i++;
     			     
     			       byte[] Readbuf = (byte[]) msg.obj;   	 		
     			       readMessage = new String(Readbuf, 0, msg.arg1);	
     			         Log.i("BUFFER IS", readMessage);
     			        			 
		        	break;
        		 }
        		 
        		super.handleMessage(msg);
        	}
        };     
	  }  

	   void doBindService() 
	   { 
		   bindService(new Intent(firstActivity.this,HttpBindService.class), mConnection, Context.BIND_AUTO_CREATE);
	       mIsBound = true;
	       Log.i("log_tag", "is do binding service	..");
	    	}

	    	void doUnbindService() {
	    	    if (mIsBound) {
	    	        // Detach our existing connection.
	    	        unbindService(mConnection);
	    	        mIsBound = false;
	    	    }
	    	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    doUnbindService();
	}
	 

	public static String charToHexString(byte strPart) 
    {
        
    	  String hex = Integer.toHexString(strPart & 0x00FF);
    	  if (hex.length() == 1) 
    	  {        
    		  hex = '0' + hex;      
    	  } 
    	  hex = "0x" + hex;
        return hex;
    }
   

 
 ServiceConnection mConnection = new ServiceConnection() 
 {  
			@Override
			public void onServiceConnected(ComponentName name,IBinder service)
			{
				// TODO Auto-generated method stub
				 // This is called when the connection with the service has been
 	        // established, giving us the service object we can use to
 	        // interact with the service.  Because we have bound to a explicit
 	        // service that we know is running in our own process, we can
 	        // cast its IBinder to a concrete class and directly access it.
 	    	mBindService = ((HttpBindService.LocalBinder)service).getService();
 	        // Tell the user about this for our demo.
 	    	mIsBound=true;
 	    	Log.i("log_tag", " on service connected..");
 	    	
			}

			@Override
			public void onServiceDisconnected(ComponentName name)
			{
		     // TODO Auto-generated method stub
		     // This is called when the connection with the service has been
 	        // unexpectedly disconnected -- that is, its process crashed.
 	        // Because it is running in our same process, we should never
 	        // see this happen.
			mIsBound=false;
 	        mBindService = null;   
			}
 	};
 
	/*
	 * check is bluetooth is available
	 * create a thread to always open the bluetooth
	 * and run it
	 */
	private void BluetoothCheck()
	{
		
	    if (_bluetooth == null)
        {
        	Toast.makeText(this, "无法打开手机蓝牙，请确认手机是否有蓝牙功能！", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
	    
	    new Thread()
	       {
	    	   public void run()
	    	   {
	    		   if(_bluetooth.isEnabled()==false)
	    		   {
	        		_bluetooth.enable();
	    		   }
	    	   }   	   
	       }.start();
	}
	
    /*
     * deal with all the main button message
     * 处理所有按钮信息
     */
	private OnClickListener listener = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			Button btn = (Button)v;
			switch (btn.getId())
			{
			case R.id.bluestart:
				StartBluetooth();  //开启蓝牙窗口
				break;
            
			case R.id.button_cancel:
				
				break;
				
			case R.id.Bind:
				if (mIsBound) {
		            // Call a method from the LocalService.
		            // However, if this call were something that might hang, then this request should
		            // occur in a separate thread to avoid slowing down the activity performance.
		            int num = mBindService.getRandomNumber();
			 
		           Log.i("log_tag", "number is"+num);
		        }
				break;

			default:
				break;
			}
		}
	};
	
	/*
	 * function check the bluetooth module 
	 * 开启蓝牙配对窗口，返回信息为蓝牙客户端口的地址
	 */
	public void StartBluetooth()
    { 
		Toast.makeText(this, " 打开蓝牙中...", Toast.LENGTH_LONG).show();
		Intent serverIntent = new Intent(this, DeviceListActivity.class); //跳转程序设置
		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);  //设置返回宏定义
		
    	
		if(_bluetooth.isEnabled()==false)
    	{  //如果蓝牙服务不可用则提示
    		Toast.makeText(this, " 打开蓝牙中...", Toast.LENGTH_LONG).show();
    		return;
    	}
 	
 	   if (bttimeflag==false)
    	{
    		if (bttask!=null)
    		{
    			bttask.cancel();
    		}
    		bttimeflag=true;
    	}
 	
    	if(_socket==null)
    	{
    		Intent serverIntent1 = new Intent(this, DeviceListActivity.class); //跳转程序设置
    		startActivityForResult(serverIntent1, REQUEST_CONNECT_DEVICE);  //设置返回宏定义
    	}
    	else
    	{
    		 //关闭连接socket
    	    try
    	    {    	    	
    	    	blueStream.close();
    	    	_socket.close();
    	    	_socket = null;
    	    	bRun = false;
    	    	//ConB.setText("连接");
    	    }
    	    catch(IOException e)
    	    {
    	    	
    	    }   
    	}
    }	
		
	 //接收活动结果，响应startActivityForResult()
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch(requestCode){
    	case REQUEST_CONNECT_DEVICE:     //连接结果，由DeviceListActivity设置返回
    		// 响应返回结果
            if (resultCode == Activity.RESULT_OK) {   //连接成功，由DeviceListActivity设置返回
                // MAC地址，由DeviceListActivity设置返回
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                // 得到蓝牙设备句柄      
                _device = _bluetooth.getRemoteDevice(address);
 
                // 用服务号得到socket
                try{
                	_socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
                }catch(IOException e){
                	Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                }
                try
				{	
					_socket.connect();
					Toast.makeText(this, "连接"+_device.getName()+"成功！", Toast.LENGTH_SHORT).show();
				} catch (IOException e)
				{
					
            		try
					{
            		Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
					_socket.close();
					_socket = null;
					} catch (IOException e1)
					{
						// TODO Auto-generated catch block
						Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();	
					}            		
					// TODO Auto-generated catch block
					return;
				}
       
                //打开接收线程
                try{
            		blueStream = _socket.getInputStream();   //得到蓝牙数据输入流
            		//blueoutOutputStream=_socket.getOutputStream();//得到蓝牙输出数据
            		Toast.makeText(this, "绑定数据流成功", Toast.LENGTH_SHORT).show();
            		}catch(IOException e){
            			//Toast.makeText(this, "接收数据失败！", Toast.LENGTH_SHORT).show();
            			return;
            		}
            		if(bThread==false){
             
            			bttask = new TimerTask() 
	        	        {
	        	        	@Override
	        	        	public void run() 
	        	        	{
	        	        		Message message = new Message();
	        	        		 
	        	        		message.what = 2;
	        	        	    drhandler.sendMessage(message);
	        	        	   
	        	        	}
	        	        };
	        	        bttimeflag=false;
	                    bttimer.schedule(bttask, 500, 35*2);
 	
            		}else{
            			bRun = true;
            		}   
            }
    		break;
    	default:break;
    	}  
    }	
   
    
    
    
    
  //单独开辟线程来读取蓝牙数据
    Thread ReadThread=new Thread(){
    	Calendar ca = Calendar.getInstance();
    	public void run()
    	{
    		
    		int num = 0;
    		byte[] buffer = new byte[1024];
    		 
    		bRun = true;
    		//接收线程
    		while(true)
    		{					
        			
    			try
						{
							num=blueStream.read(buffer);
				
						} catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}         //读入数据
						 
        				          for(int i=0; i<num; i++)
        				          {
        				        	   Log.i("DEBUG", ""+buffer[i]);	
        				          }
        				          
                            drhandler.obtainMessage(3, num, -1, buffer).sendToTarget();
        					
        	        	    
        				  		
    				}           
        			  
    		
    	 
     }
   };
}


