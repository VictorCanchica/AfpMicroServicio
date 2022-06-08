package com.AFP.AFP.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Clientes")
@Data
@NoArgsConstructor
public class Cliente {
    @Id
    private String idCliente;
    private String dni;
    private String nombre;
    private String apellidos;
    private int telefono;
    private String correo;
    private String afp;
}
