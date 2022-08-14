package com.aguilar.luis.proyectobase.app.web.controller;

import com.aguilar.luis.proyectobase.app.domain.entity.Categoria;
import com.aguilar.luis.proyectobase.app.exception.BadRequestAlertException;
import com.aguilar.luis.proyectobase.app.exception.NotFoundRequestAlertException;
import com.aguilar.luis.proyectobase.app.service.CategoriasService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing Categorias.
 */
@RestController
@RequestMapping("/api/v1")
@Api(description = "Controlador de Categoría", tags = {"Categoría"})
public class CategoriasController {
	
	private final Logger log = LoggerFactory.getLogger(CategoriasController.class);

    private static final String ENTITY_NAME = "Categorias";

    private final CategoriasService service;

    public CategoriasController(CategoriasService service) {
        this.service = service;
    }
    
    /**
     * POST  /categorias : Create a new categoria.
     *
     * @param categoria the categoria to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categorias, or with status 400 (Bad Request) if the categorias has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(value = "Método para crear nuevas categorías.",
            notes = "Este método sirve para crear nuevas categorías en la base de datos.",
            tags = "Categoría")
    @PostMapping("/categorias")
    public ResponseEntity<Categoria> createCategoria(@Validated @RequestBody Categoria categoria) throws URISyntaxException {
        log.debug("REST request to save Categoria : {}", categoria);
        if (categoria.getId() != null && categoria.getId() != 0L) {
            throw new BadRequestAlertException("A new categoria cannot have an ID", ENTITY_NAME, "idexists");
        }
        Categoria result = service.create(categoria);
        return ResponseEntity.created(new URI("/api/v1/categorias/" + result.getId()))
                .body(result);
    }

    /**
     * GET  /categorias : get all the categorias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of categorias in body
     */
    @ApiOperation(value = "Método para obtener todas las categorías.",
            notes = "Este método sirve para obtener todas las categorías de la base de datos " +
                    "sin ningún filtro o paginación que limite los datos a obtener.",
            tags = "Categoría")
    @GetMapping("/categorias")
    public Iterable<Categoria> getAllCategorias() {
        log.debug("REST request to get all Categorias");
        return service.findAll();
    }

    /**
     * GET  /categorias/:id : get the "id" categoria.
     *
     * @param id the id of the categoria to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categoria, or with status 404 (Not Found)
     */
    @ApiOperation(value = "Método para obtener una categoría.",
            notes = "Este método sirve para obtener una categoría de acuerdo a su ID " +
                    "en la base de datos la cual es su identificador.",
            tags = "Categoría")
    @GetMapping("/categorias/{id}")
    public ResponseEntity<Categoria> getCategoria(@PathVariable Long id) {
        log.debug("REST request to get Categoria : {}", id);
        Optional<Categoria> categoria = service.find(id);
        if (categoria.isPresent()) {
            return new ResponseEntity<>(categoria.get(), HttpStatus.OK);
        }
        throw new NotFoundRequestAlertException("Account with ID not found", ENTITY_NAME, "notfound");
    }

    /**
     * PUT  /categorias : Updates an existing categoria.
     *
     * @param categoria the categoria to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categoria,
     * or with status 400 (Bad Request) if the categoria is not valid,
     * or with status 500 (Internal Server Error) if the categoria couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(value = "Método para obtener actualizar una categoría.",
            notes = "Este método sirve para actualizar una categoría por completo dentro de la base de datos.",
            tags = "Categoría")
    @PutMapping("/categorias")
    public ResponseEntity<Categoria> updateCategoria(@RequestBody Categoria categoria) throws URISyntaxException {
        log.debug("REST request to update Categoria : {}", categoria);
        if (categoria.getId() == null) {
            return createCategoria(categoria);
        }
        Categoria result = service.update(categoria);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * PATCH  /categorias : Updates an existing categoria.
     *
     * @param id    the categoria to update
     * @param patch what will be updated
     * @return the ResponseEntity with status 200 (OK) and with body the updated categoria,
     * or with status 400 (Bad Request) if the categoria is not valid,
     * or with status 500 (Internal Server Error) if the categoria couldn't be updated
     */
    @ApiOperation(value = "Método para obtener actualizar una categoría.",
            notes = "Este método sirve para actualizar alguna parte de la categoría dentro " +
                    "de la base de datos.",
            tags = "Categoría")
    @PatchMapping(path = "/categorias/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Categoria> modifyCategoria(@PathVariable Long id, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        log.debug("REST request to update Categoria : {}", patch);
        Optional<Categoria> optionalCategoria = service.find(id);
        if (optionalCategoria.isPresent()) {
            Categoria categoria = optionalCategoria.get();
            Categoria categoriaPatched = applyPatchToCategoria(patch, categoria);
            Categoria result = service.change(categoriaPatched);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        throw new NotFoundRequestAlertException("Account with ID not found", ENTITY_NAME, "notfound");
    }

    private Categoria applyPatchToCategoria(JsonPatch patch, Categoria targetCategoria) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(targetCategoria, JsonNode.class));
        return objectMapper.treeToValue(patched, Categoria.class);
    }

    /**
     * DELETE  /categorias/:id : delete the "id" categoria.
     *
     * @param id the id of the categoria to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @ApiOperation(value = "Método para eliminar una categoría.",
            notes = "Este método sirve para eliminar una categoría dentro de la base de datos.",
            tags = "Categoría")
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        log.debug("REST request to delete Categoria : {}", id);
        service.remove(id);
        return ResponseEntity.ok()
                .build();
    }

}
