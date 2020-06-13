package com.example.dps924_a4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context){
        super(context,"teamDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE teams(id INTEGER PRIMARY KEY, name TEXT, city TEXT, sport TEXT, mvp TEXT, image BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS teams");
        onCreate(db);
    }

    public void insertTeam(String pName, String pCity, String pSport, String pMVP, byte[] pImage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", pName);
        values.put("city", pCity);
        values.put("sport", pSport);
        values.put("mvp", pMVP);
        values.put("image", pImage);
        db.insert("teams", null, values);
        db.close();
    }

    public int deleteTeam(Integer pID){
        SQLiteDatabase db = this.getWritableDatabase();
        int temp = db.delete("teams", "id=?", new String[]{Integer.toString(pID)});
        db.close();
        return temp;
    }

    public int updateTeam(Integer pID, String pName, String pCity, String pSport, String pMVP, byte[] pImage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", pName);
        values.put("city", pCity);
        values.put("sport", pSport);
        values.put("mvp", pMVP);
        values.put("image", pImage);
        int temp = db.update("teams", values, "id=?", new String[]{Integer.toString(pID)});
        db.close();
        return temp;
    }

    public List<Integer> getAllIDs(){
        List<Integer> listItems = new ArrayList<>();
        String selectQuery = "SELECT * FROM teams";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do{
                listItems.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public List<String> getAllNames(){
        List<String> listItems = new ArrayList<>();
        String selectQuery = "SELECT * FROM teams";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do{
                listItems.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public List<String> getAllCities(){
        List<String> listItems = new ArrayList<>();
        String selectQuery = "SELECT * FROM teams";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do{
                listItems.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public List<String> getAllSports(){
        List<String> listItems = new ArrayList<>();
        String selectQuery = "SELECT * FROM teams";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do{
                listItems.add(cursor.getString(3));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public List<String> getAllMVPs(){
        List<String> listItems = new ArrayList<>();
        String selectQuery = "SELECT * FROM teams";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do{
                listItems.add(cursor.getString(4));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listItems;
    }

    public List<byte[]> getAllImages(){
        List<byte[]> listItems = new ArrayList<>();
        String selectQuery = "SELECT * FROM teams";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do{
                listItems.add(cursor.getBlob(5));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listItems;
    }
}
