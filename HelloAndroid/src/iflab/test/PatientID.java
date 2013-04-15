package iflab.test;

import iflab.model.elder;
import iflab.myinterface.ElderDAO;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
 
public class PatientID extends Activity
{
	/** Called when the activity is first created. */
	private ImageView imageView;
	 
	private Bitmap myBitmap;
	private byte[] mContent;
	Bitmap resizedBitmap;
	//UI按钮界面
	Button caremabtn,gallerybtn;
	Button backButton;
	Button checkButton,twodabaseButton;
    TextView nameTextView;
    TextView ageTextView;
    TextView phoneTextView;
    TextView decrpitontTextView;
	ElderDAO elderDAO;
	elder elder;
	
	@ Override
	public void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patientid);
		
		nameTextView=(TextView)findViewById(R.id.name);
		ageTextView=(TextView)findViewById(R.id.age);
		 phoneTextView=(TextView)findViewById(R.id.myphone);
		decrpitontTextView=(TextView)findViewById(R.id.decrpiton);
		
		imageView = (ImageView) findViewById(R.id.imageView);
		caremabtn=(Button)findViewById(R.id.camera);
		gallerybtn=(Button)findViewById(R.id.gallery);
		
		
		elderDAO=new ElderDAO(getBaseContext());
		elderDAO.creattable();
		
		
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
				 	elder=new elder(1, "CABLEYANG", 25, "NANJING SEU", "15295508253", "very healthy", null);
				    elder=elderDAO.findElder(1);
				    byte []b=elder.getimg();
				     ageTextView.setText(String.valueOf(elder.getage()));
				     Log.i("patientid","the elder age is.."+ elder.getage());
				     nameTextView.setText(elder.getname());
				     phoneTextView.setText(elder.getphone());
				     decrpitontTextView.setText(elder.getdescripiton());
				     Log.i("patientid",elder.getdescripiton());
			         Bitmap bmimage =BitmapFactory.decodeByteArray(b, 0, b.length);		           
		            imageView.setImageBitmap(bmimage);
				} catch (Exception e)
				{
					// TODO: handle exception
				}
				
				 
			}
		});
 	  /*
 	   * 用于插入新的用户信息
 	   * @para input name,age,tele, description img
 	   *    name age tele decription  img =======>>>>>> sqltite
 	   */
		twodabaseButton=(Button)findViewById(R.id.todatabase);
		twodabaseButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				  
				// TODO Auto-generated method stub
				try
				{
				  // input some ready information	
				  elder.setname((String) nameTextView.getText());  //装载name
				  elder.setage(Integer.parseInt((String) ageTextView.getText()));  //读取age
			      elder.setphone((String) phoneTextView.getText()); //读取电话
			      elder.setimg(mContent);
					//elder=new elder(1, "CABLEYANG", 25, "NANJING SEU", "15295508253", "very healthy", mContent);
			     elderDAO.addelder(elder); 
			      
				} catch (Exception e)
				{
					// TODO: handle exception
					
				}
				 	
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
				myBitmap.compress(Bitmap.CompressFormat.JPEG, 1, baos);
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
  
	
}