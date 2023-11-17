package com.example.springboot.Category;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import java.util.List;

public class Category {
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private int id;
    @BsonProperty
    private String name;
    @BsonProperty
    private boolean isParentCategory;
    @BsonIgnore
    private List<Integer> subcategories;
    @BsonProperty
    private int parentId;

    public Category() {
    }

    public Category(@BsonId int id,
                    @BsonProperty String name,
                    @BsonProperty boolean isParentCategory,
                    @BsonProperty int parentId) {
        this.id = id;
        this.name = name;
        this.isParentCategory = isParentCategory;
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
