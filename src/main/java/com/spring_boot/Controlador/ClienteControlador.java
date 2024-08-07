package com.spring_boot.Controlador;

import com.spring_boot.Entidad.Cliente;
import com.spring_boot.Entidad.Producto;
import com.spring_boot.Servicio.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteServicio;

    // Mostrar todos los clientes
    @GetMapping("/clientes")
    public String mostrarClientes(Model model) {
        List<Cliente> clientes = clienteServicio.listaClientes();
        model.addAttribute("clientes", clientes);
        return "Producto/vistaClientes";
    }

    // Buscar clientes por ID
    @GetMapping("/clientes/buscar")
    public String buscarClientes(@RequestParam Long id, Model model) {
        Optional<Cliente> cliente = clienteServicio.buscarClientePorId(id);
        model.addAttribute("clientes", cliente.isPresent() ? List.of(cliente.get()) : List.of());
        return "Producto/vistaClientes";
    }

    // Mostrar productos asociados a un cliente
    @GetMapping("/clientes/{clienteID}/productos")
    public String mostrarProductosByCliente(@PathVariable Long clienteID, Model model) {
        Optional<Cliente> cliente = clienteServicio.buscarClientePorId(clienteID);
        if (cliente.isPresent()) {
            List<Producto> productos = cliente.get().getProductos();
            model.addAttribute("productos", productos);
            model.addAttribute("clienteID", clienteID);
            return "Producto/vistaProductosCliente";
        } else {
            model.addAttribute("error", "Cliente no encontrado");
            return "Producto/vistaClientes";
        }
    }

    // Mostrar el formulario para crear o editar un cliente
    @GetMapping("/formularioCliente")
    public String mostrarFormularioCliente(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            Optional<Cliente> cliente = clienteServicio.buscarClientePorId(id);
            if (cliente.isPresent()) {
                model.addAttribute("cliente", cliente.get());
            } else {
                model.addAttribute("cliente", new Cliente());
            }
        } else {
            model.addAttribute("cliente", new Cliente());
        }
        return "Producto/formularioCliente";
    }

    // Crear o actualizar un cliente
    @PostMapping("/guardarCliente")
    public String guardarCliente(Cliente cliente) {
        clienteServicio.guardarCliente(cliente);
        return "redirect:/clientes";
    }

    // Editar cliente
    @GetMapping("/clientes/{clienteID}/editar")
    public String editarCliente(@PathVariable Long clienteID, Model model) {
        Optional<Cliente> cliente = clienteServicio.buscarClientePorId(clienteID);
        if (cliente.isPresent()) {
            model.addAttribute("cliente", cliente.get());
            return "Producto/formularioCliente";
        } else {
            model.addAttribute("error", "Cliente no encontrado");
            return "redirect:/clientes";
        }
    }

    // Eliminar cliente
    @GetMapping("/eliminarCliente/{clienteID}")
    public String eliminarCliente(@PathVariable Long clienteID) {
        clienteServicio.eliminarCliente(clienteID);
        return "redirect:/clientes";
    }
}