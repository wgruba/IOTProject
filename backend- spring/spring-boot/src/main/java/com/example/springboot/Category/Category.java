package com.example.springboot.Category;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;

@Document(collection = "Category")
public class Category {
    @Id
    private int id;
    private String name;
    private boolean isParentCategory;
    private List<Integer> subcategories;
    private int parentId;
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
