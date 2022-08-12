package com.aguilar.luis.proyectobase.app.web.controller;

import com.aguilar.luis.proyectobase.app.domain.entity.Producto;
import com.aguilar.luis.proyectobase.app.remoto.service.VentasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing Ventas como MS.
 */
@RestController
@RequestMapping("/api/v1")
@Api(description = "Controlador de Venta", tags = {"Venta"})
public class VentasController {

    private final Logger log = LoggerFactory.getLogger(VentasController.class);

    private final VentasService service;

    public VentasController(VentasService service) {
        this.service = service;
    }

    /**
     * GET  /ventas : get all the ventas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ventas in body
     */
    @ApiOperation(value = "Método para obtener todas las ventas.",
            notes = "Este método sirve para obtener todas las ventas de un micro-servicio " +
                    "externo sin ningún limite para obtener datos.",
            tags = "Venta")
    @GetMapping("/ventas")
    public List<Producto> getAllVentas() {
        log.debug("REST request to get all Ventas");
        return service.findAll();
    }

}
