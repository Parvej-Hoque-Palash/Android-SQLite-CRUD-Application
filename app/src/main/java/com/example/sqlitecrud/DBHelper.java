package com.example.sqlitecrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "MyUserDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create Table UserRecord (NAME Text, CONTACT Text, DOB Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists UserRecord");
    }

    public Boolean insertUserData(String name,String contact,String dob){
        //to get database connection
        SQLiteDatabase db = this.getWritableDatabase();
        //to write content in database
        ContentValues contentValue = new ContentValues();
        //assign values to content variable
        contentValue.put("NAME", name);
        contentValue.put("CONTACT", contact);
        contentValue.put("DOB", dob);
        //execute the insert operation
        long result = db.insert("UserRecord", null, contentValue);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean updateUserData(String name,String contact,String dob){

        //to get database connection
        SQLiteDatabase db = this.getWritableDatabase();

        //to write content in database
        ContentValues contentValue = new ContentValues();

        //assign values to content variable
        //contentValue.put("NAME", name);
        contentValue.put("CONTACT", contact);
        contentValue.put("DOB", dob);

        //find current userRecord
        Cursor currentRecord = db.rawQuery("select * from UserRecord where NAME=?", new String[]{name});

        if(currentRecord.getCount()>0){
            int result = db.update("UserRecord", contentValue, "NAME=?", new String[]{name});
            if(result == -1){
                return false;
            }else{
                return true;
            }
        }
        else{
            return false;
        }
    }//end of update method

    public Boolean deleteUserData(String name){

        //to get database connection
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor findRecord = db.rawQuery("select * from UserRecord where NAME=?", new String[]{name});

        if(findRecord.getCount() > 0){
            int result = db.delete("UserRecord", "NAME=?", new String[]{name});
            if(result == -1){
                return false;
            }else{
                return true;
            }
        }
        else{
            return false;
        }
    }//end of delete method

    //view all user record
    public Cursor viewUserData(){

        //to get database connection
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor findAllRecords = db.rawQuery("select * from UserRecord", null);
        return findAllRecords;

    }//end of view method

}
