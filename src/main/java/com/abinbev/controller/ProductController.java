package com.abinbev.controller;

import com.abinbev.document.Product;
import com.abinbev.response.Response;
import com.abinbev.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Response<List<Product>>> listAll() {
        List<Product> products = productService.listAll();

        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(new Response<>(products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Product>> getById(@PathVariable String id) {
        Product product = this.productService.listById(id);

        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new Response<>(product));
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<Response<List<Product>>> getByName(@PathVariable String name) {
        var product = this.productService.listByName(name);

        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new Response<>(product));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Response<Product>> add(@RequestBody Product product) {

        return new ResponseEntity<>(new Response<>(this.productService.add(product)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Product>> update(@PathVariable String id, @RequestBody Product product) {

        if (this.productService.listById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        product.setId(id);
        return ResponseEntity.ok(new Response<>(this.productService.update(product)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Integer>> remove(@PathVariable String id) {
        if (this.productService.listById(id) == null) {
            return new ResponseEntity<>(new Response<>("No product found for id: " + id), HttpStatus.NOT_FOUND);
        }

        productService.remove(id);
        return ResponseEntity.ok(new Response<>("Product deleted!"));
    }
}
