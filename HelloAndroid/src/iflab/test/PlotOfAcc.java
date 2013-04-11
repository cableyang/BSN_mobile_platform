package iflab.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.View;

public class PlotOfAcc extends View implements Runnable{	//�Զ���View
	
	GraphicsData graphicsDataX;
	GraphicsData graphicsDataY;
	GraphicsData graphicsDataZ;
	private Paint paint=null;	
	//�������ʶ���
	static int a=0;
	public PlotOfAcc(Context context, int rate, GraphicsData datax, GraphicsData datay, GraphicsData dataz) {
		super(context);
		// TODO Auto-generated constructor stub
     	graphicsDataX=datax;
     	graphicsDataY=datay;
     	graphicsDataZ=dataz;
		paint=new Paint();							//��������
     		
		new Thread(this).start();					//�����߳�
	}
	
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		paint.setAntiAlias(true);	//���û���Ϊ�޾��	
		canvas.drawColor(Color.DKGRAY);				//��ɫ����canvasΪ����
		
		
		//*************************************************
		//*****************���ݶ������x����ٶ�����**********
		paint.setStrokeWidth((float) 4.0);				//�߿�
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.RED);	//���û�����ɫ
		Path pathX = new Path();						//Path����
		
		pathX.moveTo(0, (float) graphicsDataX.data[0]);						//��ʼ��
		for(int i=0; i<1000; i++)
		{
			pathX.lineTo(i, (float) graphicsDataX.data[i]);
		}
		 canvas.drawPath(pathX, paint);				
		   
		 
		//*************************************************
			//*****************���ݶ������y����ٶ�����**********
			paint.setStrokeWidth((float) 4.0);				//�߿�
			paint.setStyle(Style.STROKE);
			paint.setColor(Color.BLUE);	//���û�����ɫ
			Path pathY = new Path();						//Path����
			
			pathY.moveTo(0, (float) graphicsDataY.data[0]);						//��ʼ��
			for(int i=0; i<1000; i++)
			{
				pathY.lineTo(i, (float) graphicsDataY.data[i]);
			}
			 canvas.drawPath(pathY, paint);		
		    
				//*************************************************
				//*****************���ݶ������Z����ٶ�����**********
				paint.setStrokeWidth((float) 4.0);				//�߿�
				paint.setStyle(Style.STROKE);
				paint.setColor(Color.YELLOW);	//���û�����ɫ
				Path pathZ = new Path();						//Path����
				
				pathY.moveTo(0, (float) graphicsDataZ.data[0]);						//��ʼ��
				for(int i=0; i<1000; i++)
				{
					pathZ.lineTo(i, (float) graphicsDataZ.data[i]);
				}
				 canvas.drawPath(pathZ, paint);		 
			 
				 
				 
		    //��������ɺ��
		    paint.setAntiAlias(true);	//���û���Ϊ�޾��
			paint.setColor(Color.BLACK);	//���û�����ɫ 
			paint.setStrokeWidth((float) 1.0);				//�߿�
			paint.setStyle(Style.STROKE);
			Path gridpath = new Path();						//Path����
			//���ݶ���
			//������������
			for(int i=0; i<10; i++)
			{
				gridpath.moveTo(i*50, 0);	
				gridpath.lineTo(i*50, 500);
				canvas.drawText(""+i*50, i*50, 300, paint);
			}
			    canvas.drawPath(gridpath, paint);					//������������  
			    
			  //���ƺ���ɺ��
			    paint.setAntiAlias(true);	//���û���Ϊ�޾��
				paint.setColor(Color.BLACK);	//���û�����ɫ 
				paint.setStrokeWidth((float) 1.0);				//�߿�
				paint.setStyle(Style.STROKE);
				Path gridpath2 = new Path();						//Path����
				//���ݶ���
				//������������
				for(int i=0; i<10; i++)
				{
					gridpath2.moveTo(0,i*50);	
					gridpath2.lineTo(500, i*50);
					canvas.drawText(""+(300-i*50), 0, i*50, paint);
				}
				    canvas.drawPath(gridpath2, paint);					//������������  
		    
	}

	@Override
	public void run() {								//����run����
		// TODO Auto-generated method stub
		while(!Thread.currentThread().isInterrupted())
		{
			
			try
			{
				Thread.sleep(100);
				//Log.i("ECG_PLOG", "The plog thread is"+Thread.currentThread().getId());
			}
			catch(InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
			//Log.i("HELLO", "this is that");
			String msg="";
			msg.format("%d", a);
			Log.i("HELLO", msg);
			postInvalidate();						//���½���
		}
	}
}