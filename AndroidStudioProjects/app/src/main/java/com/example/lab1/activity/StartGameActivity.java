package com.example.lab1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab1.MainActivity;
import com.example.lab1.R;

public class StartGameActivity extends AppCompatActivity{
    private final int TOAST_DURATION = 1000;
    public static String player_name = null;
    private final String PLACE_HOLDER_PLAYER_NAME = "Имя";

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.start_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView t_view = findViewById(R.id.textPlayerName);
        t_view.setOnClickListener(view -> {
            t_view.setText("");
        });
    }

    public void startGame (View view ){
        try{
            TextView t_view = findViewById(R.id.textPlayerName);
            player_name = (!t_view.getText().equals(""))?t_view.getText().toString():null;
            if(player_name != null){
                Intent intent = new Intent(StartGameActivity.this, MainActivity.class);
                startActivityForResult(intent, 0);
            }else{
                Toast.makeText(getApplicationContext(), "Имя не задано", Toast.LENGTH_LONG).show();
            }
        }catch(Exception ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
