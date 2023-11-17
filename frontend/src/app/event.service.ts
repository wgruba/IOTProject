import { Injectable } from '@angular/core';
import { Card } from './models/card.model';


@Injectable({
  providedIn: 'root'
})
export class EventService {
  private currentEvent!: Card;

  constructor() { 
  }

  setCurrentEvent(event: Card): void {
    this.currentEvent = event;
    localStorage.setItem('currentEvent', JSON.stringify(event));
  }

  getCurrentEvent(): Card {
  try {
    const eventJson = localStorage.getItem('currentEvent');
    if (eventJson) {
      return JSON.parse(eventJson) as Card;
    }
  } catch (error) {
    console.error('Error parsing event data from localStorage', error);
  }
  return  { id: 1, title: 'Computer', description: 'Description about computer...', url: 'https://picsum.photos/id/1/640/480',};
  }
}
