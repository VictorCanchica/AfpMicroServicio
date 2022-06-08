package com.AFP.AFP.controllers;

import com.AFP.AFP.entity.RegistroRetiro;
import com.AFP.AFP.repository.ICliente;
import com.AFP.AFP.repository.IDataAfpCliente;
import com.AFP.AFP.repository.IRegistroRetiro;
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
@RequestMapping("/General/RegistroRetiro")

public class RegistroRetiroController {

    @Autowired
    private ICliente icliente;
    @Autowired
    private IDataAfpCliente idata;
    @Autowired
    private IRegistroRetiro iregistro;

    //Instancia de Slf4j para esta clase
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<RegistroRetiro> getAllRecords() {
        LOGGER.info("Hizo la petición de listado");
        return iregistro.findAll();
    }


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public String createRecord(@RequestBody RegistroRetiro registroRetiro) {
        var existente=iregistro.findByDni(registroRetiro.getDni());

        if (existente==null){
            var afp=(icliente.findByDni(registroRetiro.getDni())).getAfp();
            if (afp!=null){
                registroRetiro.setAfp((icliente.findByDni(registroRetiro.getDni())).getAfp());

                var monto=idata.findByDni(registroRetiro.getDni()).getMontoDisponible();
                if (monto>0){
                    if (monto<registroRetiro.getMontoRetiro()){
                        return "No se puede registrar la solicitud. Monto mayor que el permitido";
                    } else if (monto/2>registroRetiro.getMontoRetiro()) {
                        return "Monto mínimo no cubierto por favor revise el monto mínimo a retirar \nMonto minimo:"+monto/2;
                    }
                }
            }else{
                return "cliente no esta afiliado a ninguna AFP";
            }
            iregistro.save(registroRetiro);
            LOGGER.info("Hizo la petición de add");
            return "Registro Agregado de forma exitosa";
        }
        else{
            LOGGER.info("Registro ya existe en el Sistema, no se completa la peticion");
            return "Registro ya existe en el Sistema";
        }
    }

    @GetMapping("/getById/{idRetiro}")
    public ResponseEntity<RegistroRetiro> getRecord(@PathVariable(value = "idRetiro") String idRetiro) {
        LOGGER.info("Hizo la petición de obtener por Id");
        Optional<RegistroRetiro> registroRetiro = iregistro.findById(idRetiro);
        return registroRetiro.map(afpCliente -> new ResponseEntity<>(afpCliente, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(registroRetiro.get(), HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{idRetiro}")
    public String deleteRecord(@PathVariable(value = "idData") String idRetiro) {
        LOGGER.info("Hizo la petición eliminar por Id");
        iregistro.deleteById(idRetiro);
        return "Se eliminó correctamente la Data del Cliente";
    }

    @PutMapping("/update/{idRetiro}")
    public RegistroRetiro updateRecord(@RequestBody RegistroRetiro registroRetiro, @PathVariable(value = "idRetiro") String idRetiro) {
        LOGGER.info("Hizo la petición actualizar por Id");
        registroRetiro.setIdRetiro(idRetiro);
        iregistro.save(registroRetiro);
        return registroRetiro;
    }
    public RegistroRetiro getRecordByDni(String dni) {
        LOGGER.info("Hizo la petición de obtener por dni");

        return iregistro.findByDni(dni);
    }

}
