package com.AFP.AFP.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.AFP.AFP.entity.RegistroRetiro;

public interface IRegistroRetiro extends MongoRepository<RegistroRetiro, String> {
    public RegistroRetiro findByDni(String dni);
}