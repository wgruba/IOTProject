import { Component, OnInit } from '@angular/core';
import { Category } from '../models/Category.model';
import { Event } from '../models/event.model';
import { UserService } from '../user.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponentComponent } from '../confirmation-dialog-component/confirmation-dialog-component.component';
import { AddingCategoriesModalComponent } from '../adding-categories-modal/adding-categories-modal.component';



@Component({
  selector: 'app-user-subscriptions',
  templateUrl: './user-subscriptions.component.html',
  styleUrls: ['./user-subscriptions.component.scss']
})
export class UserSubscriptionsComponent implements OnInit {
  subscribedCategoriesList! : Category[];
  subscribedEventsList! : Event[];

  constructor(private userService: UserService, public dialog: MatDialog) {

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
    const dialogRef = this.dialog.open(AddingCategoriesModalComponent, {
      width: '500px',
      data: {title: 'Dodaj Kategorie: ', message: 'Wybierz kategorie jakie chcesz dodać: '}
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.ngOnInit()
      }
    });

  }

  unsubscribeCategory(categoryId: number){
    this.userService.unsubscribeCategory(categoryId).subscribe(response => {

    });
  }

  openUnsubscribeCategoryModal(categoryId: number) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponentComponent, {
      width: '350px',
      data: {title: 'Potwierdzenie', message: 'Czy na pewno chcesz anulować subskrybcję tej kategorii?'}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.unsubscribeCategory(categoryId);
      }
    });
  }

  unsubscribeEvent(eventId: number){
    this.userService.unsubscribeEvent(eventId).subscribe(response => {

    });
  }

  openUnsubscribeEventModal(eventId: number) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponentComponent, {
      width: '350px',
      data: {title: 'Potwierdzenie', message: 'Czy na pewno chcesz anulować subskrybcję tego wydarzenia?'}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.unsubscribeEvent(eventId);
      }
    });
  }
}
