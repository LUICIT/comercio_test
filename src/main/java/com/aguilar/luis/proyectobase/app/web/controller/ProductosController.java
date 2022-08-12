package com.aguilar.luis.proyectobase.app.web.controller;

import com.aguilar.luis.proyectobase.app.domain.entity.Producto;
import com.aguilar.luis.proyectobase.app.exception.BadRequestAlertException;
import com.aguilar.luis.proyectobase.app.exception.NotFoundRequestAlertException;
import com.aguilar.luis.proyectobase.app.service.ProductosService;
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
 * REST controller for managing Productos.
 */
@RestController
@RequestMapping("/api/v1")
@Api(description = "Controlador de Producto", tags = {"Producto"})
public class ProductosController {

    private final Logger log = LoggerFactory.getLogger(ProductosController.class);

    private static final String ENTITY_NAME = "Productos";

    private final ProductosService service;

    public ProductosController(ProductosService service) {
        this.service = service;
    }

    /**
     * POST  /productos : Create a new producto.
     *
     * @param producto the producto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productos, or with status 400 (Bad Request) if the productos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(value = "Método para crear nuevos productos.",
            notes = "Este método sirve para crear nuevos productos en la base de datos.",
            tags = "Producto")
    @PostMapping("/productos")
    public ResponseEntity<Producto> createProducto(@Validated @RequestBody Producto producto) throws URISyntaxException {
        log.debug("REST request to save Producto : {}", producto);
        if (producto.getId() != null) {
            throw new BadRequestAlertException("A new producto cannot have an ID", ENTITY_NAME, "idexists");
        }
        Producto result = service.create(producto);
        return ResponseEntity.created(new URI("/api/v1/productos/" + result.getId()))
                .body(result);
    }

    /**
     * GET  /productos : get all the productos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productos in body
     */
    @ApiOperation(value = "Método para obtener todos los productos.",
            notes = "Este método sirve para obtener todos los productos de la base de datos " +
                    "sin ningún filtro o paginación que limite los datos a obtener.",
            tags = "Producto")
    @GetMapping("/productos")
    public Iterable<Producto> getAllProductos() {
        log.debug("REST request to get all Productos");
        return service.findAll();
    }

    /**
     * GET  /productos/:id : get the "id" producto.
     *
     * @param id the id of the producto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the producto, or with status 404 (Not Found)
     */
    @ApiOperation(value = "Método para obtener un producto.",
            notes = "Este método sirve para obtener un producto de acuerdo a su ID " +
                    "en la base de datos la cual es su identificador.",
            tags = "Producto")
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable Long id) {
        log.debug("REST request to get Producto : {}", id);
        Optional<Producto> producto = service.find(id);
        if (producto.isPresent()) {
            return new ResponseEntity<>(producto.get(), HttpStatus.OK);
        }
        throw new NotFoundRequestAlertException("Account with ID not found", ENTITY_NAME, "notfound");
    }

    /**
     * PUT  /productos : Updates an existing producto.
     *
     * @param producto the producto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated producto,
     * or with status 400 (Bad Request) if the producto is not valid,
     * or with status 500 (Internal Server Error) if the producto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(value = "Método para obtener actualizar un producto.",
            notes = "Este método sirve para actualizar un producto por completo dentro de la base de datos.",
            tags = "Producto")
    @PutMapping("/productos")
    public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto) throws URISyntaxException {
        log.debug("REST request to update Producto : {}", producto);
        if (producto.getId() == null) {
            return createProducto(producto);
        }
        Producto result = service.update(producto);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * PATCH  /productos : Updates an existing producto.
     *
     * @param producto the producto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated producto,
     * or with status 400 (Bad Request) if the producto is not valid,
     * or with status 500 (Internal Server Error) if the producto couldn't be updated
     */
    @ApiOperation(value = "Método para obtener actualizar un producto.",
            notes = "Este método sirve para actualizar alguna parte del producto dentro " +
                    "de la base de datos.",
            tags = "Producto")
    @PatchMapping("/productos")
    public ResponseEntity<Producto> modifyProducto(@RequestBody Producto producto) {
        log.debug("REST request to update Producto : {}", producto);
        Producto result = service.change(producto);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * DELETE  /productos/:id : delete the "id" producto.
     *
     * @param id the id of the producto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @ApiOperation(value = "Método para eliminar un producto.",
            notes = "Este método sirve para eliminar un producto dentro de la base de datos.",
            tags = "Producto")
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        log.debug("REST request to delete Producto : {}", id);
        service.remove(id);
        return ResponseEntity.ok()
                .build();
    }

}
