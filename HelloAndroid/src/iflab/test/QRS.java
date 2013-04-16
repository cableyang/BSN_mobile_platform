package iflab.test;

import android.R.integer;

public class QRS
{
	  public static final int M = 5; 
	  int[]sig0;
	  int nsamp;
	  
	  public  QRS(double[] sample)
	{
		 sig0=new int[sample.length];
		  for (int i = 0; i < sample.length; i++)
		{
			sig0[i]=(int)sample[i];
		}
		 
		nsamp=500;
	}
	  
 
	public double[] filter()
	{
		  float[] highPass = highPass(sig0, nsamp); 
		  float[] lowPass = lowPass(highPass, nsamp);   
		  int[] QRS1 = QRS_algorithm(lowPass, nsamp); 
		  double[]ret=new double[QRS1.length];
		  for (int i = 0; i < QRS1.length; i++)
		{
			ret[i]=QRS1[i];
		}
		  
		  
		return ret;
	}
	    
	  public static float[] highPass(int[] sig0, int nsamp)
	  {        
	  float[] highPass = new float[nsamp];  
	  float constant = (float) 1/M;     
	  for(int i=0; i<sig0.length; i++)
	  {            
		  float y1 = 0;          
		  float y2 = 0;         
		  int y2_index = i-((M+1)/2);      
		  if(y2_index < 0) 
		  {                 
			  y2_index = nsamp + y2_index;   
		  }            
		  y2 = sig0[y2_index];     
		  float y1_sum = 0;      
		  for(int j=i; j>i-M; j--) 
		  {                
			  int x_index = i - (i-j);   
			  if(x_index < 0) 
			   {                
				  x_index = nsamp + x_index;   
				}                
			  y1_sum += sig0[x_index];      
	       }           
		  y1 = constant * y1_sum; 
		  highPass[i] = y2 - y1;        
		  }                   
	  return highPass;     
	  }       
	  
	  
	  
	  public static float[] lowPass(float[] sig0, int nsamp) 
	  {        
		  float[] lowPass = new float[nsamp];   
		  for(int i=0; i<sig0.length; i++) 
		  {            
			  float sum = 0;       
			  if(i+30 < sig0.length) 
			  {                 
				  for(int j=i; j<i+30; j++) 
				  {                    
					  float current = sig0[j] * sig0[j];   
					  sum += current;          
					  }           
				  }             
			  else if(i+30 >= sig0.length) 
			  {                
				  int over = i+30 - sig0.length; 
				  for(int j=i; j<sig0.length; j++)
				  {                     
					  float current = sig0[j] * sig0[j]; 
					  sum += current;           
				}                
				  for(int j=0; j<over; j++)
				  {                    
					  float current = sig0[j] * sig0[j];   
					  sum += current;            
					  }             
				  }              
			  lowPass[i] = sum;     
			  }           
		  return lowPass;       
		  }       
	  
	  
	  
	  public static int[] QRS_algorithm(float[] lowPass, int nsamp) 
	  {         
		  int[] QRS = new int[nsamp];   
		  double treshold = 0;         
		  for(int i=0; i<200; i++) 
		  {             
			  if(lowPass[i] > treshold)
			  {               
				  treshold = lowPass[i];  
			  }        
	     }          
		  int frame = 250;  
		  for(int i=0; i<lowPass.length; i+=frame)
		  {            
			  float max = 0;  
			  int index = 0;   
			  if(i + frame > lowPass.length) 
			  {                
				  index = lowPass.length;    
		      }             
			  else 
			  {            
				  index = i + frame;    
		      }            
			  for(int j=i; j<index; j++)
			  {                
				  if(lowPass[j] > max) max = lowPass[j];  
			  }             
			  boolean added = false;            
			  for(int j=i; j<index; j++)
			  {               
				  if(lowPass[j] > treshold && !added)
				  {                   
					  QRS[j] = 1;               
					  added = true;           
				 }                 
				  else {                   
					  QRS[j] = 0;             
					  }            
				  }               
			  double gama = (Math.random() > 0.5) ? 0.15 : 0.20;     
			  double alpha = 0.01 + (Math.random() * ((0.1 - 0.01)));     
			  treshold = alpha * gama * max + (1 - alpha) * treshold;     
			  }           
		  return QRS;     
		  }       
	  
	  /*
	  public static int getNrOfSamps(String record) 
	  {        
		  WFDB_SiginfoArray siarray = new WFDB_SiginfoArray(2);
		  if(wfdb.isigopen(record, siarray.cast(), 2) < 2) 
			  System.exit(1);         
		  return siarray.cast().getNsamp(); 
		  }       
	  */
	  /*
	  public static int[] readData(int[] sig0, String record) 
	  {       
		  WFDB_SiginfoArray siarray = new WFDB_SiginfoArray(2);    
		  if(wfdb.isigopen(record, siarray.cast(), 2) < 2)    
			  System.exit(1);           
		  WFDB_SampleArray v = new WFDB_SampleArray(2); 
		  for(int i=0; i<siarray.cast().getNsamp(); i++) 
		  {           
			  if(wfdb.getvec(v.cast()) < 0)        
				  break;             
			  sig0[i] = v.getitem(0);     
		  }         
		  return sig0;     
	  }       
	  */
	  
	  /*
	  public static void writeResults(int[] QRS, String record) 
	  {       
		  WFDB_Anninfo annInfo = new WFDB_Anninfo(); 
		  WFDB_Annotation annot = new WFDB_Annotation();  
		  annInfo.setName("qrs");       
		  annInfo.setStat(wfdb.WFDB_WRITE);      
		  if(wfdb.annopen(record, annInfo, 1) < 0) 
		  {             
			  System.err.println("Problem pri pisanju v datoteko.");  
			  return;       
			  }           
		  
		  annot.setSubtyp(0);       
		  annot.setChan(0);      
		  annot.setNum(0);       
		  annot.setAux(null);        
		  for(int i=0; i<QRS.length; i++)
		  {            
			  if(QRS[i] != 0) 
			  {                 
				  annot.setAnntyp(QRS[i]);    
				  annot.setTime(i);     
				  int error;               
				  if((error=wfdb.putann(0, annot)) < 0) 
				  {                     
					  System.err.println("Napaka pri pisanju v QRS datoteko.");  
					  return;               
					  }             
				  }        
			  }           
		  wfdb.wfdbquit();       
		  }       
	  }
	  
	  */
}

