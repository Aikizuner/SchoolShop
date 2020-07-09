package com.example.schoolshop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.schoolshop.bean.CommodityItems;

import java.util.ArrayList;
import java.util.List;

public class CommodityDBHelper extends SQLiteOpenHelper {

    public static final String create_Commodity="create table Commodity(" +
            "cid integer primary key," +
            "cname varchar(20) not null," +
            "cprice varchar(20) not null," +
            "ctype varchar(10) not null," +
            "cdescribe varchar(300) not null," +
            "cphone varchar(20) not null," +
            "cpicture BLOB not null," +
            "uid int not null)";
    public CommodityDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

//    //onCreate()只有在第一次创建时会执行
//    @Override
//    public void onOpen(SQLiteDatabase db) {
//        super.onOpen(db);
//        db.execSQL(create_sql);
//    }

    public boolean findCommodity(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.query("Commodity",null,null,null,null,null,null);
        if (cursor.getCount()>0){
            return true;
        }
        db.close();
        return false;
    }
    public void insertCommodity(String name, String price, String type, String describe, String phone, byte[] picture,int uid){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("cname",name);
        values.put("cprice",price);
        values.put("ctype",type);
        values.put("cdescribe",describe);
        values.put("cphone",phone);
        values.put("cpicture",picture);
        values.put("uid",uid);
        db.insert("Commodity",null,values);
        db.close();
    }
    //查找所有商品并把信息一次存入数组
    public List<CommodityItems> findAllCommodity(){
        List<CommodityItems> list=new ArrayList<CommodityItems>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.query("Commodity",null,null,null,null,null,"cid desc");
        for (int i = 0; i<5; i++){      //取前五条记录
            while(cursor.moveToNext()){
                CommodityItems ci=new CommodityItems();
                ci.setId(cursor.getInt(0));
                ci.setName(cursor.getString(1));
                ci.setPrice(cursor.getString(2));
                ci.setType(cursor.getString(3));
                ci.setDescribe(cursor.getString(4));
                ci.setPhone(cursor.getString(5));
                ci.setPicture(cursor.getBlob(6));
                ci.setUid(cursor.getInt(7));
                list.add(ci);
            }
        }
        cursor.close();
        return list;
    }
    //查找对应类型的商品
    public List<CommodityItems> findLearningCommodity(String type){
        List<CommodityItems> list=new ArrayList<CommodityItems>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.query("Commodity",null,"ctype=?",new String[]{type},null,null,null);
        while(cursor.moveToNext()){
            CommodityItems ci=new CommodityItems();
            ci.setId(cursor.getInt(0));
            ci.setName(cursor.getString(1));
            ci.setPrice(cursor.getString(2));
            ci.setType(cursor.getString(3));
            ci.setDescribe(cursor.getString(4));
            ci.setPhone(cursor.getString(5));
            ci.setPicture(cursor.getBlob(6));
            ci.setUid(cursor.getInt(7));
            list.add(ci);
        }
        cursor.close();
        return list;
    }
    //查找我发布的商品
    public List<CommodityItems> findMyCommodity(String uid){
        List<CommodityItems> list=new ArrayList<CommodityItems>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.query("Commodity",null,"uid=?",new String[]{uid},null,null,"cid desc");
        while(cursor.moveToNext()){
            CommodityItems ci=new CommodityItems();
            ci.setId(cursor.getInt(0));
            ci.setName(cursor.getString(1));
            ci.setPrice(cursor.getString(2));
            ci.setType(cursor.getString(3));
            ci.setDescribe(cursor.getString(4));
            ci.setPhone(cursor.getString(5));
            ci.setPicture(cursor.getBlob(6));
            ci.setUid(cursor.getInt(7));
            list.add(ci);
        }
        cursor.close();
        return list;
    }
    //删除商品
    public boolean deleteCommodity(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete("Commodity","cid=?",new String[]{id})>0;
    }
}
