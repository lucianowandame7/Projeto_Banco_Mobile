package com.example.bancogremio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroChavePixActivity extends AppCompatActivity {
    RepositorioChavePix repositorioChavePix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_chave_pix);
    }

    public void CadastrarChavePix(View view) {
        EditText caixaCadastroChavePix = findViewById(R.id.editTextCadastroChavePix);
        String chavePix = caixaCadastroChavePix.getText().toString();

        if (chavePix.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
            return;
        }



        // Validação
        if (validarCPF(chavePix)==false) {
            Toast.makeText(this, "CPF inválido", Toast.LENGTH_SHORT).show();
            return;
        } else if (validarTelefone(chavePix) == false) {
            Toast.makeText(this, "Telefone inválido", Toast.LENGTH_SHORT).show();
            return;
        }


        repositorioChavePix = new RepositorioChavePix(this);

        ChavePix chavepix= new ChavePix();
        chavepix.numeroChave = Double.parseDouble(chavePix);
        repositorioChavePix.adionarChavePix(chavepix);

        Toast.makeText(this,"Cadastro realizado" +
                        " com sucesso.",
                Toast.LENGTH_LONG).show();

    }

    // Método para validar CPF
    private boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11) return false;

        int soma = 0;
        int resto;

        for (int i = 1; i <= 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i - 1)) * (11 - i);
        }

        resto = (soma * 10) % 11;
        if (resto == 10 || resto == 11) resto = 0;
        if (resto != Character.getNumericValue(cpf.charAt(9))) return false;

        soma = 0;
        for (int i = 1; i <= 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i - 1)) * (12 - i);
        }

        resto = (soma * 10) % 11;
        if (resto == 10 || resto == 11) resto = 0;
        return resto == Character.getNumericValue(cpf.charAt(10));
    }

    // Método para validar telefone
    private boolean validarTelefone(String telefone) {
        telefone = telefone.replaceAll("[^0-9]", "");
        return telefone.length() == 10 || telefone.length() == 11;
    }

    // Método para aplicar máscara ao CPF
    private String aplicarMascaraCPF(String cpf) {
        return cpf.replaceAll("(\\d{3})(\\d)", "$1.$2")
                .replaceAll("(\\d{3})(\\d)", "$1.$2")
                .replaceAll("(\\d{3})(\\d)", "$1-$2");
    }

    // Método para aplicar máscara ao telefone
    private String aplicarMascaraTelefone(String telefone) {
        return telefone.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3")
                .replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
    }
}