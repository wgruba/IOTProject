import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Event } from '../models/event.model';
import { Router } from '@angular/router';
import { EventService } from '../event.service';
import { UserService } from '../user.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { FilterSearchService } from '../filter-search.service';
import { FormControl } from '@angular/forms';


@Component({
  selector: 'app-user-searching-page',
  templateUrl: './user-searching-page.component.html',
  styleUrls: ['./user-searching-page.component.scss']
})
export class UserSearchingPageComponent implements OnInit{
  roleClass: string = '';
  isAdmin: boolean = false;
  isUser: boolean = false ;
  filterForm: FormGroup;
  events: Event[] = [];


  constructor(private router: Router,private eventService: EventService, public userService: UserService, private formBuilder: FormBuilder, private filterSearchService: FilterSearchService) {
    this.filterForm = this.formBuilder.group({
      historyczne: false,
      rezerwacja: "0",
      koszt: "0",
      wiek: "0",
      miejscaMin: null,
      miejscaMax: null,
    });
  }

  ngOnInit(): void {
    const filter = localStorage.getItem('filter');
    if(filter != null){
      this.filterForm.patchValue(JSON.parse(filter));
    }
    const user = this.userService.getCurrentUser();
    if(user){
      this.isAdmin = user.permissionLevel === 'MODERATOR' || user.permissionLevel === "ADMIN";
      this.isUser = (!this.isAdmin)
    }
    this.roleClass = user.permissionLevel === 'VERIFIED_USER' ? 'admin-header' : 'user-header';
  }

  applyFilters(){
    console.log(this.filterForm.value);
    localStorage.setItem('filter', JSON.stringify(this.filterForm.value));
    this.filterSearchService.getFillteredEvents(this.filterForm.value).subscribe(response => {

    });
    this.filterSearchService.sendToSearch();
  }

  showDetails(card: Event): void {
    this.eventService.setCurrentEvent(card);
    this.router.navigate(['/event-details', card.id]);
  }
}
