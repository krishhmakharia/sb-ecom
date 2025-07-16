package com.ecom.project.services;

import com.ecom.project.config.AppConfig;
import com.ecom.project.exceptions.APIException;
import com.ecom.project.exceptions.EmptyDataException;
import com.ecom.project.exceptions.ResourceNotFoundException;
import com.ecom.project.model.Category;
import com.ecom.project.payload.CategoryDTO;
import com.ecom.project.payload.CategoryResponse;
import com.ecom.project.repositories.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder) {
        //Sort type
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        //Pagination
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Category> categoryPage = categoryRepo.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();
        if (categories.isEmpty())
            throw new APIException("No Categories exists");
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        //Page info
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalPage(categoryPage.getTotalPages());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategories(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO,Category.class);//map Category-->DTO entity
        Category categoryFromDB = categoryRepo.findByCategoryName(category.getCategoryName());
        if (categoryFromDB != null)
            throw new APIException("Category with Name \"" + category.getCategoryName() + "\" is already exists!!");
        Category savedCategory = categoryRepo.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);//map Category entity-->DTO Class
    }

    @Override
    public CategoryDTO deleteCategory(Long id) {
        Optional<Category> savedCategoryOptional = categoryRepo.findById(id);
        Category savedCategory = savedCategoryOptional.orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",id));
        categoryRepo.delete(savedCategory);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO newCategoryDTO, Long categoryId) {

        Optional<Category> savedCategoryOptional = categoryRepo.findById(categoryId);
        Category savedCategory = savedCategoryOptional.orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        Category category = modelMapper.map(newCategoryDTO,Category.class);
        category.setCategoryId(categoryId);
        savedCategory = categoryRepo.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

}
