package com.ecom.project.controller;

import com.ecom.project.config.AppConstants;
import com.ecom.project.model.Category;
import com.ecom.project.payload.CategoryDTO;
import com.ecom.project.payload.CategoryResponse;
import com.ecom.project.services.CategoryService;
import com.ecom.project.services.ServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
//    @GetMapping("/echo")
//    public ResponseEntity<String> messageEcho(@RequestParam(value = "message") String message){
//        return new ResponseEntity<>("Message echoed : "+message,HttpStatus.OK);
//    }
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber" , defaultValue =AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue =AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue =AppConstants.SORT_BY,required = false ) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue =AppConstants.SORT_ORDER,required = false ) String sortOrder
    ){
        CategoryResponse categoryList = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(categoryList,HttpStatus.OK);
    }
    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO>  createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO = categoryService.createCategories(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO,HttpStatus.CREATED);
    }
    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id){
            CategoryDTO deletedCategory = categoryService.deleteCategory(id);
            return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
    }
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO newCategoryDTO,@PathVariable Long categoryId){
            CategoryDTO savedCategory = categoryService.updateCategory(newCategoryDTO,categoryId);
            return new ResponseEntity<>(savedCategory, HttpStatus.OK);
    }
}
