package com.ecom.project.controller;

import com.ecom.project.model.Category;
import com.ecom.project.services.CategoryService;
import com.ecom.project.services.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categoryList = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryList,HttpStatus.OK);
    }
    @PostMapping("/public/categories")
    public ResponseEntity<String>  createCategory(@RequestBody Category category){
        categoryService.createCategories(category);
        return new ResponseEntity<>("Category Created",HttpStatus.CREATED);
    }
    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        try{
            String status = categoryService.deleteCategory(id);
            return new ResponseEntity<>(status, HttpStatus.OK);
        }catch(ResponseStatusException ex){
            return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
        }
    }
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody Category newCategory,@PathVariable Long categoryId){
        try{
            Category savedCategory = categoryService.updateCategory(newCategory,categoryId);
            return new ResponseEntity<>("Updated category id : "+categoryId+" Successfully !!", HttpStatus.OK);
        }catch(ResponseStatusException ex){
            return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
        }
    }
}
