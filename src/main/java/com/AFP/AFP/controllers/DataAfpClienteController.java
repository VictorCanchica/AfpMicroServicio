package com.AFP.AFP.controllers;

import com.AFP.AFP.entity.DataAfpCliente;
import com.AFP.AFP.repository.ICliente;
import com.AFP.AFP.repository.IDataAfpCliente;
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
@RequestMapping("/General/DataAfp")

public class DataAfpClienteController {

    @Autowired
    private ICliente icliente;
    @Autowired
    private IDataAfpCliente idata;

    //Instancia de Slf4j para esta clase
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<DataAfpCliente> getAllAfpData() {
        LOGGER.info("Hizo la petición de listado");
        return idata.findAll();
    }


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public String createAfpData(@RequestBody DataAfpCliente dataAfpCliente) {
        var existente=idata.findByDni(dataAfpCliente.getDni());
        if (existente==null){

            var existeCliente=icliente.findByDni(dataAfpCliente.getDni());
            if (existeCliente!=null){
                idata.save(dataAfpCliente);
                LOGGER.info("Hizo la petición de add");
                return "Data Agregada de forma exitosa";
            }
            else {
                return "Cliente no existe";
            }
        }
        else{
            LOGGER.info("Data del cliente ya existe en el Sistema, no se completa la peticion");
            return "Data del cliente ya existe en el Sistema";
        }
    }

    @GetMapping("/getById/{idData}")
    public ResponseEntity<DataAfpCliente> getDataClient(@PathVariable(value = "idData") String idData) {
        LOGGER.info("Hizo la petición de obtener por Id");
        Optional<DataAfpCliente> dataAfpCliente = idata.findById(idData);
        return dataAfpCliente.map(afpCliente -> new ResponseEntity<>(afpCliente, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(dataAfpCliente.get(), HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{idData}")
    public String deleteData(@PathVariable(value = "idData") String idData) {
        LOGGER.info("Hizo la petición eliminar por Id");
        idata.deleteById(idData);
        return "Se eliminó correctamente la Data del Cliente";
    }

    @PutMapping("/update/{idData}")
    public DataAfpCliente updateData(@RequestBody DataAfpCliente dataAfpCliente, @PathVariable(value = "idData") String idData) {
        LOGGER.info("Hizo la petición actualizar por Id");
        dataAfpCliente.setIdData(idData);
        idata.save(dataAfpCliente);
        return dataAfpCliente;
    }
    public DataAfpCliente getDataAfpClienteByDni(String dni) {
        LOGGER.info("Hizo la petición de obtener por dni");

        return idata.findByDni(dni);
    }

}
