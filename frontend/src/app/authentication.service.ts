import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private isLoggedIn = new BehaviorSubject<boolean>(false);

  constructor() {
    // check something in storage, or a token
    const userToken = localStorage.getItem('userToken');
    this.isLoggedIn.next(!!userToken);
  }

  get isLoggedIn$(): Observable<boolean> {
    return this.isLoggedIn.asObservable();
  }

  login(token: string): void {
    localStorage.setItem('userToken', token);
    this.isLoggedIn.next(true);
  }

  logout(): void {
    localStorage.removeItem('userToken');
    this.isLoggedIn.next(false);
  }

}
