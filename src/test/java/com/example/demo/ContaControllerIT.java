package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ContaControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContaRepository contaRepository;

    @Test
    @DisplayName("Deve abrir a conta bancária passando o CPF do titular")
    public void deveAbrirContaBancaria() throws Exception {

        mockMvc
                .perform(post("/conta").contentType(MediaType.APPLICATION_JSON).content("22143662025"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroConta", notNullValue()));
    }

    @Test
    @DisplayName("Deve abrir retornar erro ao tentar abrir conta com CPF inválido")
    public void deveRetornarErroDeNegocioAoTentarAbrirContaComCPFInvalido() throws Exception {

        mockMvc
                .perform(post("/conta").contentType(MediaType.APPLICATION_JSON).content("123123"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$", is(Conta.MENSAGEM_CPF_INVÁLDO)));
    }

    @Test
    @DisplayName("Deve retornar a conta bancária, buscando pelo número da conta")
    public void deveBuscarContaBancaria() throws Exception {
        Conta conta = new Conta("46916436050");
        Conta contaSalva = contaRepository.save(conta);

        mockMvc
                .perform(get("/conta/" + contaSalva.getNumeroConta()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroConta", is(contaSalva.getNumeroConta())))
                .andExpect(jsonPath("$.cpf", is("46916436050")))
                .andExpect(jsonPath("$.saldo", is(0.0)));
    }


    @Test
    @DisplayName("Não deve retornar conta, quando não existe")
    public void naoDeveEncontrarContaQuandoNaoExiste() throws Exception {
        mockMvc
                .perform(get("/conta/12312312312"))
                .andExpect(status().isNotFound());
    }

}