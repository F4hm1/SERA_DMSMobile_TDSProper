package com.serasiautoraya.tdsproper.SQLIte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Randi Dwi Nandra on 11/04/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TDSProper.db";

    public static final String NOTIFICATION_TABLE_NAME = "NotificationHistory";
    public static final String NOTIFICATION_COLUMN_ID = "NotificationId";
    public static final String NOTIFICATION_COLUMN_TITLE = "Title";
    public static final String NOTIFICATION_COLUMN_MESSAGE = "Message";
    public static final String NOTIFICATION_COLUMN_DATE = "Date";

    static DBHelper instance;

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + NOTIFICATION_TABLE_NAME + " (" +
                        NOTIFICATION_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        NOTIFICATION_COLUMN_TITLE + " TEXT," +
                        NOTIFICATION_COLUMN_MESSAGE + " TEXT," +
                        NOTIFICATION_COLUMN_DATE + " TEXT" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION_TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(String tableName, ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(tableName, null, contentValues);
        return true;
    }

    public void update(String tableName, ContentValues contentValues,String condition){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(tableName, contentValues, condition, null);
    }

    public Cursor runRawQuery(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();
        return res;
    }


}
