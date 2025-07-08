package com.ecom.project.services;

import com.ecom.project.model.Category;
import com.ecom.project.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceImpl implements CategoryService{

//    List<Category> categories = new ArrayList<>();
    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public List<Category> getAllCategories() {
//        return categories;
        return categoryRepo.findAll();
    }

    @Override
    public void createCategories(Category category) {

//        categories.add(category);

        try {
            categoryRepo.save(category);
        } catch (ObjectOptimisticLockingFailureException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String deleteCategory(Long id) {
//        List<Category> categories = categoryRepo.findAll();
//
//        Category category = categories.stream()
//                .filter(c -> c.getCategoryId().equals(id))
//                .findFirst().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Response not found"));
//
////        categories.remove(category);
//            categoryRepo.delete(category);
//        return "Category "+id+" Successfully Deleted !!";
        Optional<Category> savedCategoryOptional = categoryRepo.findById(id);
        Category savedCategory = savedCategoryOptional.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not such RESOURCE PRESENT!!"));
        categoryRepo.delete(savedCategory);
        return "Category "+id+" Successfully Deleted !!";
    }

    @Override
    public Category updateCategory(Category newCategory, Long categoryId) {
//        List<Category> categories = categoryRepo.findAll();
//        Optional<Category> optionalCategory = categories.stream()
//                .filter(c-> c.getCategoryId().equals(categoryId))
//                .findFirst();
//        if(optionalCategory.isPresent()){
//            Category existingCategory = optionalCategory.get();
//            existingCategory.setCategoryName(newCategory.getCategoryName());
//            Category savedCategory = categoryRepo.save(existingCategory);
//            return savedCategory;
//        }else{
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not found");
//        }
        Optional<Category> savedCategoryOptional = categoryRepo.findById(categoryId);
        Category savedCategory = savedCategoryOptional.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not Found!"));

        newCategory.setCategoryId(categoryId);
        savedCategory=categoryRepo.save(newCategory);
        return savedCategory;
    }

}
