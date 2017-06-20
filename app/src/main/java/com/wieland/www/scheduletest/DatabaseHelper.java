package com.wieland.www.scheduletest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wulka on 20.06.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "schedule.db";
    public static final String TABLE_NAME = "day1";
    public static final String TABLE_NAME2 = "day2";
    public static final String COL_1 = "kl";
    public static final String COL_2 = "std";
    public static final String COL_3 = "fach";
    public static final String COL_4 = "raum";
    public static final String COL_5 = "vlehrer";
    public static final String COL_6 = "vfach";
    public static final String COL_7 = "vraum";
    public static final String COL_8 = "info";

    /**
     * creates a database
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_1 + " TEXT,"
                + COL_2 + " TEXT,"
                + COL_3 + " TEXT,"
                + COL_4 + " TEXT,"
                + COL_5 + " TEXT,"
                + COL_6 + " TEXT,"
                + COL_7 + " TEXT,"
                + COL_8 + " TEXT)");

        db.execSQL("create table " + TABLE_NAME2 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_1 + " TEXT,"
                + COL_2 + " TEXT,"
                + COL_3 + " TEXT,"
                + COL_4 + " TEXT,"
                + COL_5 + " TEXT,"
                + COL_6 + " TEXT,"
                + COL_7 + " TEXT,"
                + COL_8 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    public boolean insertData(int tablenumber, String kl, String std, String fach, String raum, String vlehrer, String vfach, String vraum, String info) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, kl);
        contentValues.put(COL_2, std);
        contentValues.put(COL_3, fach);
        contentValues.put(COL_4, raum);
        contentValues.put(COL_5, vlehrer);
        contentValues.put(COL_6, vfach);
        contentValues.put(COL_7, vraum);
        contentValues.put(COL_8, info);

        long result;

        if (tablenumber == 1)
            result = db.insert(TABLE_NAME, null, contentValues);
        else //if (tablenumber == 2)
            result = db.insert(TABLE_NAME2, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }
}
