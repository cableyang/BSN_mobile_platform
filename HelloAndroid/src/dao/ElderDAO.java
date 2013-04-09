package dao;

 
import iflab.model.elder;
 
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ElderDAO 
{
	private DBOpenHelper helper;
	private SQLiteDatabase db;
	
	public ElderDAO(Context context)
	{
		helper= new DBOpenHelper(context);
	}
	
	/*
	 * 创建表
	 */
	public void creattable()
	{
		db=helper.getWritableDatabase();
		try
		{
			db.execSQL("create table elder_test (id integer primary key,name varchar(20),age integer, address varchar(32), phone varchar(15))");
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	public void deletetable()
	{
		db=helper.getWritableDatabase();
		try
		{
			db.execSQL("DROP table elder_test");
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	public void addelder(elder elder)
	{
		db=helper.getWritableDatabase();
		db.execSQL("insert into elder_test (id,name,age,address,phone) values (?,?,?,?,?)", new Object[]
		{ elder.getid(), elder.getname(), elder.getage(), elder.getaddress(), elder.getphone()});
	}
	
	public void update(elder elder)
	{
		db=helper.getWritableDatabase();
		db.execSQL("update elder_id set name = ?,age = ?,address = ? , phone = ? where sid = ?", new Object[]
         {elder.getname(), elder.getage(), elder.getaddress(), elder.getphone(),elder.getid()});
	}
	
	public elder findElder(int id)
	{
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select id,name,age,address,phone from elder_id where sid = ?", new String[]
		{ String.valueOf(id)});
		
		if (cursor.moveToNext())
		{
			return new elder(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("name")), cursor.getInt(cursor.getColumnIndex("age")), cursor.getString(cursor.getColumnIndex("address")),cursor.getString(cursor.getColumnIndex("phone")));
		}
		return null;
	}
	
	/*
	 * 删除id号的
	 */
	public void delete(int id)
	{
		db= helper.getWritableDatabase();
		db.execSQL("delete from elder_id where id="+String.valueOf(id));
	}
	
	
	
}
