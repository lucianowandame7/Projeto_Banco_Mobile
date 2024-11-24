package com.example.bancogremio;

public class Transacao {
    public String tipo;
    public double valor;

    public Transacao(String tipo, double valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return tipo + " - R$ " + String.format("%.2f", valor);
    }
}
