package iflab.model;

import java.sql.Date;
import java.sql.Time;


/*
 * the ecg table`s format is 
 * id====name====date====time====ecg=====bpm===
 */
public class ECG
{
    int id;
	String name;
	Date date;
	Time time;
	double ecg;
	double bpm;
	
	
	public ECG(int id, String name,Date date, Time time, double ecg, double bpm)
	{
		super();
	    this.id=id;
	    this.name=name;
	    this.date=date;
	    this.time=time;
	    this.ecg=ecg;
	    this.bpm=bpm;
	}
	
	public String getname()
	{
		return name;
	}
	public void setname(String name)
	{
		this.name=name;
	}
	
	public int getid()
	{
		return id;
	}
	public void setid(int id)
	{
		this.id=id;
	}
	
	public Date getdate()
	{
		return date;	
	}
	public void setdate(Date date)
	{
		this.date=date;
	}
	
	public Time getTime ()
	{
		return time;	
	}
	public void settime(Time time)
	{
		this.time =time ;
	}
	
	public double getecg()
	{
		return ecg;	
	}
	public void setecg(double ecg)
	{
		this.ecg=ecg;
	}
	
	public double getbpm()
	{
		return bpm;	
	}
	public void setbpm(double bpm)
	{
		this.bpm=bpm;
	}
	
	public String toString()
	{
		return "id=" + id + ";name=" + name + ";date=" + date+";time="+time+";ecg="+ecg+";bpm"+bpm;
	}
	
}
