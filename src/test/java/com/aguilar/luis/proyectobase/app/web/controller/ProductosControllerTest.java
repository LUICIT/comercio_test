package com.aguilar.luis.proyectobase.app.web.controller;

import com.aguilar.luis.proyectobase.app.domain.entity.Categoria;
import com.aguilar.luis.proyectobase.app.domain.entity.Producto;
import com.aguilar.luis.proyectobase.app.domain.generate.ProductosDatos;
import com.aguilar.luis.proyectobase.app.service.ProductosService;
import com.github.javafaker.Faker;
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
@WebMvcTest(ProductosController.class)
class ProductosControllerTest {

    @Value("${test.localhost.api.base-url}")
    private String baseUrl;

    private final Logger log = LoggerFactory.getLogger(CategoriasControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductosService service;

    @Test
    void getAllAPI() throws Exception {
        log.debug("TEST to get all Productos");
        given(service.findAll()).willReturn(new ProductosDatos(3));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(baseUrl + "/productos")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    void getAPI() throws Exception {
        Faker faker = new Faker();
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre(faker.name().fullName());
        categoria.setDescripcion(faker.lorem().sentence());
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre(faker.commerce().productName());
        producto.setPrecio(Double.parseDouble(faker.commerce().price()));
        producto.setDescripcion(faker.lorem().sentence());
        producto.setCategoria(categoria);
        given(service.find(1)).willReturn(Optional.of(producto));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(baseUrl + "/productos/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

}
