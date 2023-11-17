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
  events: Event[] = [{
    id: 1,
    title: 'Computer',
    description: 'Description about computer...',
    url: 'https://picsum.photos/id/1/640/480',
  },
  {
    id: 2,
    title: 'Building',
    description: 'Building description...',
    url: 'https://picsum.photos/id/101/640/480',
  }, {
    id: 3,
    title: 'Glass over a computer',
    description: 'Description of a glass over a computer',
    url: 'https://picsum.photos/id/201/640/480',
  }, {
    id: 4,
    title: 'Autumn',
    description: 'Description about autumn leaves',
    url: 'https://picsum.photos/id/301/640/480',
  }, {
    id: 5,
    title: 'Balloon',
    description: 'Coloured balloon',
    url: 'https://picsum.photos/id/401/640/480',
  },
  {
    id: 1,
    title: 'Computer',
    description: 'Description about computer...',
    url: 'https://picsum.photos/id/1/640/480',
  },
  {
    id: 2,
    title: 'Building',
    description: 'Building description...',
    url: 'https://picsum.photos/id/101/640/480',
  }, {
    id: 3,
    title: 'Glass over a computer',
    description: 'Description of a glass over a computer',
    url: 'https://picsum.photos/id/201/640/480',
  }, {
    id: 4,
    title: 'Autumn',
    description: 'Description about autumn leaves',
    url: 'https://picsum.photos/id/301/640/480',
  }, {
    id: 5,
    title: 'Balloon',
    description: 'Coloured balloon',
    url: 'https://picsum.photos/id/401/640/480',
  },
  {
    id: 1,
    title: 'Computer',
    description: 'Description about computer...',
    url: 'https://picsum.photos/id/1/640/480',
  },
  {
    id: 2,
    title: 'Building',
    description: 'Building description...',
    url: 'https://picsum.photos/id/101/640/480',
  }, {
    id: 3,
    title: 'Glass over a computer',
    description: 'Description of a glass over a computer',
    url: 'https://picsum.photos/id/201/640/480',
  }, {
    id: 4,
    title: 'Autumn',
    description: 'Description about autumn leaves',
    url: 'https://picsum.photos/id/301/640/480',
  }, {
    id: 5,
    title: 'Balloon',
    description: 'Coloured balloon',
    url: 'https://picsum.photos/id/401/640/480',
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