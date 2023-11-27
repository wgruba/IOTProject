import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { EventService } from '../event.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from '../user.service';
import { Event } from '../models/event.model';

@Component({
  selector: 'app-add-event-site',
  templateUrl: './add-event-site.component.html',
  styleUrls: ['./add-event-site.component.scss']
})
export class AddEventSiteComponent {
  eventForm !: FormGroup;
  constructor(private eventService: EventService, private snackBar: MatSnackBar, public userService: UserService){}

  ngOnInit(): void {
    this.eventForm = new FormGroup({
      eventName: new FormControl('', Validators.required),
      participants: new FormControl(50, [Validators.required, Validators.min(1)]),
      isFree: new FormControl(false),
      isOnline: new FormControl(false),
      isReservationRequired: new FormControl(false),
      ageRequirement: new FormControl('FAMILY_FRIENDLY'),
      startTime: new FormControl('', [Validators.required, this.validateStartTime]),
      endTime: new FormControl('', Validators.required),
      description: new FormControl(''),
      localisation: new FormControl(''), // Add this if required
      imageUrl: new FormControl('') // Add this if required
    }, { validators: this.validateDates });
  }

  validateStartTime(control: FormControl): {[key: string]: any} | null {
    const startTime = new Date(control.value);
    if (startTime < new Date()) {
      return { 'startTimeInvalid': 'Start time cannot be in the past.' };
    }
    return null;
  }

  validateDates(control: AbstractControl): {[key: string]: any} | null {
    const group = control as FormGroup;
    const start = group.get('startTime')?.value;
    const end = group.get('endTime')?.value;
    if (start && end && new Date(start) > new Date(end)) {
      return { 'dateInvalid': 'End time cannot be earlier than start time.' };
    }
    return null;
  }


  prepareEventData(formValue: any): Event {
    return {
        id: 12,
        name: formValue.eventName,
        organizer: this.userService.getCurrentUser().id,
        categoryList: [],
        clientList: [],
        description: formValue.description,
        size: formValue.participants,
        localisation: formValue.localisation,
        isFree: formValue.isFree,
        isReservationNecessary: formValue.isReservationRequired,
        ageGroup: formValue.ageRequirement,
        startDate: formValue.startTime,
        endDate: formValue.endTime,
        eventStatus: 'TO_ACCEPTANCE',
        imageUrl: formValue.imageUrl 
    };
  }

  onSubmit(): void {
      if (this.eventForm.valid) {
        const eventData = this.prepareEventData(this.eventForm.value);
        console.log(eventData);
        this.eventService.addEvent(eventData).subscribe(
          response => {
            console.log('Event added:', response);
            this.snackBar.open("Wydarzenie zostało dodane", 'Zamknij', {
              duration: 3000,
              horizontalPosition: 'right',
              verticalPosition: 'top', });
          },
          error => {
            console.error('Error adding event:', error);
            this.snackBar.open("Coś poszło nie tak, Spróbuj ponownie", 'Zamknij', {
              duration: 3000,
              horizontalPosition: 'right',
              verticalPosition: 'top', });
          }
        );
      }
  }
}
