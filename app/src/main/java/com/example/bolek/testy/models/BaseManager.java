package com.example.bolek.testy.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class BaseManager extends SQLiteOpenHelper{
    private Context c;
    public BaseManager(Context c){
        super(c,"kontakty.db",null,1);
        this.c=c;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table testowa" +
                "(id integer primary key autoincrement," +
                "tekst char(50)," +
                "age integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}

    public void addOne(String tekst, int age){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("tekst",tekst);
        wartosci.put("age",age);
        try{
            db.insertOrThrow("testowa",null,wartosci);
        }catch(Exception e){
            Toast.makeText(c,"błąd",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public Cursor getAll(){
        String[] kolumny = {"id","tekst","age"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("testowa",kolumny,null,null,null,null,null);
        return c;
    }

    public void deleteOne(int id){
        SQLiteDatabase db = getWritableDatabase();
        String[] arg = {""+id};
        try{
            db.delete("testowa","id=?",arg);
        }catch(Exception e){
            Toast.makeText(c,"błąd",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void updateOne(int id, String tekst, int age){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("id",id);
        wartosci.put("tekst",tekst);
        wartosci.put("age",age);
        String arg[]={id+""};
        try{
            db.update("testowa",wartosci,"id=?",arg);
        }catch(Exception e){
            Toast.makeText(c,"błąd",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
