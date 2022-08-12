package com.aguilar.luis.proyectobase.app.remoto.repository;

import com.aguilar.luis.proyectobase.app.configuration.FeignClientConfig;
import com.aguilar.luis.proyectobase.app.domain.entity.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "VENTAS-MOCK-API", url = "${external.localhost.api.base-url}/productos", configuration = FeignClientConfig.class)
public interface VentaRepository {

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Producto> findAll();

}
