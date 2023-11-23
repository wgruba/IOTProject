/*
package com.example.springboot.Category;

import com.example.springboot.Category.Exceptions.CategoryExistsEx;
import com.example.springboot.Category.Exceptions.CategoryIsNotParentCategory;
import com.example.springboot.Category.Exceptions.CategoryIsNotSubcategoryEx;
import com.example.springboot.Category.Exceptions.CategoryNotFoundEx;
import com.example.springboot.Database.CRUDAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.bson.conversions.Bson;

import java.util.List;


public class CategoryRepositoryImpl implements CategoryRepository {

    private final String USERNAME = "Agata";
    private final String PASSWORD = "haslo";
    private final String DATABASE = "bazadanych";

    @Autowired
    MongoTemplate mongoTemplate;

    public CategoryRepositoryImpl() {
    }

    @Override
    public List<Category> getAllCategories() {
        return null;
    }

    @Override
    public List<Category> getAllParentCategories() {
        return null;
    }

    @Override
    public List<Category> getAllSubCategoriesofParentCategory(int id) throws CategoryNotFoundEx, CategoryIsNotParentCategory {
        return null;
    }

    @Override
    public List<Category> getSubscribedCategories(List<Integer> ids) throws CategoryNotFoundEx {
        return null;
    }

    @Override
    public List<Category> getEventCategories(List<Integer> ids) throws CategoryNotFoundEx {
        return null;
    }

    @Override
    public Category getCategory(int id) throws CategoryNotFoundEx {
        return null;
    }

    @Override
    public Category getCategory(String name) throws CategoryNotFoundEx {
        return null;
    }

    @Override
    public boolean updateCategory(int id, String name, boolean isParentCategory, List<Integer> subcategories, int parentId) throws CategoryNotFoundEx {
        return false;
    }

    @Override
    public boolean deleteCategory(int id) throws CategoryNotFoundEx {
        return false;
    }

    @Override
    public boolean addCategory(int id, String name, boolean isParentCategory, List<Integer> subcategories, int parentId) throws CategoryExistsEx {
        return false;
    }

    @Override
    public boolean addCategory(Category category) throws CategoryExistsEx {
        return false;
    }

    @Override
    public boolean isCategoryAParentCategory(int id) throws CategoryNotFoundEx {
        return false;
    }

    @Override
    public boolean makeNewCategoryConnection(int parentCategoryId, int subCategoryId) throws CategoryNotFoundEx, CategoryIsNotParentCategory {
        return false;
    }

    @Override
    public boolean deleteCategoryConnection(int parentCategoryId, int subCategoryId) throws CategoryIsNotSubcategoryEx, CategoryNotFoundEx {
        return false;
    }

   */
/* @Override
    public List<Category> getAllCategories() {
        System.out.println("getAllCategory");

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        return dc.CategoryRead(CRUDAmount.MANY, df.allEntries());
    }

    @Override
    public List<Category> getAllParentCategories() {
        System.out.println("getAllParentCategories");

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        return dc.CategoryRead(CRUDAmount.MANY, df.ensureParent());
    }

    @Override
    public List<Category> getAllSubCategoriesofParentCategory(int id) throws CategoryNotFoundEx, CategoryIsNotParentCategory {
        System.out.println("getAllSubCategoriesofParentCategory: " + id);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        return dc.CategoryRead(CRUDAmount.MANY, df.findThroughParentId(id));
    }

    @Override
    public List<Category> getSubscribedCategories(List<Integer> ids) throws CategoryNotFoundEx {
        System.out.println("getSubscribedCategories: " + ids);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        return dc.CategoryRead(CRUDAmount.MANY, df.findAllIds(ids));
    }

    @Override
    public List<Category> getEventCategories(List<Integer> ids) throws CategoryNotFoundEx {
        System.out.println("getEventCategories: " + ids);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        return dc.CategoryRead(CRUDAmount.MANY, df.findAllIds(ids));
    }

    @Override
    public Category getCategory(int id) throws CategoryNotFoundEx {
        System.out.println("getCategory: " + id);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        Category found = dc.CategoryRead(df.findById(id)).get(0);
        if (found == null) {
            found = dc.CategoryRead(df.findSubcategory(id)).get(0);
        }
        return found;
    }

    @Override
    public Category getCategory(String name) throws CategoryNotFoundEx {
        System.out.println("getCategory: " + name);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        Category found = dc.CategoryRead(df.findByName(name)).get(0);
        if (found == null) {
            found = dc.CategoryRead(df.findSubcategoryName(name)).get(0);
        }
        return found;
    }

    @Override
    public boolean updateCategory(int id,
                                  String name,
                                  boolean isParentCategory,
                                  List<Integer> subcategories,
                                  int parentId) throws CategoryNotFoundEx {
        System.out.println("updateCategory: " + id + " " + name + " " + isParentCategory + " " + subcategories + " " + parentId);

        // isParentCategory will always be true for a topmost category and always false for a subcategory, does not need updating
        // problem - this representation of subcategories does not work with the database, a list of Categories is needed
        // parentId will always be null for the topmost category and static for sub-categories, so it can be skipped

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        DatabaseUpdater du = new DatabaseUpdater();

        if (isParentCategory) {
            return dc.CategoryCUD(new Bson[]{df.findById(id)}, new Bson[]{du.updateName(name)});
        } else {
            Bson filter = df.andFilter(
                    df.findById(parentId),
                    df.findSubcategory(id)
            );
            return dc.CategoryCUD(new Bson[]{filter}, new Bson[]{du.updateSubcategoryName(name)});
        }
    }

    @Override
    public boolean deleteCategory(int id) throws CategoryNotFoundEx {
        System.out.println("deleteCategory: " + id);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        DatabaseFilters df = new DatabaseFilters();
        Bson filters = df.findById(id);
        if (dc.CategoryRead(filters) != null) {
            return dc.CategoryCUD(new Bson[]{filters});
        } else {
            DatabaseUpdater du = new DatabaseUpdater();
            return dc.CategoryCUD(new Bson[]{df.findSubcategory(id)}, new Bson[]{du.removeSubcategory(id)});
        }
    }

    @Override
    public boolean addCategory(int id,
                               String name,
                               boolean isParentCategory,
                               List<Integer> subcategories,
                               int parentId) throws CategoryExistsEx {
        System.out.println("addCategory: " + id + " " + name + " " + isParentCategory + " " + subcategories + " " + parentId);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        Category new_cat = new Category(id, name, isParentCategory, parentId);

        if (isParentCategory) {
            return dc.CategoryCUD(new Category[]{new_cat});
        } else {
            DatabaseUpdater du = new DatabaseUpdater();
            DatabaseFilters df = new DatabaseFilters();
            return dc.CategoryCUD(new Bson[]{df.findById(parentId)}, new Bson[]{du.addSubcategory(new_cat)});
        }
    }

    @Override
    public boolean addCategory(Category category) throws CategoryExistsEx {
        System.out.println("addCategory: " + category);

        DatabaseConnection dc = new DatabaseConnection(USERNAME, PASSWORD, DATABASE);
        if (category.isParentCategory()) {
            return dc.CategoryCUD(new Category[]{category});
        } else {
            DatabaseUpdater du = new DatabaseUpdater();
            DatabaseFilters df = new DatabaseFilters();
            return dc.CategoryCUD(
                    new Bson[]{df.findById(category.getParentId())},
                    new Bson[]{du.addSubcategory(category)}
            );
        }
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

        updateCategory(tempCategory.getId(),
                tempCategory.getName(),
                tempCategory.isParentCategory(),
                tempList,
                tempCategory.getParentId());


        tempCategory = getCategory(subCategoryId);

        updateCategory(tempCategory.getId(),
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

        updateCategory(tempCategory.getId(),
                tempCategory.getName(),
                tempCategory.isParentCategory(),
                tempList,
                tempCategory.getParentId());


        tempCategory = getCategory(subCategoryId);

        updateCategory(tempCategory.getId(),
                tempCategory.getName(),
                true,
                tempCategory.getSubcategories(),
                0);

        return true;
    }*//*

}
*/
