package com.example.springboot.Category;

import com.example.springboot.Category.Exceptions.CategoryExistsEx;
import com.example.springboot.Category.Exceptions.CategoryIsNotParentCategory;
import com.example.springboot.Category.Exceptions.CategoryNotFoundEx;

import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository{
    public CategoryRepositoryImpl(){}
    @Override
    public List<Category> getAllCategories() {
        System.out.println("getAllCategory");
        //todo link to Agata's function: Categories Read bez_filtra
        return null;
    }

    @Override
    public List<Category> getAllParentCategories() {
        System.out.println("getAllParentCategories");
        //todo link to Agata's function: Categories Read filtr eq("isParentCategory" = true)
        return null;
    }

    @Override
    public List<Category> getAllSubCategoriesofParentCategory(int id) throws CategoryNotFoundEx, CategoryIsNotParentCategory {
        System.out.println("getAllSubCategoriesofParentCategory: " + id);
        //todo link to Agata's function: Categories Read filtr eq("parentCategory" = id)
        return null;
    }

    @Override
    public List<Category> getSubscribedCategories(List<Integer> ids) throws CategoryNotFoundEx {
        System.out.println("getSubscribedCategories: " + ids);
        //todo link to Agata's function: Categories Read filtr in("id", ids)
        return null;
    }

    @Override
    public List<Category> getEventCategories(List<Integer> ids) throws CategoryNotFoundEx {
        System.out.println("getEventCategories: " + ids);
        //todo link to Agata's function: Categories Read filtr in("id", ids)
        return null;
    }

    @Override
    public Category getCategory(int id) throws CategoryNotFoundEx {
        System.out.println("getCategory: " + id);
        //todo link to Agata's function: Categories Read filrt (id = id)
        return null;
    }

    @Override
    public Category getCategory(String name) throws CategoryNotFoundEx {
        System.out.println("getCategory: " + name);
        //todo link to Agata's function: Categories Read filrt (name = name)
        return null;
    }

    @Override
    public boolean updateCategory(int id,
                                  String name,
                                  boolean isParentCategory,
                                  List<Integer> subcategories,
                                  int parentId) throws CategoryNotFoundEx {
        System.out.println("updateCategory: " + id + " " + name + " " + isParentCategory + " " + subcategories + " " + parentId);
        //todo link to Agata's function: Categories Update filtr eq("id" = id)
        return false;
    }

    @Override
    public boolean deleteCategory(int id) throws CategoryNotFoundEx {
        System.out.println("deleteCategory: " + id);
        //todo link to Agata's function: Categories Delete filtr eq("id" = id)
        return false;
    }

    @Override
    public boolean addCategory(int id,
                               String name,
                               boolean isParentCategory,
                               List<Integer> subcategories,
                               int parentId) throws CategoryExistsEx {
        System.out.println("addCategory: " + id + " " + name + " " + isParentCategory + " " + subcategories + " " + parentId);
        //todo link to Agata's function: Categories Create bez_filtra
        return false;
    }

    @Override
    public Category addCategory(Category category) throws CategoryExistsEx {
        System.out.println("addCategory: " + category);
        //todo link to Agata's function: Categories Create bez_filtra
        return null;
    }

    @Override
    public boolean isCategoryAParentCategory(int id) throws CategoryNotFoundEx {
        System.out.println("isCategoryAParentCategory" + id);
        Category tempCategory = getCategory(id);
        return tempCategory.isParentCategory();
    }

    @Override
    public boolean makeNewCategoryConnection(int parentCategoryId, int subCategoryId) throws CategoryNotFoundEx {
        System.out.println("makeNewCategoryConnection: " + parentCategoryId + " " + subCategoryId);
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
    public boolean deleteCategoryConnection(int parentCategoryId, int subCategoryId) throws CategoryNotFoundEx {
        System.out.println("deleteCategoryConnection: " + parentCategoryId + " " + subCategoryId);
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
