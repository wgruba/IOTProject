import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Category } from '../models/Category.model';
import { UserService } from '../user.service';

@Component({
  selector: 'app-adding-categories-modal',
  templateUrl: './adding-categories-modal.component.html',
  styleUrls: ['./adding-categories-modal.component.scss']
})
export class AddingCategoriesModalComponent {
  categories: Category[] = [
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
  selectedCategoryIds: number[] = [];
  selectedSubcategoryIds: number[] = [];

  constructor(
    public dialogRef: MatDialogRef<AddingCategoriesModalComponent>,
    public userService: UserService,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) {}

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  onYesClick(): void {
    let allCategoryIds: number[] = [...this.selectedCategoryIds, ...this.selectedSubcategoryIds];
    console.log(allCategoryIds)
    allCategoryIds.forEach(async (val) => { this.userService.subscribeCategory(val).subscribe(response => 
      {console.log(response)})})
    this.dialogRef.close(true);
  }

  toggleCategorySelection(categoryId: number): void {
    const index = this.selectedCategoryIds.indexOf(categoryId);
    if (index > -1) {
      this.selectedCategoryIds.splice(index, 1);
    } else {
      this.selectedCategoryIds.push(categoryId);
    }
  }

  toggleSubcategorySelection(subcategoryId: number): void {
    const index = this.selectedSubcategoryIds.indexOf(subcategoryId);
    if (index > -1) {
      this.selectedSubcategoryIds.splice(index, 1);
    } else {
      this.selectedSubcategoryIds.push(subcategoryId);
    }
  }


  getSelectedCategoryNames(): string[] {
    return this.selectedCategoryIds.map(categoryId => 
      this.categories.find(category => category.id === categoryId)?.name || ''
    );
  }
  
  getSelectedSubcategoryNames(): string[] {
    return this.selectedSubcategoryIds.map(subcategoryId => {
      for (const category of this.categories) {
        const subcategory = category.subcategories.find(sub => sub.id === subcategoryId);
        if (subcategory) {
          return subcategory.name;
        }
      }
      return '';
    });
  }


  getCategoryByName(name: string): Category | undefined {
    return this.categories.find(category => category.name === name);
  }
  
  getSubcategoryByName(name: string): { categoryId: number, subcategoryId: number } | undefined {
    for (const category of this.categories) {
      const subcategory = category.subcategories.find(sub => sub.name === name);
      if (subcategory) {
        return { categoryId: category.id, subcategoryId: subcategory.id };
      }
    }
    return undefined;
  }
  
  removeSelectedCategory(categoryName: string): void {
    const category = this.getCategoryByName(categoryName);
    if (category) {
      this.selectedCategoryIds = this.selectedCategoryIds.filter(id => id !== category.id);
      // Update UI or backend as necessary
    }
  }
  
  removeSelectedSubcategory(subcategoryName: string): void {
    const subcategoryInfo = this.getSubcategoryByName(subcategoryName);
    if (subcategoryInfo) {
      this.selectedSubcategoryIds = this.selectedSubcategoryIds.filter(id => id !== subcategoryInfo.subcategoryId);
      // Update UI or backend as necessary
    }
  }

  removeCategory(categoryId: number): void {
    this.selectedCategoryIds = this.selectedCategoryIds.filter(id => id !== categoryId);
  }
  
  removeSubcategory(subcategoryId: number): void {
    this.selectedSubcategoryIds = this.selectedSubcategoryIds.filter(id => id !== subcategoryId);
  }
}

export interface DialogData {
  title: string;
  message: string;
}


