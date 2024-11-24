package com.example.bancogremio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EnviarPixActivity extends AppCompatActivity {
    RepositorioConta repositorioConta;  // Repositório que lida com as contas
    RepositorioChavePix repositorioChavePix;  // Repositório que lida com as chaves Pix
    EditText caixaSaldo;  // EditText que exibirá o saldo do remetente

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_pix);

        // Inicializa os repositórios
        repositorioConta = new RepositorioConta(this);
        repositorioChavePix = new RepositorioChavePix(this);

        // Inicializa a caixa de saldo
        caixaSaldo = findViewById(R.id.editTextSaldo);

        // Atualiza o saldo na tela ao carregar a atividade
        atualizarSaldoNaTela();
    }

    public void ConfirmarEnvioPix(View view) {
        EditText caixaEnviarPix = findViewById(R.id.editTextEnviarPix);
        EditText caixaChaveReceber = findViewById(R.id.editTextChaveReceber);

        String enviarPix = caixaEnviarPix.getText().toString();
        String chaveReceber = caixaChaveReceber.getText().toString();

        // Verifica se o usuário tem uma chave Pix cadastrada
        if (!usuarioTemChaveCadastrada()) {
            Toast.makeText(this, "Você precisa cadastrar uma chave Pix antes de enviar", Toast.LENGTH_LONG).show();
            return;
        }

        // Verifica se os campos estão preenchidos
        if (enviarPix.isEmpty() || chaveReceber.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
            return;
        }

        // Validação da chave (CPF ou telefone)
        if (!validarChaveReceber(chaveReceber)) {
            Toast.makeText(this, "Chave Pix inválida", Toast.LENGTH_LONG).show();
            return;
        }

        // Validação do valor de envio
        float valor;
        try {
            valor = Float.parseFloat(enviarPix);  // Tenta converter para float
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_LONG).show();
            return;
        }

        // Obtém o saldo da conta remetente (ID fixo)
        int idRemetente = 1;  // ID fixo do remetente, altere conforme necessário
        double saldoAtual = repositorioConta.getSaldo(idRemetente);
        if (saldoAtual < valor) {
            Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_LONG).show();
            return;
        }

        // Atualiza o saldo do remetente após a transação
        repositorioConta.atualizarSaldo(idRemetente, saldoAtual - valor);

        // Sucesso na transação
        Toast.makeText(this, "PIX enviado com sucesso", Toast.LENGTH_LONG).show();

        // Atualiza o saldo na tela após o envio
        atualizarSaldoNaTela();

        limparTela();
    }

    // Método para verificar se o usuário tem chave Pix cadastrada
    private boolean usuarioTemChaveCadastrada() {
        // Aqui você pode verificar no banco de dados se o usuário já tem uma chave Pix cadastrada.
        // Supondo que o ID do usuário seja 1 (substitua pelo ID real do usuário)
        return repositorioChavePix.usuarioTemChaveCadastrada(1);  // Passa o ID do usuário (exemplo: 1)
    }

    // Método para validar CPF ou telefone (chave Pix)
    private boolean validarChaveReceber(String chaveReceber) {
        return validarCPF(chaveReceber) || validarTelefone(chaveReceber);
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

    // Método para atualizar o saldo na tela
    private void atualizarSaldoNaTela() {
        // Obtém o saldo do banco de dados e exibe na tela
        double saldo = repositorioConta.getSaldo(1); // Usando ID 1 como exemplo, altere conforme necessário
        caixaSaldo.setText(String.format("R$ %.2f", saldo));
    }

    // Limpar a tela
    public void limparTela() {
        EditText caixaEnviarPix = findViewById(R.id.editTextEnviarPix);
        EditText caixaChaveReceber = findViewById(R.id.editTextChaveReceber);
        caixaEnviarPix.setText("");
        caixaChaveReceber.setText("");
    }
}



