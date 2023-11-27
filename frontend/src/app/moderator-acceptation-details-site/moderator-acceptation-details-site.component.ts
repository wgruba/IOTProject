import { Component } from '@angular/core';
import { EventService } from '../event.service';
import { ActivatedRoute } from '@angular/router';
import { Event } from '../models/event.model';

@Component({
  selector: 'app-moderator-acceptation-details-site',
  templateUrl: './moderator-acceptation-details-site.component.html',
  styleUrls: ['./moderator-acceptation-details-site.component.scss']
})
export class ModeratorAcceptationDetailsSiteComponent {
  event !: Event;
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

  
}
