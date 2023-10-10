package com.raspberry.awards;


import com.raspberry.awards.jpa.model.Nominated;
import com.raspberry.awards.jpa.model.Producer;
import com.raspberry.awards.jpa.repository.NominatedRepository;
import com.raspberry.awards.jpa.repository.ProducerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.util.Lists.newArrayList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WinnersControllerTests {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private NominatedRepository nominatedRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Test
    void dadoIntervaloQuandoPesquisadosEntaoRetornaLista() throws Exception {
        setUpData();
        ResultActions result = mockMvc.perform(get("/winners-intervals/min-max"));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.min", hasSize(1)))
                .andExpect(jsonPath("$.min[0].producer", is("Producer 2")))
                .andExpect(jsonPath("$.min[0].interval", is(1)))
                .andExpect(jsonPath("$.min[0].previousWin", is(2002)))
                .andExpect(jsonPath("$.min[0].followingWin", is(2003)))
                .andExpect(jsonPath("$.max", hasSize(1)))
                .andExpect(jsonPath("$.max[0].producer", is("Producer 1")))
                .andExpect(jsonPath("$.max[0].interval", is(2)))
                .andExpect(jsonPath("$.max[0].previousWin", is(2001)))
                .andExpect(jsonPath("$.max[0].followingWin", is(2003)));
    }

    @Test
    void dadoGanhadoresExistentesQuandoPesquisadosEntaoRetornaLista() throws Exception {
        setUpData();
        ResultActions result = mockMvc.perform(get("/winners-intervals"));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void dadoIntervaloInexistenteQuandoPesquisadosEntaoRetornaListaVazia() throws Exception {
        nominatedRepository.deleteAll();
        ResultActions result = mockMvc.perform(get("/winners-intervals"));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void quandoRequestParaRotaNaoMapeadoEntaoRetornaMensagemDeErro() throws Exception {
        ResultActions result = mockMvc.perform(get("/rota-inexistente"));
        result.andExpect(status().isNotFound())
                .andExpect(content().string("O request não corresponde a nenhum endpoint mapeado." +
                        " Para encontrar a documentação desta API acesse: http://localhost:8080/swagger-ui/index.html"));
    }

    @Test
    void quandoRequestProcessadaComErroEntaoRetornaMensagemDeErro() throws Exception {
        Nominated nominated = nominatedRepository.findByWinnerOrderByYear(true).get(0);
        nominated.setStudios(null);
        nominatedRepository.save(nominated);
        ResultActions result = mockMvc.perform(get("/winners-intervals/min-max"));
        result.andExpect(status().isInternalServerError())
                .andExpect(content().string("Ocorreu um erro no processamento do request."));
    }

    private void setUpData() {
        Producer p1 = new Producer("Producer 1");
        Producer p2 = new Producer("Producer 2");
        Producer p3 = new Producer("Producer 3");
        producerRepository.saveAll(newArrayList(p1,p2,p3));

        Nominated n1 = new Nominated(new String[]{"2001","Title I","Studio","","yes"}, newArrayList(p1));
        Nominated n2 = new Nominated(new String[]{"2001","Title A","Studio","","no"}, newArrayList(p3));

        Nominated n3 = new Nominated(new String[]{"2002","Title II","Studio","","yes"}, newArrayList(p2));
        Nominated n4 = new Nominated(new String[]{"2002","Title B","Studio","","no"}, newArrayList(p3));

        Nominated n5 = new Nominated(new String[]{"2003","Title III","Studio","","yes"}, newArrayList(p1,p2));
        Nominated n6 = new Nominated(new String[]{"2003","Title C","Studio","","no"}, newArrayList(p3));
        nominatedRepository.saveAll(newArrayList(n1,n2,n3,n4,n5,n6));
    }
}