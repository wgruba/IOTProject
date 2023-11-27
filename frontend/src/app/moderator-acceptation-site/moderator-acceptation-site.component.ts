import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Event } from '../models/event.model';
import { Router } from '@angular/router';
import { EventService } from '../event.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-moderator-acceptation-site',
  templateUrl: './moderator-acceptation-site.component.html',
  styleUrls: ['./moderator-acceptation-site.component.scss']
})
export class ModeratorAcceptationSiteComponent {
  isAdmin: boolean = false;
  events: Event[] = [
    {
      id: 1,
      name: "Music Concert",
      organizer: 123,
      categoryList: [],
      clientList: [],
      description: "A great evening of music",
      size: 200,
      localisation: "Downtown Auditorium",
      isFree : false,
      isReservationNecessary: true,
      ageGroup: "ADULT",
      startDate: "2023-12-01T19:00:00",
      endDate: "2023-12-01T23:00:00",
      eventStatus: "SCHEDULED",
      imageUrl: "https://picsum.photos/id/1/640/480"
    },
    {
      id: 2,
      name: "Music Concert",
      organizer: 123,
      categoryList: [],
      clientList: [],
      description: "A great evening of music",
      size: 200,
      localisation: "Downtown Auditorium",
      isFree : false,
      isReservationNecessary: true,
      ageGroup: "ADULT",
      startDate: "2023-12-01T19:00:00",
      endDate: "2023-12-01T23:00:00",
      eventStatus: "SCHEDULED",
      imageUrl: 'https://picsum.photos/id/101/640/480',
    }, {
      id: 3,
      name: "Music Concert",
      organizer: 123,
      categoryList: [],
      clientList: [],
      description: "A great evening of music",
      size: 200,
      localisation: "Downtown Auditorium",
      isFree : false,
      isReservationNecessary: true,
      ageGroup: "ADULT",
      startDate: "2023-12-01T19:00:00",
      endDate: "2023-12-01T23:00:00",
      eventStatus: "SCHEDULED",
      imageUrl: 'https://picsum.photos/id/201/640/480',
    }, {
      id: 4,
      name: "Music Concert",
      organizer: 123,
      categoryList: [],
      clientList: [],
      description: "A great evening of music",
      size: 200,
      localisation: "Downtown Auditorium",
      isFree : false,
      isReservationNecessary: true,
      ageGroup: "ADULT",
      startDate: "2023-12-01T19:00:00",
      endDate: "2023-12-01T23:00:00",
      eventStatus: "SCHEDULED",
      imageUrl: 'https://picsum.photos/id/301/640/480',
    }, {
      id: 5,
      name: "Music Concert",
      organizer: 123,
      categoryList: [],
      clientList: [],
      description: "A great evening of music",
      size: 200,
      localisation: "Downtown Auditorium",
      isFree : false,
      isReservationNecessary: true,
      ageGroup: "ADULT",
      startDate: "2023-12-01T19:00:00",
      endDate: "2023-12-01T23:00:00",
      eventStatus: "SCHEDULED",
      imageUrl: 'https://picsum.photos/id/401/640/480',
    },
    {
      id: 1,
      name: "Music Concert",
      organizer: 123,
      categoryList: [],
      clientList: [],
      description: "A great evening of music",
      size: 200,
      localisation: "Downtown Auditorium",
      isFree : false,
      isReservationNecessary: true,
      ageGroup: "ADULT",
      startDate: "2023-12-01T19:00:00",
      endDate: "2023-12-01T23:00:00",
      eventStatus: "SCHEDULED",
      imageUrl: 'https://picsum.photos/id/1/640/480',
    },
    {
      id: 2,
      name: "Music Concert",
      organizer: 123,
      categoryList: [],
      clientList: [],
      description: "A great evening of music",
      size: 200,
      localisation: "Downtown Auditorium",
      isFree : false,
      isReservationNecessary: true,
      ageGroup: "ADULT",
      startDate: "2023-12-01T19:00:00",
      endDate: "2023-12-01T23:00:00",
      eventStatus: "SCHEDULED",
      imageUrl: 'https://picsum.photos/id/101/640/480',
    }, {
      id: 3,
      name: "Music Concert",
      organizer: 123,
      categoryList: [],
      clientList: [],
      description: "A great evening of music",
      size: 200,
      localisation: "Downtown Auditorium",
      isFree : false,
      isReservationNecessary: true,
      ageGroup: "ADULT",
      startDate: "2023-12-01T19:00:00",
      endDate: "2023-12-01T23:00:00",
      eventStatus: "SCHEDULED",
      imageUrl: 'https://picsum.photos/id/201/640/480',
    }, {
      id: 4,
      name: "Music Concert",
      organizer: 123,
      categoryList: [],
      clientList: [],
      description: "A great evening of music",
      size: 200,
      localisation: "Downtown Auditorium",
      isFree : false,
      isReservationNecessary: true,
      ageGroup: "ADULT",
      startDate: "2023-12-01T19:00:00",
      endDate: "2023-12-01T23:00:00",
      eventStatus: "SCHEDULED",
      imageUrl: 'https://picsum.photos/id/301/640/480',
    }, {
      id: 5,
      name: "Music Concert",
      organizer: 123,
      categoryList: [],
      clientList: [],
      description: "A great evening of music",
      size: 200,
      localisation: "Downtown Auditorium",
      isFree : false,
      isReservationNecessary: true,
      ageGroup: "ADULT",
      startDate: "2023-12-01T19:00:00",
      endDate: "2023-12-01T23:00:00",
      eventStatus: "SCHEDULED",
      imageUrl: 'https://picsum.photos/id/401/640/480',
    },
  ];


  constructor(private router: Router, private eventService: EventService, public userService: UserService) {
  }

  ngOnInit(): void {
    const user = this.userService.getCurrentUser();
  }

  applyFilters(){
    
  }

  showDetails(card: Event): void {
    this.eventService.setCurrentEvent(card);
    this.router.navigate(['/admin-event-details', card.id]);
  }
}
