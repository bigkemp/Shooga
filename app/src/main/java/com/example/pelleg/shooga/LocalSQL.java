package com.example.pelleg.shooga;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalSQL extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ShoogaAlarms.db";
    public static final String TABLE_NAME = "alarms_table3";
    public static final String COL_1 = "CODE";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "TIME";
    public static final String COL_4 = "REPEAT";
    public static final String COL_5 = "STATUE";


    public LocalSQL(Context context) {
        super(context, DATABASE_NAME, null,1    );
    }





    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (CODE INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,TIME TEXT,REPEAT TEXT,STATUE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String code,String name,String time,Boolean repeat, Boolean statue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try { onCreate(db);}catch (Exception e){}

        contentValues.put(COL_1,code);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,time);
        contentValues.put(COL_4,String.valueOf(repeat));
        contentValues.put(COL_5,String.valueOf(statue));

        long result = db.insert(TABLE_NAME,null ,contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String code,String name,String time,Boolean repeat, Boolean statue) {
        SQLiteDatabase db = this.getWritableDatabase();
        try { onCreate(db);}catch (Exception e){}
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,code);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,time);
        contentValues.put(COL_4,String.valueOf(repeat));
        contentValues.put(COL_5,String.valueOf(statue));
        db.update(TABLE_NAME, contentValues, "CODE = ?",new String[] { code });
        return true;
    }

    public Integer deleteData (String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "CODE = ?",new String[] {code});
    }

    public Boolean deleteAllData () {
        SQLiteDatabase db = this.getWritableDatabase();
        String delete= "delete from ";
        db.execSQL(delete + TABLE_NAME);
        return true;
    }
}
