package com.example.bancogremio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PixActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pix);
    }

    public void EnviarPix(View view) {
        Intent intent = new Intent(PixActivity.this, EnviarPixActivity.class);
        startActivity(intent);
    }

    public void CadastrarChavePix(View view) {
        Intent intent = new Intent(PixActivity.this, CadastroChavePixActivity.class);
        startActivity(intent);
    }


    public void ListagemPix(View view) {
        Intent intent = new Intent(PixActivity.this, ListagemPixActivity.class);
        startActivity(intent);
    }

    public void RemoverChavePix(View view){
        Intent intent = new Intent(PixActivity.this, RemoverChavePixActivity.class);
        startActivity(intent);
    }
}