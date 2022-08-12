package com.aguilar.luis.proyectobase.app.service;

import com.aguilar.luis.proyectobase.app.domain.entity.Producto;
import com.aguilar.luis.proyectobase.app.domain.repository.ProductosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductosService {

    private final Logger log = LoggerFactory.getLogger(ProductosService.class);

    private final ProductosRepository repository;

    public ProductosService(ProductosRepository productosRepository) {
        this.repository = productosRepository;
    }

    /**
     * Guarda un producto.
     *
     * @param producto the entity to save
     * @return the persisted entity
     */
    public Producto create(Producto producto) {
        log.debug("Request to save Producto : {}", producto);
        return repository.save(producto);
    }

    /**
     * Obtiene todos los productos.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Iterable<Producto> findAll() {
        log.debug("Request to get all Producto");
        return repository.findAll();
    }

    /**
     * Obtiene un producto por ID.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Producto> find(long id) {
        return repository.findById(id);
    }


    /**
     * Actualiza el producto por ID.
     *
     * @param producto the id of the entity
     */
    public Producto update(Producto producto) {
        log.debug("Request to update Producto : {}", producto);
        return repository.save(producto);
    }


    /**
     * Actualiza parte del producto por ID.
     *
     * @param producto the id of the entity
     */
    public Producto change(Producto producto) {
        log.debug("Request to change Producto : {}", producto);
        return repository.save(producto);
    }

    /**
     * Elimina el producto por ID.
     *
     * @param id the id of the entity
     */
    public boolean remove(long id) {
        if (repository.existsById(id)) {
            log.debug("Request to delete Producto : {}", id);
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
