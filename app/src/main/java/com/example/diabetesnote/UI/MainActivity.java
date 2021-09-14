package com.example.diabetesnote.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.diabetesnote.R;

public class MainActivity extends AppCompatActivity {

    private Button buttonAdd;
    private Button buttonShow;
    private Button buttonReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        setButtons();
    }

    void Init(){
        buttonAdd = findViewById(R.id.main_add);
        buttonShow = findViewById(R.id.main_show);
        buttonReport = findViewById(R.id.main_report);
    }

    private void setButtons() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , Add.class);
                startActivity(intent);
            }
        });
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , Show.class);
                startActivity(intent);
            }
        });
        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, R.string.futureVersion, Toast.LENGTH_SHORT).show();
            }
        });
    }
}