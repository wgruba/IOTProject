import { Injectable } from '@angular/core';
import { Event } from './models/event.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private currentEvent!: Event;
  private eventsSubject = new BehaviorSubject<Event[]>([
  ]);
  private baseUrl = 'http://localhost:8080'

  constructor(private http: HttpClient) { 
  }

  setCurrentEvent(event: Event): void {
    this.currentEvent = event;
    localStorage.setItem('currentEvent', JSON.stringify(event));
  }

  getCurrentEvent(): Event {
  try {
    const eventJson = localStorage.getItem('currentEvent');
    if (eventJson) {
      return JSON.parse(eventJson) as Event;
    }
  } catch (error) {
    console.error('Error parsing event data from localStorage', error);
  }
  return  {id: 1,
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
  };
  }

  
  getEvents(): Observable<Event[]> {
    return this.eventsSubject.asObservable();
  }

  getAllEvents(): Observable<any> {
    return this.http.get(`${this.baseUrl}/events`);
  }

  addEvent(eventData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/addEvent`, eventData);
  }

}