package iflab.test;

import iflab.model.elder;
import iflab.myinterface.ElderDAO;
import iflab.myinterface.HttpPatientSending;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.w3c.dom.Text;

import android.R.integer;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
 
public class PatientID extends Activity
{
	/** Called when the activity is first created. */
	private ImageView imageView;
	private Handler handler;
	
	private Bitmap myBitmap;
	private byte[] mContent;
	Bitmap resizedBitmap;
	//UI按钮界面
	Button caremabtn,gallerybtn;
	Button backButton;
	Button checkButton,writedb;
	Button remoteButton;
    TextView nameTextView;
    public String nameString;
    TextView idTextView;
    int id;
    TextView ageTextView;
    public int age;
    TextView phoneTextView;
    public String phoneString;
    TextView decrpitontTextView;
    public String des;
    TextView addressTextView;
    String address;
	ElderDAO elderDAO;
	elder elder;
	
	@SuppressLint("NewApi")
	@ Override
	public void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patientid);
		
		nameTextView=(TextView)findViewById(R.id.name);
		idTextView=(TextView)findViewById(R.id.id);
		ageTextView=(TextView)findViewById(R.id.age);
		phoneTextView=(TextView)findViewById(R.id.myphone);
		decrpitontTextView=(TextView)findViewById(R.id.decrpiton);
		addressTextView=(TextView)findViewById(R.id.address);
		imageView = (ImageView) findViewById(R.id.imageView);
		caremabtn=(Button)findViewById(R.id.camera);
		gallerybtn=(Button)findViewById(R.id.gallery);
		remoteButton=(Button)findViewById(R.id.remote_register);
		
		elderDAO=new ElderDAO(getBaseContext());
		elderDAO.creattable();
 	
		/*
		 * 添加handler处理进程
		 */
		handler=new Handler()
		{
            String nameString;
            int age;
            String phone;
            String description;
            String address;
            int id;
            
			@SuppressLint({ "NewApi", "NewApi" })
			@Override
			public void handleMessage(Message msg)
			{
				// TODO Auto-generated method stub 
				//更新数据与handler中
				nameString=PatientID.this.nameString;
				phone=PatientID.this.phoneString;
				age=PatientID.this.age;
				description=PatientID.this.des;
				address=PatientID.this.address;
				id=PatientID.this.id;
				
				switch (msg.what)
				{
				case 1:  //运用httppost将信息传递给相关php页面进行远程注册
						elder=new elder(id, nameString, age,address,  phone, description, mContent);	
						HttpPatientSending httpPatientSending=new HttpPatientSending(elder);			 
					    httpPatientSending.httpsending();
					break;
					
				case 2:  //通过sqlite将信息存放在相应表中 实现本地注册
					  // input some ready information	
					if (mContent==null)
					{
						Toast.makeText(getBaseContext(), "请上传照片或拍照", Toast.LENGTH_LONG).show();
					}
					else {
				  try
					{	 
				    elder=new elder(id, nameString, age,address,  phone, description, mContent);		 
				    elderDAO.addelder(elder); 
				    
				     Toast.makeText(getBaseContext(), "本地注册成功", Toast.LENGTH_LONG).show();
					} catch (Exception e)
					{
						// TODO: handle exception
						Toast.makeText(getBaseContext(), "本地注册失败", Toast.LENGTH_LONG).show();		
					}
					}
				   
					break;
					
				case 3:  //通过相关信息进行检索
					try
					{
						
					 	elder=new elder(0, null, 0, null, null, null, null);
					    elder=elderDAO.findElderbyname(nameString);
					    
					    if(elder==null)
					    {
					    	Toast.makeText(getBaseContext(), "没有注册请注册", Toast.LENGTH_LONG).show();
					    }
					    else {
					    	
						 mContent=elder.getimg();
					     ageTextView.setText(String.valueOf(elder.getage()));
					     Log.i("patientid","the elder age is.."+ elder.getage());
					     nameTextView.setText(elder.getname().toString());
					     idTextView.setText(String.valueOf(elder.getid()));
					     phoneTextView.setText(elder.getphone().toString());
					     addressTextView.setText(elder.getaddress().toString());
					     decrpitontTextView.setText(elder.getdescripiton().toString());
					     Log.i("patientid",elder.getdescripiton().toString());
				        
					     Bitmap bmimage =BitmapFactory.decodeByteArray(mContent, 0, mContent.length);
				         
			             imageView.setImageBitmap(rotateBitmap(bmimage));
			            
						}
					   
					} catch (Exception e)
					{
						// TODO: handle exception
					}
					break;
				default:
					break;
				}
			}
 	};
		
		/*
		 * camera功能
		 */
		caremabtn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
					Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
				    startActivityForResult(getImageByCamera, 1);
	     	}
		});
		
		
		/*
		 * 用于浏览相册功能
		 */
		gallerybtn.setOnClickListener(new OnClickListener()
		{		
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
				getImage.addCategory(Intent.CATEGORY_OPENABLE);
				getImage.setType("image/jpeg");
				startActivityForResult(getImage, 0);	
				
			}
		});
		
		/*
		 * 用于查询数据库是否有该用户信息
		 */
		checkButton =(Button)findViewById(R.id.databasecheck);
		checkButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				//elder=new elder(id, name, age, address, phone, des, im)
				try
				{
					nameString=nameTextView.getText().toString();
				if (nameString=="")
				{
					Toast.makeText(getApplicationContext(), "请输入查询的姓名", Toast.LENGTH_LONG).show();
				}
				else {
				Message msg= new Message();
				msg.what=3;
				handler.sendMessage(msg);
				}
				} catch (Exception e)
				{
					// TODO: handle exception
				}
				
			}
		});
 	  /*
 	   * 用于插入新的用户信息
 	   * @para input name,age,tele, description img
 	   *    id====name=====age====address======phone=====decription======img=======>>>>>> sqltite
 	   */
		writedb=(Button)findViewById(R.id.writedb);
		writedb.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{	 
				refresh();
				// TODO Auto-generated method stub
				Message msg= new Message();
				msg.what=2;
				handler.sendMessage(msg);
				 	
			}
		});
 
	/*
	 * 用于向服务器进行注册
	 */
		remoteButton.setOnClickListener(new OnClickListener()
		{
        
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
		    refresh();
			Message msgMessage=new Message();
			msgMessage.what=1;
			handler.sendMessage(msgMessage);		
			}
		});
		
		/*
		 * 用于返回
		 */
		backButton=(Button)findViewById(R.id.back);
		backButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra("back", "Back Data");
				setResult(5, intent);
				finish();
			}
		});

	}

	
	/*
	 * 用于更新输入的text信息
	 */
	public void refresh()
	{
		try
		{
			id=Integer.parseInt(idTextView.getText().toString());
			nameString=nameTextView.getText().toString();
			age=Integer.parseInt(ageTextView.getText().toString());
			phoneString=phoneTextView.getText().toString();
			address=addressTextView.getText().toString();
			des=decrpitontTextView.getText().toString();

		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	
	@ Override
	protected void onActivityResult ( int requestCode , int resultCode , Intent data )
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		ContentResolver resolver = getContentResolver();
	 
		if (requestCode == 0)
		{
			try
			{
				// 获得图片的uri
				Uri originalUri = data.getData();
				// 将图片内容解析成字节数组
				mContent = readStream(resolver.openInputStream(Uri.parse(originalUri.toString())));
				// 将字节数组转换为ImageView可调用的Bitmap对象
				myBitmap = getPicFromBytes(mContent, null);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				mContent = baos.toByteArray();
				// //把得到的图片绑定在控件上显示
				int width = myBitmap.getWidth();  
				int height = myBitmap.getHeight();                  
				//定义预转换成的图片的宽和高    
				int newWidth = 200;        
				int newHight = 200;      
				//计算缩放率，新尺寸除原尺寸    
				float scaleWidth = (float)newWidth/width;     
				float scaleHeight = (float)newHight/height;  
				//创建操作图片用的matrix对象    
				Matrix matrix = new Matrix();   
				//缩放图片动作        
				matrix.postScale(scaleWidth, scaleHeight);  
				//旋转图片动作      
				matrix.postRotate(90);  
				//创建新的图片        
			    resizedBitmap = Bitmap.createBitmap(myBitmap, 0, 0, width, height, matrix, true);
				imageView.setImageBitmap(resizedBitmap);
				
			} catch ( Exception e )
			{
				System.out.println(e.getMessage());
			}

		} else if (requestCode == 1)
		{
			try
			{
				super.onActivityResult(requestCode, resultCode, data);
				Bundle extras = data.getExtras();
				myBitmap = (Bitmap) extras.get("data");
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				mContent = baos.toByteArray();
			} catch ( Exception e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			// 把得到的图片绑定在控件上显示
			
			//获取这个图片的宽和高      
			int width = myBitmap.getWidth();  
			int height = myBitmap.getHeight();                  
			//定义预转换成的图片的宽和高    
			int newWidth = 200;        
			int newHight = 200;      
			//计算缩放率，新尺寸除原尺寸    
			float scaleWidth = (float)newWidth/width;     
			float scaleHeight = (float)newHight/height;  
			//创建操作图片用的matrix对象    
			Matrix matrix = new Matrix();   
			//缩放图片动作        
			matrix.postScale(scaleWidth, scaleHeight);  
			//旋转图片动作      
			matrix.postRotate(0);  
			//创建新的图片        
		     resizedBitmap = Bitmap.createBitmap(myBitmap, 0, 0, width, height, matrix, true);
			//将上面创建的Bitmap转换成Drawable对象，使得其可以使用在imageView，imageButton上。   
		//	BitmapDrawable bitmapDrawable = new BitmapDrawable(resizedBitmap);                   //创建一个ImageView         ImageView iv = new ImageView(this);                   //将imageView的图片设置为上面转换的图片         iv.setImageDrawable(bitmapDrawable); 
	
			imageView.setImageBitmap(resizedBitmap);
		}
	}

	public static Bitmap getPicFromBytes ( byte[] bytes , BitmapFactory.Options opts )
	{
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	public static byte[] readStream ( InputStream inStream ) throws Exception
	{
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1)
		{
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;

	}
  
	
	private Bitmap rotateBitmap(Bitmap bm)
	{
		int width = bm.getWidth();  
		int height = bm.getHeight();                  
		//定义预转换成的图片的宽和高    
		int newWidth = 160;        
		int newHight = 220;      
		//计算缩放率，新尺寸除原尺寸    
		float scaleWidth = (float)newWidth/width;     
		float scaleHeight = (float)newHight/height;  
		//创建操作图片用的matrix对象    
		Matrix matrix = new Matrix();   
		//缩放图片动作        
		matrix.postScale(scaleWidth, scaleHeight);  
		//旋转图片动作      
		matrix.postRotate(0);  
		//创建新的图片        
	   Bitmap   resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
	   return resizedBitmap;
	}
	
}