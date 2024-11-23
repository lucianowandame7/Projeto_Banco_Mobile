package com.example.bancogremio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }

    public void conta(View view) {
        Intent intent = new Intent(MenuPrincipalActivity.this, ContaActivity.class);
        startActivity(intent);
    }

    public void pix(View view) {
        Intent intent = new Intent(MenuPrincipalActivity.this, PixActivity.class);
        startActivity(intent);
    }
}