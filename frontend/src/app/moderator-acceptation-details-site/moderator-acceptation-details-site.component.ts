import { Component } from '@angular/core';
import { EventService } from '../event.service';
import { ActivatedRoute } from '@angular/router';
import { Event } from '../models/event.model';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponentComponent } from '../confirmation-dialog-component/confirmation-dialog-component.component';
import { AdminService } from '../admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';




@Component({
  selector: 'app-moderator-acceptation-details-site',
  templateUrl: './moderator-acceptation-details-site.component.html',
  styleUrls: ['./moderator-acceptation-details-site.component.scss']
})
export class ModeratorAcceptationDetailsSiteComponent {
  event !: Event;
  latitude!: number;
  longitude!: number;

  constructor(
    private route: ActivatedRoute, 
    private eventService: EventService,
    public dialog: MatDialog,
    private adminService: AdminService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    const eventId = this.route.snapshot.params['id'];
    this.event = this.eventService.getCurrentEvent();
    // Optional: check if the ID from the route matches the event ID, or fetch it from a backend
    if (this.event.id !== parseInt(eventId, 10)) {
      // Handle the mismatch, possibly fetch the event by ID from a backend
    }
  }

  acceptEvent(){
    const dialogRef = this.dialog.open(ConfirmationDialogComponentComponent, {
      width: '350px',
      data: {title: 'Czy napewno chcesz zaakceptować to wydarzenie?', message: 'Akceptacja wydarzenia powinna być uprzedzona dokładnym jego sprawdzeniem.'}
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.adminService.acceptEvent(this.event.id).subscribe(
          response => {
            this.snackBar.open("Wydarzenie zostało zaakceptowane", 'Zamknij', {
              duration: 4000,
              horizontalPosition: 'right',
              verticalPosition: 'top', });
              this.router.navigate(['/admin-acceptance']);
                    },
          error => {
            this.snackBar.open("coś poszło nie tak spróbuj ponownie", 'Zamknij', {
              duration: 4000,
              horizontalPosition: 'right',
              verticalPosition: 'top', });
          }
        );
      }
    });
  }

  denyEvent(){
    const dialogRef = this.dialog.open(ConfirmationDialogComponentComponent, {
      width: '350px',
      data: {title: 'Czy napewno chcesz odrzucić to wydarzenie?', message: 'Odrzucenie wydarzenia powinno być uprzedzone dokładnym jego sprawdzeniem.'}
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.adminService.denyEvent(this.event.id).subscribe(
          response => {
            this.snackBar.open("Wydarzenie zostało odrzucone", 'Zamknij', {
              duration: 4000,
              horizontalPosition: 'right',
              verticalPosition: 'top', });
              this.router.navigate(['/admin-acceptance']);
            },
          error => {
            this.snackBar.open("coś poszło nie tak spróbuj ponownie", 'Zamknij', {
              duration: 4000,
              horizontalPosition: 'right',
              verticalPosition: 'top', });
            }
        );
      }
    });
  }

  
}
