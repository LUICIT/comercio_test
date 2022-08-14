package com.aguilar.luis.proyectobase.app.domain.generate;

import com.aguilar.luis.proyectobase.app.domain.entity.Categoria;

import java.util.Iterator;

public class CategoriaDatos implements Iterable<Categoria> {

    private long position = 0;
    private final int cantidad;

    public CategoriaDatos(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public Iterator<Categoria> iterator() {
        int cantidad = this.cantidad;
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return position < cantidad;
            }
            @Override
            public Categoria next() {
                position++;
                Categoria categoria = new Categoria();
                categoria.setId(position);
                categoria.setNombre("Categoría " + position);
                categoria.setDescripcion("Esta es una descripción para la Categoría " + position);
                return categoria;
            }
        };
    }

    /*@Override
    public Iterator<Categoria> iterator() {

        return new Iterator<>() {

            @Override
            public boolean hasNext() {
                return position < 2;
            }

            @Override
            public Categoria next() {
                position++;
                //Faker faker = new Faker();
                //return new Categoria(faker.name().fullName(), faker.lorem().sentence());
                Categoria categoria = new Categoria();
                categoria.setId(position + 1);
                categoria.setNombre("Categoría " + (position + 1));
                categoria.setDescripcion("Esta es una descripción para la Categoría " + (position + 1));
                return categoria;
            }

        };
    }*/

}
