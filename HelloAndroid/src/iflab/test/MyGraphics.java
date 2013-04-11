package iflab.test;									//包名

import java.lang.Math;
import org.apache.http.impl.conn.SingleClientConnManager;

import android.R.string;
import android.content.Context;							//导入类文件
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.test.AndroidTestCase;
import android.util.Log;
import android.view.View;


/*
 * Function  MyGraphics use canvas and paint to draw a line with 250 to 1000 points
 * @Author YangHua cabelyang@126.com 
 * @Parameter 
 */
public class MyGraphics extends View implements Runnable{	//自定义View
	
	GraphicsData graphicsData;
	private Paint paint=null;	
	//声明画笔对象
	static int a=0;
	public MyGraphics(Context context, int rate, GraphicsData pointGraphicsData) {
		super(context);
		// TODO Auto-generated constructor stub
		graphicsData= pointGraphicsData;  //由指针指向传过来的数据
		paint=new Paint();							//构建对象
		new Thread(this).start();					//开启线程
	}
	
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		paint.setAntiAlias(true);	//设置画笔为无锯齿
		paint.setColor(Color.RED);	//设置画笔颜色
		canvas.drawColor(Color.DKGRAY);				//白色背景
		paint.setStrokeWidth((float) 4.0);				//线宽
		paint.setStyle(Style.STROKE);
		Path path = new Path();						//Path对象
		//数据读入
		path.moveTo(0, (float) graphicsData.data[0]);						//起始点
		for(int i=0; i<1000; i++)
		{
			path.lineTo(i, (float) graphicsData.data[i]);
		}
		    canvas.drawPath(path, paint);					//绘制任意多边形
		    
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
					gridpath2.moveTo(0,i*50 );	
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
