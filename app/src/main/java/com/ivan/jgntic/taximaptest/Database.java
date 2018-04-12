package com.ivan.jgntic.taximaptest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by IVAN on 30.11.2016 г..
 */

public class Database extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=3;
    public static final String DATABASE_TABLE="database_table";
    public static final String DATABASE_NAME="database_name";

    public static final String COLUMN_ID="column_id";
    public static final String COLUMN_USERNAME="column_name";
    public static final String COLUMN_PASSWORD="column_password";

    SQLiteDatabase ourDatabase;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ DATABASE_TABLE + " (" +
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
        COLUMN_USERNAME + " TEXT NOT NULL, " +
        COLUMN_PASSWORD + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_TABLE);
        onCreate(db);
    }

    public void open()
    {
        ourDatabase=getReadableDatabase();
        ourDatabase=getWritableDatabase();
    }
    public void close()
    {
        ourDatabase.close();
    }

    public long insert(String username,String password){

        ContentValues cv =new ContentValues();
        cv.put(COLUMN_USERNAME,username);
        cv.put(COLUMN_PASSWORD,password);

        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public Cursor getCurrentUser(String username, String password)
    {
        return ourDatabase.query(DATABASE_TABLE,new String[]{COLUMN_USERNAME,COLUMN_PASSWORD},
                COLUMN_USERNAME + " =? " + " AND " + COLUMN_PASSWORD + " =? ",new String[]{username,password},null,null,null);
    }

    public boolean checkIfDatabaseIsEmpty()
    {
        Cursor c=ourDatabase.rawQuery("SELECT * FROM "+ DATABASE_TABLE,null);

        if(c.moveToFirst())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
