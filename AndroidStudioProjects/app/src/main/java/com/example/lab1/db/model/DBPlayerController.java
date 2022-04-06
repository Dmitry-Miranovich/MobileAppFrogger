package com.example.lab1.db.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lab1.db.view.Player;

public class DBPlayerController extends SQLiteOpenHelper {

    private boolean isActive = false;
    private Cursor customCursor = null;
    private static final String DATABASE_NAME = "frogGame.db";
    private static final int SCHEMA = 1;
    private final String TABLE_NAME = "users";

    private final String COLUMN_ID = "p_id";
    private final String COLUMN_NICKNAME = "p_nickname";
    private final String COLUMN_SCORE = "p_score";
    private final String COLUMN_BEST_SCORE = "p_best_score";
    private final String COLUMN_TIME = "p_time";

    public DBPlayerController(Context context){
        super(context, DATABASE_NAME, null, SCHEMA);
        isActive = true;
    }

    public void addData(SQLiteDatabase db ,Player player){
        if(isActive){
            try{
                String nickname = player.getNickname();
                String score = player.getScore();
                String time = player.getTime();
                String best_score = player.getMax_score();
                db.execSQL(" INSERT INTO " + TABLE_NAME + " ("+COLUMN_NICKNAME+", "+COLUMN_SCORE+", "+COLUMN_TIME+", "+COLUMN_BEST_SCORE+") " +
                        "VALUES ('"+nickname+"', '"+score+"', '"+time+"', '"+best_score+"');");
                System.out.println("Success!");
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public void updateData(SQLiteDatabase db , Player player){
        if(isActive){
            String nickname = player.getNickname();
            String score = player.getScore();
            String time = player.getTime();
            String best_score = player.getMax_score();
            try{
                db.execSQL("UPDATE "+TABLE_NAME+" SET "+COLUMN_NICKNAME+" = '"+nickname+"', "+COLUMN_SCORE+" = '"+score+"'" +
                        ", "+COLUMN_TIME+" = '"+time+"', "+COLUMN_BEST_SCORE+" = '"+best_score+"' WHERE "+COLUMN_NICKNAME+" = " +
                        "'"+nickname+"'");
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public Player GetDataByID(SQLiteDatabase db,int id){
        String nickname = "";
        String score = "";
        String time = "";
        String best_score = "";
        try{
            customCursor = db.rawQuery("select * from " + TABLE_NAME + " where "+ COLUMN_ID +" = "+ id + "", null);
            while(customCursor.moveToNext()){
                nickname = customCursor.getString(1);
                score = customCursor.getString(2);
                time = customCursor.getString(3);
                best_score = customCursor.getString(4);
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return new Player(nickname, score, time, best_score);
    }
    public Player GetDataByName(SQLiteDatabase db, String name){
        String nickname = "";
        String score = "";
        String time = "";
        String best_score = "";
        try{
            customCursor = db.rawQuery("select * from " + TABLE_NAME + " where "+ COLUMN_NICKNAME +" = '"+ name + "'", null);
            while(customCursor.moveToNext()){
                nickname = customCursor.getString(1);
                score = customCursor.getString(2);
                time = customCursor.getString(3);
                best_score = customCursor.getString(4);
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return new Player(nickname, score, time, best_score);
    }
    public int delete(SQLiteDatabase db, int id){
        int res = -1;
        try{
            db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+"="+id+"");
            res = 1;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return res;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NICKNAME + " TEXT," + COLUMN_SCORE + " TEXT," +
                " "+COLUMN_TIME+" TEXT, "+COLUMN_BEST_SCORE+",TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
