package com.example.bancogremio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtratoContaActivity extends AppCompatActivity {
    ListView listView;
    RepositorioConta repositorioConta;
    List<Map<String, String>> extrato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato_conta);

        // Inicializando o Repositório de Conta
        repositorioConta = new RepositorioConta(this);

        // Pegando o extrato da conta (usando id 1 como exemplo)
        int contaId = 1; // ID fixo para exemplo
        extrato = getExtrato(contaId);

        // Configurando a ListView
        listView = findViewById(R.id.ListViewExtrato);

        // Criando o SimpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                extrato,
                android.R.layout.simple_list_item_2, // Layout simples de 2 linhas
                new String[] {"acao", "valor"}, // Mapeia as ações e os valores
                new int[] {android.R.id.text1, android.R.id.text2} // Exibe a ação e o valor
        );

        // Configurando o adapter na ListView
        listView.setAdapter(adapter);
    }

    // Método para recuperar o extrato da conta (transações realizadas)
    public List<Map<String, String>> getExtrato(int contaId) {
        List<Map<String, String>> extrato = new ArrayList<>();

        // Acesso direto ao banco de dados para pegar transações
        SQLiteDatabase db = repositorioConta.getReadableDatabase();
        Cursor cursor = db.query("transacoes", new String[]{"acao", "valor"},
                "conta_id = ?", new String[]{String.valueOf(contaId)},
                null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String acao = cursor.getString(cursor.getColumnIndex("acao"));
                double valor = cursor.getDouble(cursor.getColumnIndex("valor"));

                // Criando um mapa para cada transação
                Map<String, String> item = new HashMap<>();
                item.put("acao", acao); // Ação realizada (depositar, remover, enviar pix)
                item.put("valor", String.format("R$ %.2f", valor)); // Valor da transação formatado

                extrato.add(item); // Adiciona ao extrato
            }
            cursor.close();
        }

        return extrato;
    }
}
