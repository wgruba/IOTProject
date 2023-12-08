import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Subject } from 'rxjs';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FilterSearchService {

  private buttonClickSource = new Subject<void>();

  buttonClick$ = this.buttonClickSource.asObservable();

  sendToSearch(): void {
    this.buttonClickSource.next();
  }

  getFillteredEvents(params1: FormGroup<any>): Observable<any> {
    let baseUrl = `http://localhost:8080`;
    const params = params1.value
    return this.http.get(`${baseUrl}/events/filter`, {params});
  }

  constructor(private http: HttpClient) {
    
  }
}
