package com.example.bancogremio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtratoContaActivity extends AppCompatActivity {

    private ListView listView;
    private RepositorioConta repositorioConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato_conta);

        // Inicializa o repositório
        repositorioConta = new RepositorioConta(this);

        // Liga o ListView à variável
        listView = findViewById(R.id.ListViewExtrato);

        // Obtém as transações da conta (ID fixo = 1)
        List<Transacao> transacoes = repositorioConta.getTransacoes(1);

        // Cria o ArrayAdapter personalizado
        ArrayAdapter<Transacao> adapter = new ArrayAdapter<Transacao>(this, android.R.layout.simple_list_item_2, android.R.id.text1, transacoes) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
                }

                // Obtém a transação
                Transacao transacao = getItem(position);

                // Verifica se a transação não é nula
                if (transacao != null) {
                    // Log para depuração
                    Log.i("Extrato", "Exibindo transação: " + transacao.tipo + " - R$ " + transacao.valor);

                    // Configura o texto da ListView
                    TextView tipoTextView = convertView.findViewById(android.R.id.text1);
                    tipoTextView.setText("Tipo: " + transacao.tipo);

                    TextView valorTextView = convertView.findViewById(android.R.id.text2);
                    valorTextView.setText(String.format("Valor: R$ %.2f", transacao.valor));
                }

                return convertView;
            }
        };

        // Define o Adapter para o ListView
        listView.setAdapter(adapter);
    }

}





