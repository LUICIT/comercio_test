package com.aguilar.luis.proyectobase.app.service;

import com.aguilar.luis.proyectobase.app.domain.entity.Categoria;
import com.aguilar.luis.proyectobase.app.domain.repository.CategoriasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoriasService {
	
	private final Logger log = LoggerFactory.getLogger(CategoriasService.class);

    private final CategoriasRepository repository;

    public CategoriasService(CategoriasRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Guarda una categoria.
     *
     * @param categoria the entity to save
     * @return the persisted entity
     */
    public Categoria create(Categoria categoria) {
    	log.debug("Request to save Categoria : {}", categoria);
        return repository.save(categoria);
    }

    /**
     * Obtiene todas las categorias.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Iterable<Categoria> findAll() {
        log.debug("Request to get all Categoria");
        return repository.findAll();
    }

    /**
     * Obtiene una categoria por ID.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Categoria> find(long id) {
        log.debug("Request to get Categoria : {}", id);
        return repository.findById(id);
    }


    /**
     * Actualiza la categoria por ID.
     *
     * @param categoria the id of the entity
     */
    public Categoria update(Categoria categoria) {
    	log.debug("Request to update Categoria : {}", categoria);
        return repository.save(categoria);
    }


    /**
     * Actualiza parte de la categoria por ID.
     *
     * @param categoria the id of the entity
     */
    public Categoria change(Categoria categoria) {
    	log.debug("Request to change Categoria : {}", categoria);
        return repository.save(categoria);
    }

    /**
     * Elimina la categoria por ID.
     *
     * @param id the id of the entity
     */
    public boolean remove(long id) {
        if (repository.existsById(id)) {
        	log.debug("Request to delete Categoria : {}", id);
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
