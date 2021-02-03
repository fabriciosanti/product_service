package com.abinbev.repository;

import com.abinbev.document.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByNameIgnoreCaseContaining(String name);
}