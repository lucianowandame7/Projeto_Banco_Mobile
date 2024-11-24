package com.example.bancogremio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bancogremio.R;

public class RemoverContaActivity extends AppCompatActivity {

    private RepositorioConta repositorioConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remover_conta_activityy);

        // Inicializa o banco de dados
        repositorioConta = new RepositorioConta(this);

        // Liga os elementos da interface
        EditText caixaRemover = findViewById(R.id.editTextRemoverDinheiroConta);
        EditText caixaSaldo = findViewById(R.id.editTextSaldoRemover);
        Button buttonRemover = findViewById(R.id.buttonRemoverDinheiro);

        // Configura o campo de saldo para exibição somente leitura
        caixaSaldo.setKeyListener(null);
        atualizarSaldoNaTela(caixaSaldo);

        // Configura o botão para remover dinheiro
        buttonRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removerDinheiroConta(caixaRemover, caixaSaldo);
            }
        });
    }

    // Método para remover dinheiro da conta
    public void removerDinheiroConta(EditText caixaRemover, EditText caixaSaldo) {
        String valorString = caixaRemover.getText().toString();

        // Validar o valor inserido
        if (valorString.isEmpty()) {
            Toast.makeText(this, "Por favor, insira um valor para remover.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Converte o valor para double
            double valor = Double.parseDouble(valorString);

            // Verifica se o valor é positivo
            if (valor <= 0) {
                Toast.makeText(this, "Digite um valor maior que zero.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtém o saldo atual
            double saldoAtual = repositorioConta.getSaldo(1); // Usando ID 1 como exemplo, altere conforme necessário

            // Verifica se há saldo suficiente para remoção
            if (saldoAtual < valor) {
                Toast.makeText(this, "Saldo insuficiente para realizar a remoção.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Calcula o novo saldo após remoção
            double novoSaldo = saldoAtual - valor;
            repositorioConta.atualizarSaldo(1, novoSaldo); // Usando ID 1 como exemplo, altere conforme necessário

            // Registra a transação no banco de dados
            repositorioConta.adicionarTransacao(1, "Remoção", -valor); // Valor negativo para indicar remoção

            // Atualiza o saldo na tela
            atualizarSaldoNaTela(caixaSaldo);

            // Exibe mensagem de sucesso
            Toast.makeText(this, "Remoção realizada com sucesso!", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            // Tratamento para valores inválidos
            Toast.makeText(this, "Digite um valor numérico válido.", Toast.LENGTH_SHORT).show();
        }
    }

    // Atualiza o saldo na tela
    private void atualizarSaldoNaTela(EditText caixaSaldo) {
        // Obtém o saldo do banco de dados e exibe na tela
        double saldo = repositorioConta.getSaldo(1); // Usando ID 1 como exemplo, altere conforme necessário
        caixaSaldo.setText(String.format("R$ %.2f", saldo));
    }
}
