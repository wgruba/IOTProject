import { Component, OnInit } from '@angular/core';
import { EventService } from '../event.service';
import { ActivatedRoute } from '@angular/router';
import { Event } from '../models/event.model';
import { UserService } from '../user.service';


@Component({
  selector: 'app-description-page',
  templateUrl: './description-page.component.html',
  styleUrls: ['./description-page.component.scss']
})
export class DescriptionPageComponent implements OnInit{
  event !: Event;
  latitude!: number;
  longitude!: number;
  isSubscribed = false;
  organizerName: Organizer = {name: ""};

  constructor(
    private route: ActivatedRoute, 
    private eventService: EventService,
    private userService: UserService
  ) {

  }

  ngOnInit(): void {
    const eventId = this.route.snapshot.params['id'];
    this.event = this.eventService.getCurrentEvent();
    const currentUser = this.userService.getCurrentUser();
    if (currentUser && Array.isArray(currentUser.subscribedEvents)) {
      this.isSubscribed = currentUser.subscribedEvents.includes(this.event.id);
    } else {
      this.isSubscribed = false;
    }
    this.getOrganizerUsername();
  }

  getGoogleCalendarUrl(event: Event): string {
    const startTime = this.formatDateToGoogleCalendar(new Date(event.startDate)); //need to adjust later with proper data
    const endTime = this.formatDateToGoogleCalendar(new Date(event.endDate));
  
    const details = encodeURIComponent(event.description);
    const location = encodeURIComponent(event.name);
    const title = encodeURIComponent(event.name);
  
    return `https://calendar.google.com/calendar/r/eventedit?text=${title}&dates=${startTime}/${endTime}&details=${details}&location=${location}`;
  }

  getOrganizerUsername() {
    this.eventService.getOrganizerName(this.event.organizer).subscribe(data => {
      this.organizerName.name = data
    }, error => console.error(error));
  }

  formatDateToGoogleCalendar(date: Date): string {
    return date.toISOString().replace(/-|:|\.\d\d\d/g, '');
  }

  addToGoogleCalendar(event: Event): void {
    const url = this.getGoogleCalendarUrl(event);
    window.open(url, '_blank');
  }

  subscribeEvent(){
    const currentEventId = this.eventService.getCurrentEvent().id;
    this.userService.subscribeEvent(currentEventId).subscribe(response => {
      this.isSubscribed = true;
      const user = this.userService.getCurrentUser();
      user.subscribedEvents.push(currentEventId)
      this.userService.setCurrentUser(user)
    });
  }
}

export class Organizer {
  constructor(
    public name: string) { }
}
