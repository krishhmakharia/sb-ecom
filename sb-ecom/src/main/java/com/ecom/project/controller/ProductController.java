package com.ecom.project.controller;

import com.ecom.project.model.Product;
import com.ecom.project.payload.ProductDTO;
import com.ecom.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody Product product , @PathVariable Long categoryId){
        ProductDTO productDTO = productService.saveProduct(product,categoryId);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

}
