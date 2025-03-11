package com.test.product_service.service;

import com.test.product_service.model.Product;

import java.util.List;

public interface ProductService {

public List<Product> getAll();
public Product single(Long id);
public Product add(Product product);
public Product update(Product product);
public void remove(Long id);



}
