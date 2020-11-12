package com.example.ujianpraktek.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "digitalent.db";
    public static final String TABLE_SQLite = "sqlite";
    public static final String COLOUMN_ID = "id";
    public static final String COLOUMN_NAME = "name";
    public static final String COLOUMN_ADDRESS = "address";

    public DbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE =" CREATE TABLE " + TABLE_SQLite + " ("+
                COLOUMN_ID + " INTEGER PRIMARY KEY autoincrement, "+
                COLOUMN_NAME + " TEXT NOT NULL, "+
                COLOUMN_ADDRESS + " TEXT NOT NULL )";
        db.execSQL(SQL_CREATE_MOVIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_SQLite);
            onCreate(db);
    }

    public ArrayList<HashMap<String,String>> getAlldata(){
        ArrayList<HashMap<String,String>> worldList;
        worldList = new ArrayList<HashMap<String,String>>();
        String selectQuery = "SELECT * FROM " + TABLE_SQLite;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> map = new HashMap<String,String>();
                map.put(COLOUMN_ID,cursor.getString(0));
                map.put(COLOUMN_NAME,cursor.getString(1));
                map.put(COLOUMN_ADDRESS,cursor.getString(2));
                worldList.add(map);
            }while (cursor.moveToNext());
        }
        Log.e("Select SQLite : ", " " + worldList);
        database.close();
        return  worldList;
    }
    public void insert(String name,String address){
        SQLiteDatabase database = this.getWritableDatabase();
        String queryValue = "INSERT INTO " + TABLE_SQLite + "("+COLOUMN_NAME+","+COLOUMN_ADDRESS+")" +
                "VALUES('"+name +"','"+address+"')";
        Log.e("inset sqlite ", "insert: "+ queryValue );
        database.execSQL(queryValue);
        database.close();
    }
    public void update(int id,String name,String address){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_SQLite + " SET " +
                COLOUMN_NAME + "='"+name+"',"+
                COLOUMN_ADDRESS + "='"+address+"' WHERE " + COLOUMN_ID +"='"+id+"'";
        Log.e("query", "update: "  + query );
        database.execSQL(query);
        database.close();
    }
    public void delete(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_SQLite + " WHERE " + COLOUMN_ID+" = '"+ id + "'" ;
        Log.e("quey ", "delete: " + query );
        database.execSQL(query);
        database.close();
    }
}
