import { Injectable } from '@angular/core';
import { Event } from './models/event.model';
import { BehaviorSubject, Observable } from 'rxjs';



@Injectable({
  providedIn: 'root'
})
export class EventService {
  private currentEvent!: Event;
  private eventsSubject = new BehaviorSubject<Event[]>([
  ]);

  constructor() { 
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
  return  { id: 1, title: 'Computer', description: 'Description about computer...', url: 'https://picsum.photos/id/1/640/480',};
  }

  
  getEvents(): Observable<Event[]> {
    return this.eventsSubject.asObservable();
  }

}
