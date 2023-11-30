import { Component } from '@angular/core';
import { EventService } from '../event.service';
import { ActivatedRoute } from '@angular/router';
import { Event } from '../models/event.model';
import { UserService } from '../user.service';


@Component({
  selector: 'app-description-page',
  templateUrl: './description-page.component.html',
  styleUrls: ['./description-page.component.scss']
})
export class DescriptionPageComponent {
  event !: Event;
  latitude: number;
  longitude: number;
  isSubscribed = false;

  constructor(
    private route: ActivatedRoute, 
    private eventService: EventService,
    private userService: UserService
  ) {
    this.latitude = 50.4300934;
    this.longitude = 22.236453;
  }

  ngOnInit(): void {
    const eventId = this.route.snapshot.params['id'];
    this.event = this.eventService.getCurrentEvent();
    // Optional: check if the ID from the route matches the event ID, or fetch it from a backend
    if (this.event.id !== parseInt(eventId, 10)) {
      // Handle the mismatch, possibly fetch the event by ID from a backend
    }
  }

  getGoogleCalendarUrl(event: Event): string {
    const startTime = this.formatDateToGoogleCalendar(new Date(event.startDate)); //need to adjust later with proper data
    const endTime = this.formatDateToGoogleCalendar(new Date(event.endDate));
  
    const details = encodeURIComponent(event.description);
    const location = encodeURIComponent(event.name);
    const title = encodeURIComponent(event.name);
  
    return `https://calendar.google.com/calendar/r/eventedit?text=${title}&dates=${startTime}/${endTime}&details=${details}&location=${location}`;
  }

  formatDateToGoogleCalendar(date: Date): string {
    return date.toISOString().replace(/-|:|\.\d\d\d/g, '');
  }

  addToGoogleCalendar(event: Event): void {
    const url = this.getGoogleCalendarUrl(event);
    window.open(url, '_blank');
  }

  subscribeEvent(){
    this.userService.subscribeEvent(this.eventService.getCurrentEvent().id).subscribe(response => {
      this.isSubscribed = true;
    });
  }
}
