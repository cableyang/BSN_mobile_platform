package iflab.myinterface;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author  coolszy
 * @blog    http://blog.csdn.net/coolszy
 */
public class DBOpenHelper extends SQLiteOpenHelper
{
	private static final int VERSION = 1;
	private static final String DBNAME = "bsn.db";
	private static final String STUDENT="t_student";
	
	public DBOpenHelper(Context context)
	{
		super(context, DBNAME, null, VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		try
		{		 		  	
			//db.execSQL("create table "+STUDENT+" (sid integer primary key,name varchar(20),age integer)");
			//db.execSQL("CREATE TABLE [doctor] ( [id] INTEGER(8) NOT NULL, [name] CHAR(32), [hospital] CHAR(152), [phone] INTEGER(15),CONSTRAINT [sqlite_autoindex_doctor_1] PRIMARY KEY ([id]));");
		} catch (Exception e)
		{
			// TODO: handle exception
			Log.e("创建数据库出错", e.getMessage());
		}
		//	db.execSQL("create table "+STUDENT+" (sid integer, name varchar(20), age integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.i("StudentDAOTest", "UpGrade!");
	}

}
