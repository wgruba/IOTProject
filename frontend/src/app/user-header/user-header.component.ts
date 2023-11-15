import { Component } from '@angular/core';
import { Category } from '../models/Category.model';
import { SelectedItem } from '../models/selectedItem.model';
import { FormGroup, FormBuilder } from '@angular/forms';




@Component({
  selector: 'app-user-header',
  templateUrl: './user-header.component.html',
  styleUrls: ['./user-header.component.scss']
})
export class UserHeaderComponent {
  categories: Category[] = [
    { 
      name: 'Kategoria 1', 
      subcategories: ['Podkategoria 1.1', 'Podkategoria 1.2']
    },
    { 
      name: 'Kategoria 1', 
      subcategories: ['Podkategoria 1.1', 'Podkategoria 1.2']
    },
    { 
      name: 'Kategoria 1', 
      subcategories: ['Podkategoria 1.1', 'Podkategoria 1.2']
    },
    { 
      name: 'Kategoria 1', 
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
  selectedItems: SelectedItem[] = [];
  showPopup: boolean = false;
  searchForm: FormGroup;



  constructor(private formBuilder: FormBuilder) {
    this.searchForm = this.formBuilder.group({
      searchQuery: [''],
      location: [''],
      date: ['']
    });
  }


  toggleSelection(categoryName: string, subcategoryName?: string): void {
    const existingIndex = this.selectedItems.findIndex(item => 
      item.category === categoryName && item.subcategory === subcategoryName
    );

    if (existingIndex > -1) {
      this.selectedItems.splice(existingIndex, 1); // Remove if already selected
    } else {
      this.selectedItems.push({ category: categoryName, subcategory: subcategoryName });
    }
  }

  removeCategory(category: string, subcategory?: string): void {
    this.selectedItems = this.selectedItems.filter(item => 
      !(item.category === category && item.subcategory === subcategory)
    );
  }

  togglePopup(): void {
    this.showPopup = !this.showPopup;
  }
  

  onSearch(): void {
    console.log('Form Values:', this.searchForm.value);
    console.log(this.selectedItems)
  }
}
