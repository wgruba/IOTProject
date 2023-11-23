package com.example.springboot.Category;

import java.util.List;
import java.util.Optional;

import com.example.springboot.Category.Exceptions.CategoryIsNotParentCategory;
import com.example.springboot.Category.Exceptions.CategoryIsNotSubcategoryEx;
import com.example.springboot.Category.Exceptions.CategoryNotFoundEx;
import com.example.springboot.Category.Exceptions.CategoryExistsEx;
import com.example.springboot.Event.Event;
import com.example.springboot.User.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CategoryRepository extends MongoRepository<Category, String> {

    List<Category> findAll();
    User findById(int id);

    @Query("{'$or': [{'name': ?0}}")
    Optional<Category> findByNameOrMail(String nameOrMail);


   /* List<Category> getAllCategories();
    List<Category> getAllParentCategories();
    List<Category> getAllSubCategoriesofParentCategory(int id) throws CategoryNotFoundEx, CategoryIsNotParentCategory;
    List<Category> getSubscribedCategories(List<Integer> ids) throws CategoryNotFoundEx;
    List<Category> getEventCategories(List<Integer> ids) throws CategoryNotFoundEx;

    //CRUD
    Category getCategory(int id) throws CategoryNotFoundEx;
    Category getCategory(String name) throws CategoryNotFoundEx;
    boolean updateCategory(int id, String name, boolean isParentCategory, List<Integer> subcategories, int parentId) throws CategoryNotFoundEx;
    boolean deleteCategory(int id) throws CategoryNotFoundEx;
    boolean addCategory(int id, String name, boolean isParentCategory, List<Integer> subcategories, int parentId) throws CategoryExistsEx;
    boolean addCategory(Category category) throws CategoryExistsEx;


    //sub category and parent category management
    boolean isCategoryAParentCategory(int id) throws CategoryNotFoundEx;
    boolean makeNewCategoryConnection(int parentCategoryId, int subCategoryId) throws CategoryNotFoundEx, CategoryIsNotParentCategory;
    boolean deleteCategoryConnection(int parentCategoryId, int subCategoryId) throws CategoryIsNotSubcategoryEx, CategoryNotFoundEx;
*/
}
