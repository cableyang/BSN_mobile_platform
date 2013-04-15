package iflab.test;

import iflab.myinterface.StudentDAO;
import android.Manifest.permission;
import android.R.integer;
import android.content.Context;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

    
/*
 * Class GraphicsData is the datahub and 
 * also hub for plot parameter for MyGraphics
 * version 1, 2013-3-25
 */
public class GraphicsData
{
	/*
	 * 定义采样频率，默认为2即500HZ的采样频率
	 */
	
    public static final int RATE250 = 250;
	public static final int RATE500 = 500;
	public static final int RATE1000 = 1000;
	public static int  rate=500;
	public double []data=new double[1000];//定义1000个点进行显示
 
    public GraphicsData(int myrate)
    {
    	rate=myrate; //设定速率为myrate
    	 
    }
    
    
  /*
   * 将P指针指向数据显示区域
   */
 public void putdata(double p[])
    {
    	    
      for (int i = 0; i < p.length; i++)
	  {
		 data[i] = p[i];  //装入数据
	  }
	  	
    }
    
 
 
 /*
  * 添加数据
  */
 public void adddata(double temp)
 {
	for(int i=0; i < RATE500-1; i++ )
	{
		data[i]=data[i+1];	
	}
	 data[RATE500-1]=temp;
	 
 }
 
 /*
  * 数据处理
  */
 public void dealwithstring(String string)
{
	 final int size=300;
	 int index1=0;
	 int index2=0;
	 int pack=(int)string.length()/((14*6+4*2));
	 //定义数据包中有14个点
     final int ref_vol=2;  //参考电压
	 final int value=0x7fffff;
     int packsize=14;
     Log.i("length", "string.length="+string.length()+"pack="+pack);
     
	//通过逐一法 求出第一个020a的标记  首先进行020a定位正寻  
     
		 for (int i = 0; i < string.length()-6; i++)
			{
				if(string.charAt(i)=='0')
				{
					if (string.charAt(i+1)=='2')
					{
						if (string.charAt(i+2)=='0')
						{
							if (string.charAt(i+3)=='a')
							{
								index1=i;
								Log.i("INDEX", string.substring(i, i+4)+" index1 "+index1+"  pack="+pack);break;
							}
						}
					}
					
				}
			}
		 
		//逆序 查找020a是否最后一次出现 
		 try
		{
			 for (int i = 1; i < string.length()-14*6-4*2-1; i++)
			{
				if(string.charAt(string.length()-i)=='a')
				{
					if (string.charAt(string.length()-i-1)=='0')
					{
						if (string.charAt(string.length()-i-2)=='2')
						{
							if (string.charAt(string.length()-i-3)=='0')
							{
								index2=i;
								Log.i("INDEX", string.substring(i, i+4)+" index2 "+index2);break;
							}
						}
					}
					
				}
			}
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		
		
		 //对020a之间的数据进行处理 xx xx xx 030a 020a
		
		 if (index1>13)
		{ 
			 int before=(int)(index1-4)/6;
			for (int i = 0; i < before; i++)
			{
				
				int temp = Integer.parseInt(string.substring(index1-4-6*before+i*6, index1-4-6*before+5+1+i*6),16);
				if(temp>0x7fffff)//表明为负数
				 {
					 temp=(~temp+1)&0x7fffff;
				 }
				 int dataadd=size-size*temp/value;
				 adddata(temp);
				 if ((dataadd)<0)
					{
						Log.i("tag", "first size-size*temp/value= "+(dataadd));
					}
			}
		}
		 
		 //计算020a~030a整数个数据包的信息
		 if ((pack>0)&(pack<5))
		{
			 for(int j=0; j< pack; j++)
		 {
			 for (int i = 0; i < packsize; i++)
				{
				 try
				{
				 int temp = Integer.parseInt(string.substring(index1+4+i*6+j*(14*6+4*2), index1+9+i*6+1+j*(14*6+4*2)),16);
				 if(temp>0x7fffff)//表明为负数
				 {
					 temp=(~temp+1)&0x7fffff;
				 }
			 
				 int dataadd=size-size*temp/value;
				 adddata(temp);
				 if ((dataadd)<0)
					{
						Log.i("tag", "second size-size*temp/value= "+(dataadd));
					}
				} catch (Exception e)
				{
					// TODO: handle exception
				}
					 
					
				} 
			 
		 }
		}
		
		 
		 int lastindex=index1+pack*(14*6+4*2);
		 int left = string.length()-lastindex-4;
		 
		 try
		{
			 if (left>10)
			{
				 if ((index2-1)>6 )
				{ 
				 for (int i = 0; i < (index2-1)/6; i++)
				{
			     int temp = Integer.parseInt(string.substring(string.length()-index2+i*6+1, string.length()+2-index2+5+i*6),16);
			     Log.i("log_tag", "left"+left+" string is"+ string.substring(string.length()-index2+i*6+1, 2+string.length()-index2+5+i*6));	
			     if(temp>0x7fffff)//表明为负数
				 {
					 temp=(~temp+1)&0x7fffff;
				 } 
				 adddata(temp);
				 
				}
				 
			//	 Log.i("log_tag", "left"+left+" string is"+ string.substring(string.length()-index2, string.length()));	
				
			}
			 
				 
				} 
			
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	
		 
	 }
	
 /*
  * 其它
  */
}
