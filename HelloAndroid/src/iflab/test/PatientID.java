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
	//UI��ť����
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
		 * ���ڲ�ѯ���ݿ��Ƿ��и��û���Ϣ
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
 	   * ���ڲ����µ��û���Ϣ
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
				  elder.setname((String) nameTextView.getText());  //װ��name
				  elder.setage(Integer.parseInt((String) ageTextView.getText()));  //��ȡage
			      elder.setphone((String) phoneTextView.getText()); //��ȡ�绰
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
		 * ���ڷ���
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
				// ���ͼƬ��uri
				Uri originalUri = data.getData();
				// ��ͼƬ���ݽ������ֽ�����
				mContent = readStream(resolver.openInputStream(Uri.parse(originalUri.toString())));
				// ���ֽ�����ת��ΪImageView�ɵ��õ�Bitmap����
				myBitmap = getPicFromBytes(mContent, null);
				// //�ѵõ���ͼƬ���ڿؼ�����ʾ
				int width = myBitmap.getWidth();  
				int height = myBitmap.getHeight();                  
				//����Ԥת���ɵ�ͼƬ�Ŀ�͸�    
				int newWidth = 200;        
				int newHight = 200;      
				//���������ʣ��³ߴ��ԭ�ߴ�    
				float scaleWidth = (float)newWidth/width;     
				float scaleHeight = (float)newHight/height;  
				//��������ͼƬ�õ�matrix����    
				Matrix matrix = new Matrix();   
				//����ͼƬ����        
				matrix.postScale(scaleWidth, scaleHeight);  
				//��תͼƬ����      
				matrix.postRotate(90);  
				//�����µ�ͼƬ        
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
			// �ѵõ���ͼƬ���ڿؼ�����ʾ
			
			//��ȡ���ͼƬ�Ŀ�͸�      
			int width = myBitmap.getWidth();  
			int height = myBitmap.getHeight();                  
			//����Ԥת���ɵ�ͼƬ�Ŀ�͸�    
			int newWidth = 200;        
			int newHight = 200;      
			//���������ʣ��³ߴ��ԭ�ߴ�    
			float scaleWidth = (float)newWidth/width;     
			float scaleHeight = (float)newHight/height;  
			//��������ͼƬ�õ�matrix����    
			Matrix matrix = new Matrix();   
			//����ͼƬ����        
			matrix.postScale(scaleWidth, scaleHeight);  
			//��תͼƬ����      
			matrix.postRotate(0);  
			//�����µ�ͼƬ        
		     resizedBitmap = Bitmap.createBitmap(myBitmap, 0, 0, width, height, matrix, true);
			//�����洴����Bitmapת����Drawable����ʹ�������ʹ����imageView��imageButton�ϡ�   
		//	BitmapDrawable bitmapDrawable = new BitmapDrawable(resizedBitmap);                   //����һ��ImageView         ImageView iv = new ImageView(this);                   //��imageView��ͼƬ����Ϊ����ת����ͼƬ         iv.setImageDrawable(bitmapDrawable); 
	
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