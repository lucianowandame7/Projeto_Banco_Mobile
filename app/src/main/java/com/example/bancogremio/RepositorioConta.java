package com.example.bancogremio;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RepositorioConta extends SQLiteOpenHelper {

    public RepositorioConta(@Nullable Context context) {
        super(context, "Conta", null, 2);  // Incrementa a versão do banco
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Criação da tabela 'conta'
        String sqlConta = "CREATE TABLE conta (" +
                "id INTEGER NOT NULL PRIMARY KEY, " +
                "dinheiro DOUBLE)";
        sqLiteDatabase.execSQL(sqlConta);
        Log.i("conta", "Tabela conta criada com sucesso");

        // Criação da tabela 'transacoes'
        String sqlTransacoes = "CREATE TABLE transacoes (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "conta_id INTEGER, " +
                "tipo TEXT, " +
                "valor DOUBLE, " +
                "FOREIGN KEY(conta_id) REFERENCES conta(id))";
        sqLiteDatabase.execSQL(sqlTransacoes);
        Log.i("transacoes", "Tabela transacoes criada com sucesso");

        // Inserção inicial de dados na tabela 'conta'
        ContentValues values = new ContentValues();
        values.put("id", 1);  // ID fixo para uma única conta
        values.put("dinheiro", 0.0);
        sqLiteDatabase.insert("conta", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualização da versão do banco e recriação das tabelas
        db.execSQL("DROP TABLE IF EXISTS conta");
        db.execSQL("DROP TABLE IF EXISTS transacoes");
        onCreate(db);
    }

    // Método para adicionar dinheiro à conta
    public void adicionarDinheiro(int id, double valor) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("dinheiro", valor);
        sqLiteDatabase.update("conta", values, "id = ?", new String[]{String.valueOf(id)});
    }

    // Método para obter o saldo da conta
    public double getSaldo(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("conta", new String[]{"dinheiro"},
                "id = ?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            double saldo = cursor.getDouble(cursor.getColumnIndexOrThrow("dinheiro"));
            cursor.close();
            return saldo;
        }
        return 0.0;
    }

    // Método para atualizar o saldo
    public void atualizarSaldo(int id, double novoSaldo) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("dinheiro", novoSaldo);
        int rowsAffected = sqLiteDatabase.update("conta", values, "id = ?", new String[]{String.valueOf(id)});
        Log.i("Saldo", "Saldo atualizado para ID: " + id + ", Novos Dados: " + novoSaldo + ", Linhas afetadas: " + rowsAffected);
    }

    // Método para adicionar transação
    public void adicionarTransacao(int contaId, String tipo, double valor) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("conta_id", contaId);
        values.put("tipo", tipo);
        values.put("valor", valor);
        long result = sqLiteDatabase.insert("transacoes", null, values);
        Log.i("Transacao", "Transacao adicionada com ID: " + result);
    }

    // Método para obter as transações da conta
    public List<Transacao> getTransacoes(int contaId) {
        List<Transacao> transacoes = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Consulta para buscar todas as transações da conta
        Cursor cursor = sqLiteDatabase.query("transacoes", new String[]{"tipo", "valor"},
                "conta_id = ?", new String[]{String.valueOf(contaId)},
                null, null, "id DESC");  // Ordena por ID (mais recentes primeiro)

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String tipo = cursor.getString(cursor.getColumnIndex("tipo"));
                @SuppressLint("Range") double valor = cursor.getDouble(cursor.getColumnIndex("valor"));

                // Cria uma nova transação e adiciona à lista
                transacoes.add(new Transacao(tipo, valor));

                // Log para verificar se os dados estão corretos
                Log.i("RepositorioConta", "Tipo: " + tipo + " - Valor: " + valor);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return transacoes;
    }

}



