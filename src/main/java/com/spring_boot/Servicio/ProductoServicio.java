package com.spring_boot.Servicio;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.spring_boot.Entidad.Producto;
import com.spring_boot.Repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicio {

    @Autowired
    ProductoRepositorio productoRepositorio;

    public List<Producto> listaProductos(){
        return productoRepositorio.findAll();
    }

    public Optional<Producto> buscarProducto(Long id){
        return productoRepositorio.findById(id);
    }

    public void guardarProducto(Producto producto){
        productoRepositorio.save(producto);
    }

    public void eliminarProducto(Long id){
        productoRepositorio.deleteById(id);
    }

}