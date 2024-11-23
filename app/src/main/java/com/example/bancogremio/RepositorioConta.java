package com.example.bancogremio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class RepositorioConta extends SQLiteOpenHelper {

    public RepositorioConta(@Nullable Context context) {
        super(context, "Conta", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Criação da tabela
        String sql = "CREATE TABLE conta (" +
                "id INTEGER NOT NULL PRIMARY KEY, " +
                "dinheiro DOUBLE)";
        sqLiteDatabase.execSQL(sql);
        Log.i("conta", "Criado com sucesso a tabela conta");

        // Inserção inicial de dados
        ContentValues values = new ContentValues();
        values.put("id", 1);  // ID fixo para uma única conta
        values.put("dinheiro", 0.0);
        sqLiteDatabase.insert("conta", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualizações de esquema, caso necessário
    }

    // Método para adicionar dinheiro à conta
    public void adicionarDinheiro(int id, double valor) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("dinheiro", valor);
        sqLiteDatabase.update("conta", values, "id = ?", new String[]{String.valueOf(id)});  // Atualiza a conta com ID específico
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
        sqLiteDatabase.update("conta", values, "id = ?", new String[]{String.valueOf(id)});
    }

    // Método para encontrar a conta pelo CPF ou telefone (chave do receptor)
    public Conta getContaPorChave(String chave) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("conta", new String[]{"id", "dinheiro"},
                "id = ?", new String[]{chave},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Conta conta = new Conta();
            conta.id = cursor.getInt(cursor.getColumnIndex("id"));
            conta.dinheiro = cursor.getDouble(cursor.getColumnIndex("dinheiro"));
            cursor.close();
            return conta;
        }
        return null;  // Caso não encontre a conta
    }
}
