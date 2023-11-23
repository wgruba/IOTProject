import { Component,  OnInit } from '@angular/core';
import { EventService } from '../event.service';
import { Event } from '../models/event.model';
import { Router } from '@angular/router';



@Component({
  selector: 'app-user-events',
  templateUrl: './user-events.component.html',
  styleUrls: ['./user-events.component.scss']
})
export class UserEventsComponent implements OnInit {
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
      imageUrl: "https://picsum.photos/id/1/640/480g"
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

  constructor(private eventService: EventService, private router: Router) {}

  ngOnInit(): void {
    // this.eventService.getEvents().subscribe(events => {
    //   this.events = events;
    // });
  }


  editEvent(){
    this.router.navigate(['/add-event']);
  }

  addEvent(): void {
    this.router.navigate(['/add-event']);
  }
}