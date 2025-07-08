package com.ecom.project.services;

import com.ecom.project.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void createCategories(Category category);

    String deleteCategory(Long id);

    Category updateCategory(Category newCategory, Long categoryId);
}
