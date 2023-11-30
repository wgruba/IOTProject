import { Component, OnInit } from '@angular/core';
import { Category } from '../models/Category.model';
import { Event } from '../models/event.model';
import { UserService } from '../user.service';

@Component({
  selector: 'app-user-subscriptions',
  templateUrl: './user-subscriptions.component.html',
  styleUrls: ['./user-subscriptions.component.scss']
})
export class UserSubscriptionsComponent implements OnInit {
  subscribedCategoriesList! : Category[];
  subscribedEventsList! : Event[];

  constructor(private userService: UserService) {
    
  }

  ngOnInit(): void {
    this.userService.getSubscribedEvents().subscribe(response => {
      this.subscribedEventsList = response;
    });
    this.userService.getSubscribedCategories().subscribe(response => {
      this.subscribedCategoriesList = response
    });
  }

  addSubscription(){
  }

  unsubscribeCategory(){

  }
  unsubscribeEvent(){
    
  }
}
