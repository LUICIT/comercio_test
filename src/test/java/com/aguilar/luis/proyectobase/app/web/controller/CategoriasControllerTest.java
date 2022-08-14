package com.aguilar.luis.proyectobase.app.web.controller;

import com.aguilar.luis.proyectobase.app.domain.entity.Categoria;
import com.aguilar.luis.proyectobase.app.domain.generate.CategoriaDatos;
import com.aguilar.luis.proyectobase.app.service.CategoriasService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoriasController.class)
class CategoriasControllerTest {

    @Value("${test.localhost.api.base-url}")
    private String baseUrl;

    private final Logger log = LoggerFactory.getLogger(CategoriasControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriasService service;

    @Test
    void getAllAPI() throws Exception {
        log.debug("TEST to get all Categoria");
        given(service.findAll()).willReturn(new CategoriaDatos(3));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(baseUrl + "/categorias")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    void getAPI() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Categoría 1");
        categoria.setDescripcion("Esta es una descripción para la Categoría 1");
        given(service.find(1)).willReturn(Optional.of(categoria));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(baseUrl + "/categorias/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

}
