package com.example.lab1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import com.example.lab1.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class DeveloperActivity extends AppCompatActivity {

    private static final String FILE_NAME = "info.txt";
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer_layout);
        readInfoFile();
    }

    public void showDeveloperInfo(View view){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
    public void readInfoFile(){
        String info_text = "Номер лабораторной работы: №1\n" +
                "Номер группы №951006\n" +
                "Разработчик: Миранович Дмитрий Владимирович";
        TextView view = findViewById(R.id.info_view);
        view.setText(info_text);
    }
}
