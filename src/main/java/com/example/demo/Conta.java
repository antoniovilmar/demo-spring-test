package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Conta {
    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    protected static final String MENSAGEM_CPF_INVÁLDO = "CPF Inváldo";
    private String cpf;
    @Id
    private Long numeroConta;
    private double saldo;

    private Conta() {
    }

    public Conta(String cpf) {
        validarCpf(cpf);
        this.cpf = cpf;
        this.numeroConta = (long) (1000000000000L + Math.random() * 8999999999999l);
        this.saldo = 0;
    }

    public void depositar(double valor) {
        this.saldo += valor;
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    private void validarCpf(String cpf) {
        if ((cpf.length() != 11) || validarSequenciaCaracter(cpf)) {
            throw new IllegalArgumentException(MENSAGEM_CPF_INVÁLDO);
        }

        Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);

        if (!isCPFValido(cpf, digito1, digito2)) {
            throw new IllegalArgumentException(MENSAGEM_CPF_INVÁLDO);
        }
    }

    private Boolean validarSequenciaCaracter(String documento) {
        String documentoTemp;
        for (int i = 0; i <= 9; i++) {
            documentoTemp = "";
            for (int j = 0; j < 11; j++) {
                documentoTemp += i;
            }
            if (documento.equals(documentoTemp)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCPFValido(String cpf, Integer digito1, Integer digito2) {
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    public double getSaldo() {
        return saldo;
    }

    public Long getNumeroConta() {
        return numeroConta;
    }

    public String getCpf() {
        return cpf;
    }


}
