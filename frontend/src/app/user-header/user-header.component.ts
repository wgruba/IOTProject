import { Component, OnInit} from '@angular/core';
import { Category } from '../models/Category.model';
import { SelectedItem } from '../models/selectedItem.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { UserService } from '../user.service';
import { SelectedLocation } from '../models/selectedLocation.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FilterSearchService } from '../filter-search.service';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-user-header',
  templateUrl: './user-header.component.html',
  styleUrls: ['./user-header.component.scss']
})
export class UserHeaderComponent implements OnInit {
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
      id: 1,
      name: 'Kategoria 2', 
      subcategories: [
          { id: 103, name: 'Podkategoria 2.1' }, 
          { id: 106, name: 'Podkategoria 2.2' }
        ]
    },
    { 
      id: 1,
      name: 'Kategoria 3', 
      subcategories: [
          { id: 104, name: 'Podkategoria 3.1' }, 
          { id: 105, name: 'Podkategoria 3.2' }
      ]
    },]
  localisations: Category[] = [
    {
      id: 3,
      name: 'Dolnośląskie',
      subcategories: [{id: 104, name:'Wrocław'}, {id: 104, name:'Wałbrzych'}, {id: 104, name:'Legnica'}]
    },
    {
      id: 2,
      name: 'Podlasie',
      subcategories: []
    },
    {
      id: 5,
      name: 'Online',
      subcategories: []
    }
  ];

  private filterSub: Subscription;

  selectedItems: SelectedItem[] = [];
  selectedLocalisation: SelectedLocation = { localisation: '', sublocalisation: ''}
  showPopup: boolean = false;
  searchForm: FormGroup;
  viewLocation: string = 'Lokalizacja:';
  roleClass: string = '';
  isAdmin: boolean = false;
  isUser: boolean = false ;
  filterParsed: any;

  constructor(private router: Router, private formBuilder: FormBuilder,public authService: AuthenticationService, public userService: UserService, private _snackBar: MatSnackBar, private filterSearchService: FilterSearchService) {
    this.filterSub = this.filterSearchService.buttonClick$.subscribe(() => {
      this.onSearch();
    });

    this.searchForm = this.formBuilder.group({
      searchQuery: [''],
      dateFrom: [''],
      dateTo: [''],
    });
  }

  ngOnInit(): void {
    const user = this.userService.getCurrentUser();
    if(user){
      this.isAdmin = user.permissionLevel === 'MODERATOR' || user.permissionLevel === "ADMIN";
      this.isUser = (!this.isAdmin)
    }
    this.roleClass = user.permissionLevel === 'VERIFIED_USER' ? 'admin-header' : 'user-header';
  }
  ngOnDestroy(): void {
    this.filterSub.unsubscribe();
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
    this.userService.removeCurrentUser();
    this.authService.logout();
    this.router.navigate(['/login-site']);
  }

  onSearch(): void {
    console.log('Form Values:', this.searchForm.value);
    console.log(this.selectedLocalisation)
    console.log(this.selectedItems)
    const filterData = localStorage.getItem('filter')
    if(filterData == null){
      this.filterParsed = JSON.stringify({historyczne: false, rezerwacja: "0", koszt: "0", wiek: "0", miejscaMin: [null, [Validators.min(0)]], miejscaMax: [null, [Validators.min(0)]]})
    }
    else {
      this.filterParsed = JSON.parse(filterData)
    }
    console.log(filterData)
    const formData = {...this.searchForm.value, ...this.selectedLocalisation, ...this.selectedItems, ...this.filterParsed}
    console.log(formData)
    this.router.navigate(['/event-searching']);
  }
}
