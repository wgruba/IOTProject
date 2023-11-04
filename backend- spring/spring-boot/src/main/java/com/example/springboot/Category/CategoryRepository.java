package com.example.springboot.Category;

import java.util.List;

import com.example.springboot.Category.Exceptions.CategoryIsNotParentCategory;
import com.example.springboot.Category.Exceptions.CategoryIsNotSubcategoryEx;
import com.example.springboot.Category.Exceptions.CategoryNotFoundEx;
import com.example.springboot.Category.Exceptions.CategoryExistsEx;

public interface CategoryRepository {
    List<Category> getAllCategories();
    List<Category> getAllParentCategories();
    List<Category> getAllSubCategoriesofParentCategory(int id) throws CategoryNotFoundEx, CategoryIsNotParentCategory;
    List<Category> getSubscribedCategories(List<Integer> ids) throws CategoryNotFoundEx;
    List<Category> getEventCategories(List<Integer> ids) throws CategoryNotFoundEx;

    //CRUD
    Category getCategory(int id) throws CategoryNotFoundEx;
    Category getCategory(String name) throws CategoryNotFoundEx;
    Category updateCategory(int id, String name, boolean isParentCategory, List<Integer> subcategories, int parentId) throws CategoryNotFoundEx;
    boolean deleteCategory(int id) throws CategoryNotFoundEx;
    Category addCategory(int id, String name, boolean isParentCategory, List<Integer> subcategories, int parentId) throws CategoryExistsEx;
    Category addCategory(Category category) throws CategoryExistsEx;


    //sub category and parent category management
    boolean isCategoryAParentCategory(int id) throws CategoryNotFoundEx;
    boolean makeNewCategoryConnection(int parentCategoryId, int subCategoryId) throws CategoryNotFoundEx, CategoryIsNotParentCategory;
    boolean deleteCategoryConnection(int parentCategoryId, int subCategoryId) throws CategoryIsNotSubcategoryEx, CategoryNotFoundEx;

}
