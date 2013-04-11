package iflab.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.View;

public class PlotOfAcc extends View implements Runnable{	//自定义View
	
	GraphicsData graphicsDataX;
	GraphicsData graphicsDataY;
	GraphicsData graphicsDataZ;
	private Paint paint=null;	
	//声明画笔对象
	static int a=0;
	public PlotOfAcc(Context context, int rate, GraphicsData datax, GraphicsData datay, GraphicsData dataz) {
		super(context);
		// TODO Auto-generated constructor stub
     	graphicsDataX=datax;
     	graphicsDataY=datay;
     	graphicsDataZ=dataz;
		paint=new Paint();							//构建对象
     		
		new Thread(this).start();					//开启线程
	}
	
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		paint.setAntiAlias(true);	//设置画笔为无锯齿	
		canvas.drawColor(Color.DKGRAY);				//白色背景canvas为画布
		
		
		//*************************************************
		//*****************数据读入绘制x轴加速度曲线**********
		paint.setStrokeWidth((float) 4.0);				//线宽
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.RED);	//设置画笔颜色
		Path pathX = new Path();						//Path对象
		
		pathX.moveTo(0, (float) graphicsDataX.data[0]);						//起始点
		for(int i=0; i<1000; i++)
		{
			pathX.lineTo(i, (float) graphicsDataX.data[i]);
		}
		 canvas.drawPath(pathX, paint);				
		   
		 
		//*************************************************
			//*****************数据读入绘制y轴加速度曲线**********
			paint.setStrokeWidth((float) 4.0);				//线宽
			paint.setStyle(Style.STROKE);
			paint.setColor(Color.BLUE);	//设置画笔颜色
			Path pathY = new Path();						//Path对象
			
			pathY.moveTo(0, (float) graphicsDataY.data[0]);						//起始点
			for(int i=0; i<1000; i++)
			{
				pathY.lineTo(i, (float) graphicsDataY.data[i]);
			}
			 canvas.drawPath(pathY, paint);		
		    
				//*************************************************
				//*****************数据读入绘制Z轴加速度曲线**********
				paint.setStrokeWidth((float) 4.0);				//线宽
				paint.setStyle(Style.STROKE);
				paint.setColor(Color.YELLOW);	//设置画笔颜色
				Path pathZ = new Path();						//Path对象
				
				pathY.moveTo(0, (float) graphicsDataZ.data[0]);						//起始点
				for(int i=0; i<1000; i++)
				{
					pathZ.lineTo(i, (float) graphicsDataZ.data[i]);
				}
				 canvas.drawPath(pathZ, paint);		 
			 
				 
				 
		    //绘制纵向珊格
		    paint.setAntiAlias(true);	//设置画笔为无锯齿
			paint.setColor(Color.BLACK);	//设置画笔颜色 
			paint.setStrokeWidth((float) 1.0);				//线宽
			paint.setStyle(Style.STROKE);
			Path gridpath = new Path();						//Path对象
			//数据读入
			//绘制纵向坐标
			for(int i=0; i<10; i++)
			{
				gridpath.moveTo(i*50, 0);	
				gridpath.lineTo(i*50, 500);
				canvas.drawText(""+i*50, i*50, 300, paint);
			}
			    canvas.drawPath(gridpath, paint);					//绘制任意多边形  
			    
			  //绘制横向珊格
			    paint.setAntiAlias(true);	//设置画笔为无锯齿
				paint.setColor(Color.BLACK);	//设置画笔颜色 
				paint.setStrokeWidth((float) 1.0);				//线宽
				paint.setStyle(Style.STROKE);
				Path gridpath2 = new Path();						//Path对象
				//数据读入
				//绘制纵向坐标
				for(int i=0; i<10; i++)
				{
					gridpath2.moveTo(0,i*50);	
					gridpath2.lineTo(500, i*50);
					canvas.drawText(""+(300-i*50), 0, i*50, paint);
				}
				    canvas.drawPath(gridpath2, paint);					//绘制任意多边形  
		    
	}

	@Override
	public void run() {								//重载run方法
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
			postInvalidate();						//更新界面
		}
	}
}