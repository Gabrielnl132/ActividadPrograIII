package com.spring_boot.Entidad;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
//@Table(name = "PRODUCTO") == Nombre de la Tabla
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable = false)
    private String nombre;

    private Double precio;
    private int stock;
    private String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @OneToOne
    @JoinColumn(name = "garantia_id")
    private Garantia garantia;

    @ManyToMany(mappedBy = "productos")
    private List<Cliente> clientes;
}
