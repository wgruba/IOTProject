import { Component } from '@angular/core';
import { Category } from '../models/Category.model';
import { SelectedItem } from '../models/selectedItem.model';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { SelectedLocation } from '../models/selectedLocation.model';
import { MatSnackBar } from '@angular/material/snack-bar';



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
  localisations: Category[] = [
    {
      name: 'Dolnośląskie',
      subcategories: ['Wrocław', 'Wałbrzych', 'Legnica']
    },
    {
      name: 'Podlasie',
      subcategories: ['Białystok', 'Łomża', 'Suwałki']
    },
    {
      name: 'Online',
      subcategories: []
    }
  ];

  selectedItems: SelectedItem[] = [];
  selectedLocalisation: SelectedLocation = { localisation: '', sublocalisation: ''}
  showPopup: boolean = false;
  searchForm: FormGroup;
  viewLocation: string = 'Lokalizacja:';


  constructor(private router: Router, private formBuilder: FormBuilder,public authService: AuthenticationService, private _snackBar: MatSnackBar  ) {
    this.searchForm = this.formBuilder.group({
      searchQuery: [''],
      dateFrom: [''],
      dateTo: [''],
    });
  }


  toggleSelection(categoryName: string, subcategoryName?: string): void {
    const existingIndex = this.selectedItems.findIndex(item => 
      item.category === categoryName && item.subcategory === subcategoryName
    );

    if (existingIndex > -1) {
      this.selectedItems.splice(existingIndex, 1); // Remove if already selected
      
      if(subcategoryName != null) {
        this._snackBar.open("Usunięto " + categoryName + " - " + subcategoryName, 'Zamknij', {duration: 5000})
      } else {
        this._snackBar.open("Usunięto " + categoryName, 'Zamknij', {duration: 5000})
      }
    } else {
      this.selectedItems.push({ category: categoryName, subcategory: subcategoryName });
      if(subcategoryName != null) {
        this._snackBar.open("Dodano " + categoryName + " - " + subcategoryName, 'Zamknij', {duration: 5000})
      } else {
        this._snackBar.open("Dodano " + categoryName, 'Zamknij', {duration: 5000})
      }    }
  }

  toggleLocation(localisation: string, sublocalisation?: string): void {
    if(localisation === this.selectedLocalisation.localisation && sublocalisation === this.selectedLocalisation.sublocalisation) {
      this.selectedLocalisation = { localisation: '', sublocalisation: '' };
      this.viewLocation = 'Lokalizacja:';
    } else {
      this.selectedLocalisation = { localisation: localisation, sublocalisation: sublocalisation };
      if(sublocalisation != null) {
        this.viewLocation = sublocalisation;
      } else {
        this.viewLocation = localisation;
      }
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

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login-site']);
  }

  onSearch(): void {
    console.log('Form Values:', this.searchForm.value);
    console.log(this.selectedLocalisation)
    console.log(this.selectedItems)
    const formData = {...this.searchForm.value, ...this.selectedLocalisation, ...this.selectedItems}
    console.log(formData)
    this.router.navigate(['/event-searching']);
  }
}
