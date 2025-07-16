package com.ecom.project.services;

import com.ecom.project.model.Product;
import com.ecom.project.payload.ProductDTO;

public interface ProductService {
    ProductDTO saveProduct(Product product, Long categoryId);
}
