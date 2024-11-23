package com.example.bancogremio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.bancogremio.RemoverContaActivity;

public class ContaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);
    }

    public void ExtratoDaConta(View view) {
        Intent intent = new Intent(ContaActivity.this, ExtratoContaActivity.class);
        startActivity(intent);
    }

    public void RemoverDaConta(View view) {
        Intent intent = new Intent(ContaActivity.this, com.example.bancogremio.RemoverContaActivity.class);
        startActivity(intent);
    }

    public void DepositarNaConta(View view) {
        Intent intent = new Intent(ContaActivity.this, DepositoContaActivity.class);
        startActivity(intent);
    }
}