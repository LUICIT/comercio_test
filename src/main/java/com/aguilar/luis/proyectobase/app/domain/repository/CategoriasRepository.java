package com.aguilar.luis.proyectobase.app.domain.repository;

import com.aguilar.luis.proyectobase.app.domain.entity.Categoria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriasRepository extends CrudRepository<Categoria, Long> {

}
