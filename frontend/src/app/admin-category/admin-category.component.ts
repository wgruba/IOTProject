import { Component } from '@angular/core';
import { Category } from '../models/Category.model';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';


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

  public categories: Category[] = [
    { 
      id: 1,
      name: 'Kategoria 1', 
      subcategories: [
          { id: 101, name: 'Podkategoria 1.1' }, 
          { id: 102, name: 'Podkategoria 1.2' }
      ]
      },
      { 
      id: 2,
      name: 'Kategoria 2', 
      subcategories: [
          { id: 103, name: 'Podkategoria 2.1' }, 
          { id: 106, name: 'Podkategoria 2.2' }
        ]
    },
    { 
      id: 3,
      name: 'Kategoria 3', 
      subcategories: [
          { id: 104, name: 'Podkategoria 3.1' }, 
          { id: 105, name: 'Podkategoria 3.2' }
      ]
    },]

  constructor(private router: Router, private formBuilder: FormBuilder) {
    this.categoryForm = this.formBuilder.group({
      categoryName: [''],
      subcategoryName: ['']
    });
  }
}
