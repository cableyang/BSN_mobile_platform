package iflab.test;

import iflab.myinterface.StudentDAO;
import android.Manifest.permission;
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
	 * �������Ƶ�ʣ�Ĭ��Ϊ2��500HZ�Ĳ���Ƶ��
	 */
	
    public static final int RATE250 = 250;
	public static final int RATE500 = 500;
	public static final int RATE1000 = 1000;
	public static int  rate=2;
	double []data=new double[1000];//����1000���������ʾ
 
    public GraphicsData(int myrate)
    {
    	rate=myrate; //�趨����Ϊmyrate
    	 
    }
    
    
  /*
   * ��Pָ��ָ��������ʾ����
   */
 public void putdata(double p[])
    {
    	    
      for (int i = 0; i < p.length; i++)
	  {
		 data[i] = p[i];  //װ������
	  }
	  	
    }
    
 
 
 /*
  * �������
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
  * ���ݴ���
  */
 public void dealwithstring(String string)
{
	 final String format="020a";
	 int index1=0;
	 int index2;

	  
	 
	//ͨ����һ�� �����һ��020a�ı��
	 if(string.length() > 14*6*6)  //����4�����ݷ������
	 {
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
								Log.i("INDEX", string.substring(i, i+4));break;
							}
						}
					}
					
				}
			}
			 //�������ݰ�����14����
		    final int value=65535;
			int packsize=14;
			//ͨ��index1�������ݴ���
			Log.i("RESULT", string);
			for (int i = 0; i < packsize; i++)
			{
				 int temp = Integer.parseInt(string.substring(index1+4+i*6, index1+9+i*6+1),16);
				 if(temp>0x7fffff)//����Ϊ����
				 {
					 temp=(~temp+1)&0x7fffff;
				 }
				 adddata(200-200*temp/value);
			} 
			
			
			//
			index2=index1+3+6*14+5;
			if(string.charAt(index2+3)=='a')
			{
				for (int i = 0; i < packsize; i++)
				{
					 int temp = Integer.parseInt(string.substring(index2+4+i*6, index2+9+i*6+1),16);
					 if(temp>0x7fffff)//����Ϊ����
					 {
						 temp=(~temp+1)&0x7fffff;
					 }
					 adddata(200-200*temp/value);
				} 	
			}
			
			index2=index2+3+6*14+5;
			if(string.charAt(index2+3)=='a')
			{
			for (int i = 0; i < packsize; i++)
			{
				 int temp = Integer.parseInt(string.substring(index2+4+i*6, index2+9+i*6+1),16);
				 if(temp>0x7fffff)//����Ϊ����
				 {
					 temp=(~temp+1)&0x7fffff;
				 }
				 adddata(200-200*temp/value);
			} 
		 }
	 } 
		 
	 }
	
 /*
  * ����
  */
}
