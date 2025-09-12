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
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public Cliente findById(@PathVariable int id){
        return clienteService.findById(id);
    }

    @PostMapping
    public Cliente create(@RequestBody Cliente cliente){
        return clienteService.save(cliente);
    }

    @PutMapping("/{id}")
    public Cliente update(@RequestBody Cliente cliente, @PathVariable int id){
        cliente.setIdCliente(id);
        return clienteService.save(cliente);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        clienteService.deleteById(id);
    }

}
