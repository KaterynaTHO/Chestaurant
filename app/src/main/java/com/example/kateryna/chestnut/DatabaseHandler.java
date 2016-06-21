package com.example.kateryna.chestnut;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kateryna on 6/20/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "restaurantsDB",
            TABLE_RESTS = "rests",
            KEY_ID = "id",
            KEY_NAME = "name",
            KEY_PHONE = "phone",
            KEY_STARS = "stars",
            KEY_ADDRESS = "address",
            KEY_IMAGEURI = "imageUri";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_RESTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_PHONE + " TEXT," + KEY_STARS + " TEXT," + KEY_ADDRESS + " TEXT," + KEY_IMAGEURI + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
db.execSQL("DROP TABLE IF EXISTS "+ TABLE_RESTS);
        onCreate(db);

    }

    public void createRest (RestList rest){
SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, rest.getName());
        values.put(KEY_PHONE, rest.getPhone());
        values.put(KEY_STARS, rest.getStars());
        values.put(KEY_ADDRESS, rest.getAddress());
        values.put(KEY_IMAGEURI, rest.get_imageUri().toString());

        db.insert(TABLE_RESTS, null, values);
        db.close();
    }

    public RestList getRestList(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_RESTS, new String[] { KEY_ID, KEY_NAME, KEY_PHONE, KEY_STARS, KEY_ADDRESS, KEY_IMAGEURI},
                KEY_ID + "=?", new String[] { String.valueOf(id)}, null, null, null, null

        );
        if (cursor != null) cursor.moveToFirst(); //This method will return false if the cursor is empty
        // if it isn't null, the currsor will be moved to the 1st item
        RestList rest = new RestList(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), Uri.parse(cursor.getString(5)));

        cursor.close();
        db.close();
return  rest;
    }

    public void deleteRest(RestList rest){
        SQLiteDatabase db =getWritableDatabase();
        db.delete(TABLE_RESTS, KEY_ID + "=?", new String[] {String.valueOf(rest.getId())});
        db.close();
    }

    public int getRestCount (){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RESTS, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int updateRest(RestList rest){
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, rest.getName());
        values.put(KEY_PHONE, rest.getPhone());
        values.put(KEY_STARS, rest.getStars());
        values.put(KEY_ADDRESS, rest.getAddress());
        values.put(KEY_IMAGEURI, rest.get_imageUri().toString());
        db.insert(TABLE_RESTS, null, values);

        return db.update(TABLE_RESTS, values, KEY_ID + "=?", new String[] { String.valueOf(rest.getId()) });

    }

    public List<RestList> getAllRests() {
        List<RestList> rests = new ArrayList<RestList>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RESTS, null);

        if (cursor.moveToFirst()) {
            do {
                rests.add(new RestList(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4), Uri.parse(cursor.getString(5))));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return rests;
    }
}


