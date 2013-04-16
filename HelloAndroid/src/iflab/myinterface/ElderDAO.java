package iflab.myinterface;

 
import iflab.model.elder;
 
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ElderDAO 
{
	private DBOpenHelper helper;
	private SQLiteDatabase db;
	private String tablename="elder_in";
	
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
			db.execSQL("create table "+tablename+"(id integer primary key,name varchar(20),age integer, address varchar(32), phone varchar(15), decription text, img blob)");
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
			db.execSQL("DROP table "+tablename);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	public void addelder(elder elder)
	{
		db=helper.getWritableDatabase();
		db.execSQL("insert into "+tablename+" (id,name,age,address,phone,decription,img) values (?,?,?,?,?,?,?)", new Object[]
		{ elder.getid(), elder.getname(), elder.getage(), elder.getaddress(), elder.getphone(),elder.getdescripiton(),elder.getimg()});
	}
	
	public void update(elder elder)
	{
		db=helper.getWritableDatabase();
		db.execSQL("update "+tablename+"  set name = ?,age = ?,address = ? , phone = ?,decription=?,img=? where id = ?", new Object[]
         {elder.getname(), elder.getage(), elder.getaddress(), elder.getphone(),elder.getdescripiton(),elder.getimg(),elder.getid()});
	}
	
	
	/*
	 * 通过id进行查询
	 */
	public elder findElderbyid(int id)
	{
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select id,name,age,address,phone,decription,img from "+tablename+"  where id = ?", new String[]
		{ String.valueOf(id)});
		
		if (cursor.moveToNext())
		{
			return new elder(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("name")), cursor.getInt(cursor.getColumnIndex("age")), cursor.getString(cursor.getColumnIndex("address")),cursor.getString(cursor.getColumnIndex("phone")),cursor.getString(cursor.getColumnIndex("decription")),cursor.getBlob(cursor.getColumnIndex("img")));
		}
		return null;
	}
	
	
	public elder findElderbyname(String name)
	{
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select id,name,age,address,phone,decription,img from "+tablename+"  where name = ? ", new String[]
		{ name});
		
		if (cursor.moveToNext())
		{                                                                                                                                                                                                                           //select id,name,age,address,phone,decription,img
			return new elder(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("name")), cursor.getInt(cursor.getColumnIndex("age")), cursor.getString(cursor.getColumnIndex("address")),cursor.getString(cursor.getColumnIndex("phone")),cursor.getString(cursor.getColumnIndex("decription")),cursor.getBlob(cursor.getColumnIndex("img")));
		}
		return null;
	}
	
	/*
	 * 删除id号的
	 */
	public void delete(int id)
	{
		db= helper.getWritableDatabase();
		db.execSQL("delete from "+tablename+"  where id="+String.valueOf(id));
	}
	
	
	
}
