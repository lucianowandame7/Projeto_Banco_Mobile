package com.example.bancogremio;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RemoverChavePixActivity extends AppCompatActivity {
    ArrayList<ChavePix> listachavepix;
    RepositorioChavePix repositorioChavePix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remover_chave_pix);
        setTitle("Remover Chave Pix");
        listachavepix = (ArrayList<ChavePix>) getIntent().getSerializableExtra("lista_chavePix");
        repositorioChavePix = new RepositorioChavePix(this);
        Log.i("chavePix", "Carregado Remoção da Chave Pix com sucesso");
    }

    public void OnClickRemoverChavePix(View view) {
        EditText editText = findViewById(R.id.editTextRemoverChavePix);
        String numeroChave = editText.getText().toString().trim(); // Número da chave Pix (telefone ou CPF)

        if (numeroChave.isEmpty()) {
            Toast.makeText(this, "Por favor, insira o número da chave Pix", Toast.LENGTH_LONG).show();
            return;
        }

        // Buscar a chave Pix usando o número fornecido
        ChavePix chavePix = repositorioChavePix.buscarChavePixPorNumero(numeroChave);

        if (chavePix == null) {
            Toast.makeText(this, "Chave Pix não encontrada", Toast.LENGTH_LONG).show();
            return;
        }

        // Remover a chave Pix
        repositorioChavePix.removerChavePixPorNumero(numeroChave);
        Toast.makeText(this, "Remoção da chave Pix realizada com sucesso.", Toast.LENGTH_LONG).show();
    }

}
