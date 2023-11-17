import { Component } from '@angular/core';
import { EventService } from '../event.service';
import { ActivatedRoute } from '@angular/router';
import { Card } from '../models/card.model';


@Component({
  selector: 'app-description-page',
  templateUrl: './description-page.component.html',
  styleUrls: ['./description-page.component.scss']
})
export class DescriptionPageComponent {
  event !: Card;
  latitude: number;
  longitude: number;

  constructor(
    private route: ActivatedRoute, 
    private eventService: EventService
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

  getGoogleCalendarUrl(event: Card): string {
    var start: Date  = new Date('2023-11-14T18:30:00Z');
    var end: Date  = new Date('2023-11-14T23:20:00Z');
    const startTime = this.formatDateToGoogleCalendar(start); //need to adjust later with proper data
    const endTime = this.formatDateToGoogleCalendar(end);
  
    const details = encodeURIComponent(event.description);
    const location = encodeURIComponent(event.title);
    const title = encodeURIComponent(event.title);
  
    return `https://calendar.google.com/calendar/r/eventedit?text=${title}&dates=${startTime}/${endTime}&details=${details}&location=${location}`;
  }

  formatDateToGoogleCalendar(date: Date): string {
    return date.toISOString().replace(/-|:|\.\d\d\d/g, '');
  }

  addToGoogleCalendar(event: Card): void {
    const url = this.getGoogleCalendarUrl(event);
    window.open(url, '_blank');
  }
}
