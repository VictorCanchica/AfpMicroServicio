package com.AFP.AFP.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import com.AFP.AFP.entity.DataAfpCliente;

public interface IDataAfpCliente extends MongoRepository<DataAfpCliente, String> {
    public DataAfpCliente findByDni(String dni);
}