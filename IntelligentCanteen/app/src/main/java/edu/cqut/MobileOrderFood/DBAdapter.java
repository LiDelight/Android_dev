package edu.cqut.MobileOrderFood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBAdapter 
{

	private static final String DB_NAME = "dishes.db";
	private static final String DB_TABLE = "dishinfo";
	private static final int DB_VERSION = 1;
	 
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_IMGNAME = "imgname";
	public static final String KEY_PRICE = "price";
	
	private SQLiteDatabase db;
	private final Context context;
	private DBOpenHelper dbOpenHelper;
	
	public DBAdapter(Context _context) {
	    context = _context;
	  }

	  /** Close the database */
	  public void close() {
		  if (db != null){
			  db.close();
			  db = null;
		  }
		}

	  /** Open the database */
	  public void open() throws SQLiteException {  
		  dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
		  try {
			  db = dbOpenHelper.getWritableDatabase();
		  }
		  catch (SQLiteException ex) {
			  db = dbOpenHelper.getReadableDatabase();
		  }	  
		}
	  
	
	  public long insert(Dish dish) {
	    ContentValues newValues = new ContentValues();
	    newValues.put(KEY_ID, dish.mId);
	    newValues.put(KEY_NAME, dish.mName);
	    newValues.put(KEY_IMGNAME, dish.mImageName);
	    newValues.put(KEY_PRICE, dish.mPrice);
	    
	    return db.insert(DB_TABLE, null, newValues);
	  }


	  public ArrayList<Dish> queryAllData() {  
		  Cursor results =  db.query(DB_TABLE, new String[] { KEY_ID, KEY_NAME, KEY_IMGNAME, KEY_PRICE}, 
				  null, null, null, null, null);
		  return ConvertToDishes(results);   
	  }
	  
	  public Dish queryOneData(long id) {  
		  Cursor results =  db.query(DB_TABLE, new String[] { KEY_ID, KEY_NAME, KEY_IMGNAME, 
				  KEY_PRICE}, KEY_ID+"="+id, null, null, null, null);
		  return ConertToDish(results);   
	  }
	  
	  private Dish ConertToDish(Cursor cursor)
	  {
		  int resultCounts = cursor.getCount();
		  if (resultCounts == 0 || !cursor.moveToFirst()){
			  return null;
		  }
		  Dish theDish = new Dish();
		  theDish.mId = cursor.getInt(0);
		  theDish.mName = cursor.getString(cursor.getColumnIndex(KEY_NAME));
		  theDish.mImageName = cursor.getString(cursor.getColumnIndex(KEY_IMGNAME));
		  theDish.mPrice = cursor.getFloat(cursor.getColumnIndex(KEY_PRICE));

		  return theDish; 		  
	  }
	  
	  private ArrayList<Dish> ConvertToDishes(Cursor cursor){
		  int resultCounts = cursor.getCount();
		  if (resultCounts == 0 || !cursor.moveToFirst()){
			  return null;
		  }
		  ArrayList<Dish> dishes = new ArrayList<Dish>();
		  for (int i = 0 ; i<resultCounts; i++){
			  Dish theDish = new Dish();
			  theDish.mId = cursor.getInt(0);
			  theDish.mName = cursor.getString(cursor.getColumnIndex(KEY_NAME));
			  theDish.mImageName = cursor.getString(cursor.getColumnIndex(KEY_IMGNAME));
			  theDish.mPrice = cursor.getFloat(cursor.getColumnIndex(KEY_PRICE));
			  dishes.add(theDish);
			  
			  cursor.moveToNext();
		  }	  
		  return dishes; 
	  }
	  
	  public long deleteAllData() {
		  return db.delete(DB_TABLE, null, null);
	  }

	  public long deleteOneData(long id) {
		  return db.delete(DB_TABLE,  KEY_ID + "=" + id, null);
	  }

	  public long updateOneData(long id , Dish dish){
		  ContentValues updateValues = new ContentValues();	  
		  updateValues.put(KEY_NAME, dish.mName);
		  updateValues.put(KEY_IMGNAME, dish.mImageName);
		  updateValues.put(KEY_PRICE, dish.mPrice);
		  
		  return db.update(DB_TABLE, updateValues,  KEY_ID + "=" + id, null);
	  }
	  
	  /** 静态Helper类，用于建立、更新和打开数据库*/
	  private static class DBOpenHelper extends SQLiteOpenHelper 
	  {

		  public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
		    super(context, name, factory, version);
		  }

		  private static final String DB_CREATE = "create table " + 
		    DB_TABLE + " (" + KEY_ID + " integer primary key, " +
		    KEY_NAME+ " text not null, " + KEY_IMGNAME+ " text," + KEY_PRICE + " float);";

		  @Override
		  public void onCreate(SQLiteDatabase _db) {
		    _db.execSQL(DB_CREATE);
		  }

		  @Override
		  public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {		    
		    _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
		    onCreate(_db);
		  }
	}
	  
    /**创建菜品数据库 */
    public boolean FillDishTable(ArrayList<Dish> dishes)
    {
    	int s = dishes.size(); //得到列表元素个数
    	for (int i=0; i<s; i++)
    	{
    		Dish theDish = dishes.get(i);
    		if (insert(theDish) == -1)
    			return false;
    	}
    	return true;
    }
    
    /**取出菜品数据库中的数据*/
	public ArrayList<Map<String, Object>> getDishData()
	{
		//将菜品从数据库中填充进ArrayList列表
		ArrayList<Dish> dishes = queryAllData();
		ArrayList<Map<String, Object>> fooddata=new ArrayList<Map<String,Object>>();  
		//再将菜品从ArrayList中填充进foodinfo列表
		int s = dishes.size(); //得到菜品数量
		for (int i=0; i<s; i++) {
			Dish theDish = dishes.get(i); //得到当前菜品
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dishid", theDish.mId);	
			map.put("image", theDish.mImageName);
			map.put("title", theDish.mName);
			map.put("price", theDish.mPrice);		
			fooddata.add(map);
		}
		return fooddata;
	}

}