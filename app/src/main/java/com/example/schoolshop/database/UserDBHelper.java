package com.example.schoolshop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDBHelper extends SQLiteOpenHelper {
    CommodityDBHelper dbHelper;
    public static int uid;
    private static final String create_sql="create table User(" +
            "uid integer primary key," +
            "username varchar(12) not null," +
            "userpwd varchar(20) not null)";

    public UserDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_sql);
        sqLiteDatabase.execSQL(dbHelper.create_Commodity);  //只有第一次创建数据库的时候会执行onCreate方法
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //注册成功进行数据的插入
    public void insertUser(String username,String pwd){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("username",username);
        values.put("userpwd",pwd);
        db.insert("User",null,values);
        db.close();
    }
    //用于判断该用户名是否以及被使用
    public boolean findThisUser(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.query("User",null,"username=?",new String[]{username},null,null,null);
        if (cursor.getCount()>0){
            return true;
        }
        db.close();
        return false;
    }
    //用于判断登录的成功与否
    public boolean findUser(String username,String pwd){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.query("User",null,"username=? and userpwd=?",new String[]{username,pwd},null,null,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            uid=cursor.getInt(0);
            return true;
        }
        db.close();
        return false;
    }
}
