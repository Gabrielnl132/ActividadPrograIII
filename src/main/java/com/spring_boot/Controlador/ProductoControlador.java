package com.spring_boot.Controlador;

import com.spring_boot.Entidad.Cliente;
import com.spring_boot.Entidad.Producto;
import com.spring_boot.Servicio.ClienteServicio;
import com.spring_boot.Servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/productos")
    public String mostrarProductos(Model model) {
        List<Producto> productos = productoServicio.listaProductos();
        List<Cliente> clientes = clienteServicio.listaClientes();
        model.addAttribute("productos", productos);
        model.addAttribute("clientes", clientes);
        return "Producto/vistaProductos";
    }

    // Crear
    @GetMapping("/formulario")
    public String formularioProducto(Model model) {
        model.addAttribute("producto", new Producto());
        return "Producto/formularioProducto";
    }

    @PostMapping("/guardar")
    public String crearProducto(Producto producto) {
        productoServicio.guardarProducto(producto);
        return "redirect:/productos";
    }

    // Actualizar
    @GetMapping("/editar/producto/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {
        Optional<Producto> producto = productoServicio.buscarProducto(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
        } else {
            model.addAttribute("error", "Producto no encontrado");
        }
        return "Producto/formularioProducto";
    }


    // Asociar producto a cliente
    @PostMapping("/asociarProductoCliente")
    public String asociarProductoCliente(@RequestParam Long productoId, @RequestParam Long clienteId) {
        Optional<Producto> producto = productoServicio.buscarProducto(productoId);
        Optional<Cliente> cliente = clienteServicio.buscarClientePorId(clienteId);

        if (producto.isPresent() && cliente.isPresent()) {
            Producto productoExistente = producto.get();
            Cliente clienteExistente = cliente.get();
            clienteExistente.getProductos().add(productoExistente);
            clienteServicio.guardarCliente(clienteExistente);
        } else {
            // Manejar el caso en el que producto o cliente no se encuentre
            return "redirect:/productos?error=ProductoOClienteNoEncontrado";
        }
        return "redirect:/productos";
    }
    //ELIMINAR
    @GetMapping("/eliminar/producto/{id}")
    public String borrarProducto(@PathVariable Long id) {
        productoServicio.eliminarProducto(id);
        return "redirect:/productos";
    }
}