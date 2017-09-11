package com.example.android.newapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDbHelper extends SQLiteOpenHelper {
    public StudentDbHelper(Context context) {
        super(context, "stud.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user (sname text , sgender text, smobile_no text, spass text, simage blob)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        onCreate(db);


    }

    // public  boolean CheckIsDataAlreadyInDBorNot(String TableName,
    //                                                String dbfield, String fieldValue) {
    //   SQLiteDatabase sqldb = this.getReadableDatabase();
    //    String Query = "Select * from " + TableName + " where " + dbfield + "=?" ;
    //   Cursor cursor = sqldb.rawQuery(Query, new String[] {fieldValue});
    //    boolean exists = cursor.moveToFirst();
    //   cursor.close();
    //   return exists;
    // }

    public void addEntry(  byte[] image) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();

        cv.put("simage",   image);
        database.insert( "user", null, cv );

    }
    public int updateContact(byte[] image, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("simage", image);



        // updating row
        return db.update("user", values, "sname" + " = ?",
                new String[] { name });
    }

}
