package com.aguilar.luis.proyectobase.app.remoto.service;

import com.aguilar.luis.proyectobase.app.domain.entity.Producto;
import com.aguilar.luis.proyectobase.app.remoto.repository.VentaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentasService {

    private final VentaRepository repository;

    public VentasService(VentaRepository repository) {
        this.repository = repository;
    }

    private final Logger log = LoggerFactory.getLogger(VentasService.class);

    public List<Producto> findAll() {
        log.debug("Request to get all Ventas");
        return repository.findAll();
    }

}
