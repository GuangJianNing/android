package dataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyOpenHelper extends SQLiteOpenHelper {
    //创建表的语句
    private int version;
    private String CREATE_TABLE="CREATE TABLE login (id INTEGER PRIMARY KEY AUTOINCREMENT,username varchar(50),password varchar(50))";
    public MyOpenHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory,int version){
        super(context,dbName,factory,version);
        this.version=version;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建表的逻辑
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.d("onCreate", "被调用");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
