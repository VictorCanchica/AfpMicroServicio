package com.AFP.AFP.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("DataAfp")
@Data
@NoArgsConstructor
public class DataAfpCliente {
    @Id
    private String idData;
    private String dni;
    private String nombre;
    private double montoDisponible;
    private String fechaRetiro;
    private String nroCuenta;
}
