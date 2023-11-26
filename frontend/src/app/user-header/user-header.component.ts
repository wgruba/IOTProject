import { Component, OnInit} from '@angular/core';
import { Category } from '../models/Category.model';
import { SelectedItem } from '../models/selectedItem.model';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { UserService } from '../user.service';


@Component({
  selector: 'app-user-header',
  templateUrl: './user-header.component.html',
  styleUrls: ['./user-header.component.scss']
})
export class UserHeaderComponent implements OnInit {
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
  roleClass: string = '';
  isAdmin: boolean = false;
  isUser: boolean = false ;


  constructor(private router: Router, private formBuilder: FormBuilder,public authService: AuthenticationService, public userService: UserService) {
    this.searchForm = this.formBuilder.group({
      searchQuery: [''],
      location: [''],
      date: ['']
    });
  }

  ngOnInit(): void {
    const user = this.userService.getCurrentUser();
    if(user){
      this.isUser = user.permissionLevel !== 'VERIFIED_USER';
      this.isAdmin = user.permissionLevel === 'VERIFIED_USER';
    }
    this.roleClass = user.permissionLevel === 'VERIFIED_USER' ? 'admin-header' : 'user-header';
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

  logout(): void {
    this.authService.logout();
    this.userService.removeCurrentUser();
    this.router.navigate(['/login-site']);
  }

  onSearch(): void {
    console.log('Form Values:', this.searchForm.value);
    console.log(this.selectedItems)
    this.router.navigate(['/event-searching']);
  }
}
