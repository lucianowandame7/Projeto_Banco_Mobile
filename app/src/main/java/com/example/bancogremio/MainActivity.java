package com.example.bancogremio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void EntrarMenuPrincipal(View view) {
        Intent intent = new Intent(MainActivity.this, MenuPrincipalActivity.class);
        startActivity(intent);
    }
}