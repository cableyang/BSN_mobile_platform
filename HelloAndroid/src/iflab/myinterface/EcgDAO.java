package iflab.myinterface;

import java.sql.Date;
import java.sql.Time;

import iflab.model.ECG;
import iflab.model.elder;
import iflab.test.GraphicsData;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EcgDAO
{
  DBOpenHelper helper;
  SQLiteDatabase db;
  GraphicsData graphicsData;

  public EcgDAO(Context context)
  {
	 
	helper= new DBOpenHelper(context);  
  }
  
  /*
   * 创建表
   */
  public void createtable()
  {
	  db=helper.getWritableDatabase();
	  try
	{
		  db.execSQL("create table ECG_DATA (id integer key,name varchar(20),date date, time time, ecg double, bpm double)");
	} catch (Exception e)
	{
		// TODO: handle exception
	} 
  }
  
  /*
   * 删除表
   */
  public void deletetable()
{
	db=helper.getWritableDatabase();
	try
	{
		db.execSQL("DROP table ECG_DATA");
	} catch (Exception e)
	{
		// TODO: handle exception
	}
}
  
  
  public void add(ECG ecg)
	{
		db=helper.getWritableDatabase();
		db.execSQL("insert into ECG_DATA (id,name,date,time,ecg,bpm) values (?,?,?,?,?,?)", new Object[]
		{ ecg.getid(),ecg.getname(),ecg.getdate(), ecg.getTime(),ecg.getecg(),ecg.getbpm()});
	}
  
  public void bulkstore(GraphicsData graphicsData, ECG ecg)
  {
	  this.graphicsData=graphicsData;
	  db=helper.getWritableDatabase();
	  for (int j = 0; j < 14*3; j++)
	{
		db.execSQL("insert into ECG_DATA (id,name,date,time,ecg,bpm) values (?,?,?,?,?,?)", new Object[]
	  { ecg.getid(),ecg.getname(),ecg.getdate(), ecg.getTime(),this.graphicsData.data[j+485] ,ecg.getbpm()});
	}
	   }
  
	public void update(ECG ecg)
	{
		db=helper.getWritableDatabase();
		db.execSQL("update ECG_DATA set name = ?,date = ?,time = ? , ecg = ?,bpm= ? where  id = ?", new Object[]
       {ecg.getname(),ecg.getdate(), ecg.getTime(),ecg.getecg(),ecg.getbpm(),ecg.getid()});
	}
	
	public ECG findElder(int id)
	{
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select id,name,age,address,phone from ECG_DATA where  id = ?", new String[]
		{ String.valueOf(id)});
		
		if (cursor.moveToNext())
		{
			return new ECG(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("name")), null,null, cursor.getDouble(cursor.getColumnIndex("ecg")),cursor.getDouble(cursor.getColumnIndex("bpm")));
		}
		return null;
	}
	
	/*
	 * 删除id号的
	 */
	public void delete(int id)
	{
		db= helper.getWritableDatabase();
		db.execSQL("delete from ECG_DATA where id="+String.valueOf(id));
	}
  
}
