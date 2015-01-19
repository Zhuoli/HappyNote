package com.example.zhuoli.notebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zhuoli on 1/18/15.
 */
public class NotesDB extends SQLiteOpenHelper {

    public static final String TABLE_NAME="notes";
    public static final String CONTENT = "content";
    public static final String ID="_id";
    public static final String TIME = "time";
    public static final String PATH="path";
    public static final String VIDEO = "video";

    public NotesDB(Context context) {
        super(context, "notes", null, 1);
    }

    @Override
    // init database table
    public void onCreate(SQLiteDatabase db) {
        String cmd = "CREATE TABLE "+TABLE_NAME +"("
                +ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +CONTENT + " TEXT NOT NULL,"
                +PATH+" TEXT NOT NULL,"
                +VIDEO+" TEXT NOT NULL,"
                +TIME + " TEXT NOT NULL)";
        db.execSQL(cmd);
        System.out.println("DATABASE: "+ "cmd executed: "+cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
