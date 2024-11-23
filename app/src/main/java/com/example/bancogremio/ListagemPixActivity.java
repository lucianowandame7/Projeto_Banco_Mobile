package com.example.bancogremio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ListagemPixActivity extends AppCompatActivity {
    ArrayList<ChavePix> listaChavePix;
    RepositorioChavePix repositorioChavePix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_pix);

        listaChavePix = (ArrayList<ChavePix>) getIntent()
                .getSerializableExtra("lista_chavePix");
        Log.i("chavePix", "Carregado Listagem chavePix com sucesso");

        repositorioChavePix = new RepositorioChavePix(this);

        ListView listView = findViewById(R.id.listViewChavePix);
        List<ChavePix> listaDB = repositorioChavePix.listarChavePix();
        String[] dados = new String[listaDB.size()];

        // Passando a lista para o vetor de strings
        for (int i = 0; i < listaDB.size(); i++) {
            ChavePix chavePix = listaDB.get(i);
            dados[i] = String.valueOf(chavePix.numeroChave); // A chave Pix é tratada como String diretamente
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, dados);

        listView.setAdapter(adapter);
    }
}
