import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { EventService } from '../event.service';

@Component({
  selector: 'app-add-event-site',
  templateUrl: './add-event-site.component.html',
  styleUrls: ['./add-event-site.component.scss']
})
export class AddEventSiteComponent {
  eventForm !: FormGroup;
  constructor(private eventService: EventService){}

  ngOnInit(): void {
    this.eventForm = new FormGroup({
      eventName: new FormControl('', Validators.required),
      participants: new FormControl(50, [Validators.required, Validators.min(1)]),
      isFree: new FormControl('no'),
      isOnline: new FormControl('no'),
      isReservationRequired: new FormControl('no'),
      ageRequirement: new FormControl('all'),
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

  onSubmit(): void {
      if (this.eventForm.valid) {
        console.log(this.eventForm.value);
        this.eventService.addEvent(this.eventForm.value).subscribe(
          response => {
            console.log('Event added:', response);
            // Handle success
          },
          error => {
            console.error('Error adding event:', error);
            // Handle error
          }
        );
      }
  }
}
