package com.example.lab1.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1.MainActivity;
import com.example.lab1.R;
import com.example.lab1.db.model.DBPlayerController;
import com.example.lab1.db.view.Player;

public class EndGameActivity extends AppCompatActivity {

    private DBPlayerController db_controller;
    private SQLiteDatabase db;
    public static Player cur_player;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.end_game_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        db_controller = new DBPlayerController(getApplicationContext());
        db = db_controller.getWritableDatabase();
        addNewRows();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "I`m " + cur_player.getNickname() + ", and i got " + cur_player.getScore()
                + " points in Frogger, let`s see, how much can you get!)";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
        return super.onOptionsItemSelected(item);
    }

    private void addNewRows(){
        int i = 1;
        Player player;
        do{
            TableRow row = new TableRow(getApplicationContext());
            player = db_controller.GetDataByID(db, i);
            if(!player.getNickname().equals("")){
                addDataToRow(row, player);
            }
            row.setBackgroundColor(getColor(R.color.rowsColor));
            TableLayout layout = findViewById(R.id.tableLayout1);
            layout.addView(row);
            i++;
        }while(!player.getNickname().equals(""));
    }
    private void addTextView(TableRow row, String name){
        TextView view = new TextView(getApplicationContext());
        view.setText(name);
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.width = 0;
        params.height = TableRow.LayoutParams.WRAP_CONTENT;
        params.weight = 4;
        view.setLayoutParams(params);
        view.setTextSize(14f);
        view.setGravity(Gravity.CENTER_HORIZONTAL);
        view.setPadding(0,10,0,10);
        view.setTextColor(getResources().getColor(R.color.black));
        row.addView(view);
    }
    private void addDataToRow(TableRow row, Player player){
        addTextView(row, player.getNickname());
        addTextView(row, player.getScore());
        addTextView(row, player.getTime());
        addTextView(row, player.getMax_score());
    }

    public void restartGame(View view){
        Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
        startActivityForResult(intent, 0);
    }
    public void createNewGame(View view){
        Intent intent = new Intent(EndGameActivity.this, StartGameActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        db_controller.close();
    }

}
