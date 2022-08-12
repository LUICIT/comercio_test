package com.aguilar.luis.proyectobase.app.domain.repository;

import com.aguilar.luis.proyectobase.app.domain.entity.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosRepository extends CrudRepository<Producto, Long> {

}
