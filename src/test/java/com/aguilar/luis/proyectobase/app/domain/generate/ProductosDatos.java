package com.aguilar.luis.proyectobase.app.domain.generate;

import com.aguilar.luis.proyectobase.app.domain.entity.Categoria;
import com.aguilar.luis.proyectobase.app.domain.entity.Producto;
import com.github.javafaker.Faker;

import java.util.Iterator;
import java.util.Locale;

public class ProductosDatos implements Iterable<Producto> {

    private long position = 0;
    private final int cantidad;
    private final Categoria categoria;

    public ProductosDatos(int cantidad) {
        this.cantidad = cantidad;
        Faker faker = new Faker();
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre(faker.name().fullName());
        categoria.setDescripcion(faker.lorem().sentence());
    }

    @Override
    public Iterator<Producto> iterator() {
        int cantidad = this.cantidad;
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return position < cantidad;
            }
            @Override
            public Producto next() {
                position++;
                Faker faker = new Faker(new Locale("es-MX"));
                Producto producto = new Producto();
                producto.setId(position);
                producto.setNombre(faker.commerce().productName());
                producto.setPrecio(Double.parseDouble(faker.commerce().price()));
                producto.setDescripcion(faker.lorem().sentence());
                producto.setCategoria(categoria);
                return producto;
            }
        };
    }

}
