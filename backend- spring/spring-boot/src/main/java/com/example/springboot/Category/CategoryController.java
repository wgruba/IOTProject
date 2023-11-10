package com.example.springboot.Category;

import com.example.springboot.Category.Exceptions.CategoryNotFoundEx;

import java.util.List;

public class CategoryController {

    private final CategoryRepository impl = new CategoryRepositoryImpl();
    public List<Category> getUsersSubscribedCategories(List<Integer> usersEventList) throws CategoryNotFoundEx {
        return impl.getSubscribedCategories(usersEventList);
    }
}
