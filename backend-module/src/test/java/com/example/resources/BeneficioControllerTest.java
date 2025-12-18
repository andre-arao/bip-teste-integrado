package com.example.resources;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.ApiApplicationTests;
import com.example.backend.controller.BeneficioController;
import com.example.ejb.entity.Beneficio;
import com.example.ejb.entity.dto.BeneficioDto;
import com.example.ejb.entity.dto.TransferenciaDto;
import com.example.ejb.service.BeneficioEjbService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@ContextConfiguration(classes = ApiApplicationTests.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BeneficioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeneficioEjbService beneficioEjbService;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Beneficio beneficio;

    @BeforeEach
    void setup() {
        beneficio = new Beneficio();
        beneficio.setId(1L);
        beneficio.setNome("Vale Alimentação");
        beneficio.setDescricao("Benefício de alimentação");
        beneficio.setValor(BigDecimal.valueOf(500));
        beneficio.setAtivo(true);
    }

    @Test
    void testListBeneficios() throws Exception {
        List<Beneficio> beneficios = Arrays.asList(beneficio);

        when(beneficioEjbService.findAll()).thenReturn(beneficios);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beneficios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Vale Alimentação"));
    }

    @Test
    void testCreateBeneficio() throws Exception {
        BeneficioDto dto = new BeneficioDto();
        dto.setNome("Vale Transporte");
        dto.setDescricao("Benefício de transporte");
        dto.setValor(BigDecimal.valueOf(300));

        Beneficio created = new Beneficio();
        created.setId(2L);
        created.setNome(dto.getNome());
        created.setDescricao(dto.getDescricao());
        created.setValor(dto.getValor());
        created.setAtivo(true);

        when(beneficioEjbService.create(any(BeneficioDto.class))).thenReturn(created);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/beneficios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testTransferBeneficio() throws Exception {
        TransferenciaDto dto = new TransferenciaDto();
        dto.setFromId(1L);
        dto.setToId(2L);
        dto.setAmount(BigDecimal.valueOf(100));

        doNothing().when(beneficioEjbService).transfer(dto.getFromId(), dto.getToId(), dto.getAmount());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/beneficios/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteBeneficio() throws Exception {
        doNothing().when(beneficioEjbService).delete(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beneficios/1"))
                .andExpect(status().isNoContent());
    }
}
