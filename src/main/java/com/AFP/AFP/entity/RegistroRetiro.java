package com.AFP.AFP.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("RegistroRetiros")
@Data
@NoArgsConstructor
public class RegistroRetiro {
    @Id
    private String idRetiro;
    private String dni;
    private double montoRetiro;
    private String afp;
}
