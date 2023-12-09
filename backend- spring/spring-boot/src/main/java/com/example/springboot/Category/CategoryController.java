package com.example.springboot.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;


    // CRUD - Create
    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        Optional<Category> existingCategory = categoryRepository.getCategoryByName(category.getName());
        if (existingCategory.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Category with the same name already exists");
        }
        category.setParentCategory(true);
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(savedCategory);
    }
    @PostMapping("/addSubCategory")
    public ResponseEntity<?> addSubCategory(@RequestBody Category subcategory){
        Category parentCategory = categoryRepository.findById(subcategory.getParentId());

        List<Pair<Integer, String>> tempList = parentCategory.getSubcategories();
        Pair tempPair = Pair.of(subcategory.getId(), subcategory.getName());
        tempList.add(tempPair);
        parentCategory.setSubcategories(tempList);
        categoryRepository.save(parentCategory);

        Category savedCategory = categoryRepository.save(subcategory);
        return ResponseEntity.ok(savedCategory);
    }


    // CRUD - Read
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/categories/name/{name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String name) {
        Category category = categoryRepository.getCategoryByName(name).get();
        return ResponseEntity.ok(category);
    }
    @GetMapping("/categories/list")
    public List<Category> getCategoriesFromList(List<Integer> ids){
        return categoryRepository.getCategoriesFromList(ids);
    }
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int categoryId){
        return ResponseEntity.ok(categoryRepository.findById(categoryId));
    }
    @GetMapping("/categories/parentCategories")
    public ResponseEntity<List<Category>> getParentCategories(){
        return ResponseEntity.ok(categoryRepository.findAllParentCategories());
    }
    @GetMapping("categories/{categoryId}/subcategories")
    public ResponseEntity<List<Category>> getSubcategories(@PathVariable int categoryId){
        return ResponseEntity.ok(categoryRepository.getAllSubCategoriesOfParentCategory(categoryId));
    }


    // CRUD - Update
    @PutMapping("/categories/{id}")
    public Category updateCategory(@PathVariable int id, @RequestBody Category category) {
        return updateCategoryHelper(id, category);
    }
    public Category updateCategoryHelper(int categoryId, Category updatedCategory) {
        Category category = categoryRepository.findById(categoryId);
        category.setName(updatedCategory.getName());
        category.setParentCategory(updatedCategory.isParentCategory());
        category.setSubcategories(updatedCategory.getSubcategories());
        category.setParentId(updatedCategory.getParentId());

        return categoryRepository.save(category);
    }


    // CRUD - Delete
    @DeleteMapping("/categories/{id}")
    public boolean deleteUser(@PathVariable int id) {
        categoryRepository.deleteById(id);
        return true;
    }
}
