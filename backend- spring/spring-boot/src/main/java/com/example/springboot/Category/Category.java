package com.example.springboot.Category;


import java.util.List;
public class Category {
    private int id;
    private String name;
    private boolean isParentCategory;
    private List<Integer> subcategories;
    private int parentId;

    public Category() {
    }

    public Category(int id, String name, boolean isParentCategory, List<Integer> subcategories, int parentId) {
        this.id = id;
        this.name = name;
        this.isParentCategory = isParentCategory;
        this.subcategories = subcategories;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isParentCategory() {
        return isParentCategory;
    }

    public void setParentCategory(boolean parentCategory) {
        isParentCategory = parentCategory;
    }

    public List<Integer> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Integer> subcategories) {
        this.subcategories = subcategories;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
