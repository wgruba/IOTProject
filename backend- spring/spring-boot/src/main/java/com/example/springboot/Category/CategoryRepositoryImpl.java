package com.example.springboot.Category;

import com.example.springboot.Category.Exceptions.CategoryExistsEx;
import com.example.springboot.Category.Exceptions.CategoryIsNotParentCategory;
import com.example.springboot.Category.Exceptions.CategoryIsNotSubcategoryEx;
import com.example.springboot.Category.Exceptions.CategoryNotFoundEx;

import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository{
    public CategoryRepositoryImpl(){}
    @Override
    public List<Category> getAllCategories() {
        //todo podlinkować do funkcji Agaty: Categories Read bez_filtra
        return null;
    }

    @Override
    public List<Category> getAllParentCategories() {
        //todo podlinkować do funkcji Agaty: Categories Read filtr eq("isParentCategory" = true)
        return null;
    }

    @Override
    public List<Category> getAllSubCategoriesofParentCategory(int id) throws CategoryNotFoundEx, CategoryIsNotParentCategory {
        //todo podlinkować do funkcji Agaty: Categories Read filtr eq("parentCategory" = id)
        return null;
    }

    @Override
    public List<Category> getSubscribedCategories(List<Integer> ids) throws CategoryNotFoundEx {
        //todo podlinkować do funkcji Agaty: Categories Read filtr in("id", ids)
        return null;
    }

    @Override
    public List<Category> getEventCategories(List<Integer> ids) throws CategoryNotFoundEx {
        //todo podlinkować do funkcji Agaty: Categories Read filtr in("id", ids)
        return null;
    }

    @Override
    public Category getCategory(int id) throws CategoryNotFoundEx {
        //todo podlinkować do funkcji Agaty: Categories Read filrt (id = id)
        return null;
    }

    @Override
    public Category getCategory(String name) throws CategoryNotFoundEx {
        //todo podlinkować do funkcji Agaty: Categories Read filrt (name = name)
        return null;
    }

    @Override
    public Category updateCategory(int id,
                                   String name,
                                   boolean isParentCategory,
                                   List<Integer> subcategories,
                                   int parentId) throws CategoryNotFoundEx {
        //todo podlinkować do funkcji Agaty: Categories Update filtr eq("id" = id)
        return null;
    }

    @Override
    public boolean deleteCategory(int id) throws CategoryNotFoundEx {
        //todo podlinkować do funkcji Agaty: Categories Delete filtr eq("id" = id)
        return false;
    }

    @Override
    public Category addCategory(int id,
                                String name,
                                boolean isParentCategory,
                                List<Integer> subcategories,
                                int parentId) throws CategoryExistsEx {
        //todo podlinkować do funkcji Agaty: Categories Create bez_filtra
        return null;
    }

    @Override
    public Category addCategory(Category category) throws CategoryExistsEx {
        //todo podlinkować do funkcji Agaty: Categories Create bez_filtra
        return null;
    }

    @Override
    public boolean isCategoryAParentCategory(int id) throws CategoryNotFoundEx {
        Category tempCategory = getCategory(id);
        return tempCategory.isParentCategory();
    }

    @Override
    public boolean makeNewCategoryConnection(int parentCategoryId, int subCategoryId) throws CategoryNotFoundEx {
        Category tempCategory = getCategory(parentCategoryId);
        List<Integer> tempList = tempCategory.getSubcategories();
        tempList.add(subCategoryId);

        updateCategory(tempCategory.get_id(),
                tempCategory.getName(),
                tempCategory.isParentCategory(),
                tempList,
                tempCategory.getParentId());


        tempCategory = getCategory(subCategoryId);

        updateCategory(tempCategory.get_id(),
                tempCategory.getName(),
                false,
                tempCategory.getSubcategories(),
                parentCategoryId);

        return true;
    }

    @Override
    public boolean deleteCategoryConnection(int parentCategoryId, int subCategoryId) throws CategoryIsNotSubcategoryEx, CategoryNotFoundEx {
        Category tempCategory = getCategory(parentCategoryId);
        List<Integer> tempList = tempCategory.getSubcategories();
        tempList.remove(subCategoryId);

        updateCategory(tempCategory.get_id(),
                tempCategory.getName(),
                tempCategory.isParentCategory(),
                tempList,
                tempCategory.getParentId());


        tempCategory = getCategory(subCategoryId);

        updateCategory(tempCategory.get_id(),
                tempCategory.getName(),
                true,
                tempCategory.getSubcategories(),
                0);

        return true;
    }
}
