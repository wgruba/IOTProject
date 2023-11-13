package com.example.springboot.Category;

import com.example.springboot.Category.Exceptions.CategoryExistsEx;
import com.example.springboot.Category.Exceptions.CategoryIsNotParentCategory;
import com.example.springboot.Category.Exceptions.CategoryNotFoundEx;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class CategoryController {

    private final CategoryRepository impl = new CategoryRepositoryImpl();

    @GetMapping("categories")
    public EntityModel<List<Category>> getAllCategories(){
        return EntityModel.of(impl.getAllCategories());
    }

    @GetMapping("categories/parentCategories")
    public EntityModel<List<Category>> getPArentCategories(){
        return EntityModel.of(impl.getAllParentCategories());
    }
    @GetMapping("categories/{categoryId}/subcategories")
    public EntityModel<List<Category>> getSubcategories(@PathVariable int categoryId){
        try {
            return EntityModel.of(impl.getAllSubCategoriesofParentCategory(categoryId));
        } catch (CategoryNotFoundEx e) {
            throw new RuntimeException(e);
        } catch (CategoryIsNotParentCategory e) {
            throw new RuntimeException(e);
        }
    }

    public List<Category> getUsersSubscribedCategories(List<Integer> usersEventList) {
        try {
            return impl.getSubscribedCategories(usersEventList);
        } catch (CategoryNotFoundEx e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean createCategory(int id,
                                  String name,
                                  boolean isParentCategory,
                                  List<Integer> subcategories,
                                  int parentId) {
        try {
            return impl.addCategory(id, name, isParentCategory, subcategories, parentId);
        } catch (CategoryExistsEx e) {
            throw new RuntimeException(e);
        }
    }
}
