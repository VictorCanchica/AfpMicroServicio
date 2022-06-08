package com.AFP.AFP.controllers;

import com.AFP.AFP.entity.Cliente;
import com.AFP.AFP.repository.ICliente;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/General/Client")

public class ClienteController {
    @Autowired
    private ICliente icliente;

    //Instancia de Slf4j para esta clase
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> getAllClients() {
        LOGGER.info("Hizo la petición de listado");
        return icliente.findAll();
    }



    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public String createClient(@RequestBody Cliente cliente) {
        var existente= getClienteByDni(cliente.getDni());
        if (existente==null){
            icliente.save(cliente);
            LOGGER.info("Hizo la petición de add");
            return "Cliente Agregado de forma exitosa";
        }
        else{
            LOGGER.info("Cliente ya existe en el Sistema, no se completa la peticion");
            return "Cliente ya existe en el Sistema";
        }
    }


    @GetMapping("/getById/{idCliente}")
    public ResponseEntity<Cliente> getClient(@PathVariable(value = "idCliente") String idCliente) {
        LOGGER.info("Hizo la petición de obtener por Id");
        Optional<Cliente> cliente = icliente.findById(idCliente);
        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(cliente.get(), HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/delete/{idCliente}")
    public String deleteClient(@PathVariable(value = "idCliente") String idCliente) {
        LOGGER.info("Hizo la petición eliminar por Id");
        icliente.deleteById(idCliente);
        return "Se eliminó correctamente el cliente";
    }

    @PutMapping("/update/{idCliente}")
    public Cliente updateClient(@RequestBody Cliente cliente, @PathVariable(value = "idCliente") String idCliente) {
        LOGGER.info("Hizo la petición actualizar por Id");
        cliente.setIdCliente(idCliente);
        icliente.save(cliente);
        return cliente;
    }
    public Cliente getClienteByDni(String dni) {
        LOGGER.info("Hizo la petición de obtener por dni");

        return icliente.findByDni(dni);
    }

}
