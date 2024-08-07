package com.spring_boot.Servicio;

import com.spring_boot.Entidad.Cliente;
import com.spring_boot.Repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public List<Cliente> listaClientes() {
        return clienteRepositorio.findAll();
    }

    public Optional<Cliente> buscarClientePorId(Long id) {
        return clienteRepositorio.findById(id);
    }

    public void guardarCliente(Cliente cliente) {
        clienteRepositorio.save(cliente);
    }

    public void eliminarCliente(Long id) {
        clienteRepositorio.deleteById(id);
    }
}
