package com.example.bancogremio;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RepositorioChavePix extends SQLiteOpenHelper {

    public RepositorioChavePix(@Nullable Context context) {
        super(context, "chavePix", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table chavePix " +
                "(id integer not null primary key, " +
                "numeroChave text)"; // Alterando para "text" para armazenar números de telefone ou CPF
        sqLiteDatabase.execSQL(sql);
        Log.i("chavePix", "Criado com sucesso a tabela ChavePix");
    }

    public void adionarChavePix(ChavePix chavePix) {
        String sql = "insert into chavePix values(null, '" + chavePix.numeroChave + "')";
        Log.i("chavePix", "SQL insert chavePix: " + sql);
        super.getWritableDatabase().execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public List<ChavePix> listarChavePix() {
        ArrayList<ChavePix> lista = new ArrayList<>();
        String sql = "select * from chavePix";
        Cursor cursor = getWritableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            ChavePix chavePix = new ChavePix();
            chavePix.id = cursor.getInt(0); // coluna 0
            chavePix.numeroChave = Double.valueOf(cursor.getString(1)); // Alterado para String para número da chave
            lista.add(chavePix);
            cursor.moveToNext();
        }
        cursor.close();
        return lista;
    }

    // Método para buscar chave Pix pelo número da chave
    public ChavePix buscarChavePixPorNumero(String numeroChave) {
        String sql = "select * from chavePix where numeroChave = ?";
        Cursor cursor = getWritableDatabase().rawQuery(sql, new String[]{numeroChave});
        cursor.moveToFirst();
        ChavePix chavePix = null;
        if (cursor.getCount() > 0) {
            chavePix = new ChavePix();
            chavePix.id = cursor.getInt(0); // coluna 0
            chavePix.numeroChave = Double.valueOf(cursor.getString(1)); // coluna 1
        }
        cursor.close();
        return chavePix;
    }

    // Método para remover a chave Pix pelo número da chave
    public void removerChavePixPorNumero(String numeroChave) {
        String sql = "delete from chavePix where numeroChave = ?";
        getWritableDatabase().execSQL(sql, new String[]{numeroChave});
        Log.i("chavePix", "SQL delete chavePix: " + sql);
    }

    // Método para verificar se a chave Pix está cadastrada
    public boolean isChaveCadastrada(String numeroChave) {
        String sql = "select count(*) from chavePix where numeroChave = ?";
        Cursor cursor = getWritableDatabase().rawQuery(sql, new String[]{numeroChave});
        cursor.moveToFirst();
        int count = cursor.getInt(0); // Obtém o resultado da contagem
        cursor.close();
        return count > 0; // Retorna true se a chave está cadastrada, senão false
    }
}


