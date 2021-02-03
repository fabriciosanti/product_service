package com.abinbev.service.impl;

import com.abinbev.document.Product;
import com.abinbev.repository.ProductRepository;
import com.abinbev.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> listAll() {
        return productRepository.findAll();
    }

    @Override
    public Product listById(String id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()){
            return null;
        }
        return product.get();
    }

    @Override
    public List<Product> listByName(String name) {
        return productRepository.findByNameIgnoreCaseContaining(name);
    }

    @Override
    public Product add(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void remove(String id) {
        productRepository.deleteById(id);
    }
}
