package com.example.javaexpress.controller;

import com.example.javaexpress.model.model.Cliente;
import com.example.javaexpress.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    List<Cliente> all(){
        return clienteService.listarClientes();
    }

    @GetMapping("/{id}")
    public Cliente findById(@PathVariable int id){
        return clienteService.buscarPorId(id);
    }

    @PostMapping
    public Cliente create(@RequestBody Cliente cliente){
        return clienteService.registrarCliente(cliente);
    }

    @PutMapping("/{id}")
    public Cliente update(@RequestBody Cliente cliente, @PathVariable int id){
        cliente.setId(id);
        return clienteService.atualizarCliente(cliente);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        clienteService.excluirCliente(id);
    }

}
