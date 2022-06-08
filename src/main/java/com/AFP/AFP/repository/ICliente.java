package com.AFP.AFP.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.AFP.AFP.entity.Cliente;

public interface ICliente extends MongoRepository<Cliente, String> {
    public Cliente findByDni(String dni);
}

