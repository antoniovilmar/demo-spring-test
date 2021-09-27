package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContaTest {

    @Test
    @DisplayName("Ao criar conta bancária é ncessário informar um CPF válido")
    void deveCriarContaBancarioInformandoTitularDaConta() {
        String cpf = "44330894009";
        Conta conta = new Conta(cpf);

        assertEquals("44330894009", conta.getCpf());
    }

    @Test
    @DisplayName("O saldo da conta deve estar zerado ao criar a conta bancária")
    void deveCriarContaComSaldoZerado() {
        String cpf = "44330894009";
        Conta conta = new Conta(cpf);

        assertEquals(0, conta.getSaldo());
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar criar conta bancária informando CPF inválido")
    void naoDeveCriarContaBancariaQuandoTitularInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Conta("9999");
                });

        assertEquals(Conta.MENSAGEM_CPF_INVÁLDO, exception.getMessage());
    }

    @Test
    @DisplayName("Dado que tenho 0 reais na conta, quando deposito 50 reais é esperado que o saldo seja atualizado para 50 reais")
    void deveDepositarValorNaConta() {
        var conta = new Conta("44330894009");
        conta.depositar(50);

        assertEquals(50, conta.getSaldo());


    }

}