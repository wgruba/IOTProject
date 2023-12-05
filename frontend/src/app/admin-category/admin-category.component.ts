import { Component } from '@angular/core';
import { Category } from '../models/Category.model';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { CategoryService } from '../category.service';


@Component({
  selector: 'app-admin-category',
  templateUrl: './admin-category.component.html',
  styleUrls: ['./admin-category.component.scss']
})

export class AdminCategoryComponent {
  public selectedCategory: number | null = null;
  categoryForm: FormGroup;

  public selectCategory(category: number): void {
    this.selectedCategory = this.selectedCategory === category ? null : category;
  }

  public addCategory(): void {
    const newCategory = { category: this.categoryForm.value.categoryName, subcategory: null };
    console.log(newCategory);
  }

  public addSubcategory(catName: string): void {
    const newSubcategory = { category: catName, subcategory: this.categoryForm.value.subcategoryName };
    console.log(newSubcategory);
  }

  public categories: Category[] = [];

  constructor(private router: Router, private formBuilder: FormBuilder, private categoryService: CategoryService) {
    this.categoryForm = this.formBuilder.group({
      categoryName: [''],
      subcategoryName: ['']
    });

    this.categoryService.getCategoriesFromDatabase().subscribe(response => {
      this.categories = response;
    });
  }
}
