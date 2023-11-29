import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class FilterSearchService {

  private buttonClickSource = new Subject<void>();

  buttonClick$ = this.buttonClickSource.asObservable();

  sendToSearch(): void {
    this.buttonClickSource.next();
  }

  constructor() {
    
  }
}
