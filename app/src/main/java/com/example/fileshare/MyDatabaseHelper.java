package com.example.fileshare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	public static final String CREATE_USER = "create table User ("
			+ "id integer primary key autoincrement, "
			+ "username text, "
			+ "password text)";
	private Context context;

	/**
	 * @param context 第一个参数是Context，必须要有Context才能对数据库进行操作。
	 * @param name 第二参数是数据库名，创建数据库时使用的就是这个名称。
	 * @param factory 第三个参数允许我们在查询数据的时候返回一个自定义的Cursor，一般都是传如null。
	 * @param version 第四个参数表示当前数据库的版本号，可用于对数据库进行升级操作。
	 */

	public MyDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO: 17/3/8 Auto-generated method stub
		this.context = context;
	}

	/**
	 * @param database
	 */
	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO: 17/3/8 Auto-generated method stub
		database.execSQL(CREATE_USER);
		Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show();
	}

	/**
	 * @param database
	 * @param oldVersion
	 * @param newVersion
	 */
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		// TODO: 17/3/8 Auto-generated method stub
	}
}
