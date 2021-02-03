package com.abinbev.service;

import com.abinbev.document.Product;

import java.util.List;

public interface ProductService {

    List<Product> listAll();

    Product listById(String id);

    List<Product> listByName(String name);

    Product add(Product product);

    Product update(Product product);

    void remove(String id);
}
