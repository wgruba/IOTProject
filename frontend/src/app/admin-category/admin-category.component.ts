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
  public selectedCategory: string | null = null;
  categoryForm: FormGroup;

  public selectCategory(category: string): void {
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
      name: 'Koncert 1', 
      subcategories: ['Podkategoria 1.1', 'Podkategoria 1.2', 'Podkategoria 1.3', 'Podkategoria 1.4']
    },
    { 
      name: 'Koncert 2', 
      subcategories: ['Podkategoria 1.1', 'Podkategoria 1.2']
    },
    { 
      name: 'Koncert 3', 
      subcategories: ['Podkategoria 1.1', 'Podkategoria 1.2']
    },
    { 
      name: 'Koncert 4', 
      subcategories: ['Podkategoria 1.1', 'Podkategoria 1.2']
    },
    { 
      name: 'Kategoria 1', 
      subcategories: ['Podkategoria 1.1', 'Podkategoria 1.2']
    },
    { 
      name: 'Kategoria 2', 
      subcategories: ['Podkategoria 1.1', 'Podkategoria 1.2']
    },
    { 
      name: 'Kategoria 3', 
      subcategories: ['Podkategoria 1.1', 'Podkategoria 1.2']
    },
    { 
      name: 'Kategoria 5', 
      subcategories: ['Podkategoria 1.1', 'Podkategoria 1.2']
    },
    { 
      name: 'Kategoria 9', 
      subcategories: ['Podkategoria 9.1', 'Podkategoria 9.2']
    },
  ];

  constructor(private router: Router, private formBuilder: FormBuilder) {
    this.categoryForm = this.formBuilder.group({
      categoryName: [''],
      subcategoryName: ['']
    });
  }
}
