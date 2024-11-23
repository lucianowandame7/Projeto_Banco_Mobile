package com.example.bancogremio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DepositoContaActivity extends AppCompatActivity {

    private EditText caixaDeposito;
    private EditText caixaSaldo;
    private RepositorioConta repositorioConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito_conta);

        // Inicializa o banco de dados
        repositorioConta = new RepositorioConta(this);

        // Liga os elementos da interface
        caixaDeposito = findViewById(R.id.editTextDepositoConta);
        caixaSaldo = findViewById(R.id.editTextSaldo);
        Button buttonDepositar = findViewById(R.id.buttonDepositarConta);

        // Configura o botão para realizar depósitos
        buttonDepositar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DepositarConta();
            }
        });

        // Configura o campo de saldo para exibição somente leitura
        caixaSaldo.setKeyListener(null);
        atualizarSaldoNaTela();
    }

    public void DepositarConta() {
        String valorString = caixaDeposito.getText().toString();

        // Validar o valor inserido
        if (valorString.isEmpty()) {
            Toast.makeText(this, "Por favor, insira um valor.", Toast.LENGTH_SHORT).show();
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

            // Calcula o novo saldo e atualiza no banco de dados
            double novoSaldo = saldoAtual + valor;
            repositorioConta.atualizarSaldo(1, novoSaldo); // Usando ID 1 como exemplo, altere conforme necessário

            // Atualiza o saldo na tela
            atualizarSaldoNaTela();

            // Exibe mensagem de sucesso
            Toast.makeText(this, "Depósito realizado com sucesso!", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            // Tratamento para valores inválidos
            Toast.makeText(this, "Digite um valor numérico válido.", Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizarSaldoNaTela() {
        // Obtém o saldo do banco de dados e exibe na tela
        double saldo = repositorioConta.getSaldo(1); // Usando ID 1 como exemplo, altere conforme necessário
        caixaSaldo.setText(String.format("R$ %.2f", saldo));
    }
}
