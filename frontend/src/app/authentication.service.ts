import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private isLoggedIn = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient) {
    const userToken = localStorage.getItem('userToken');
    this.isLoggedIn.next(!!userToken);
  }

  get isLoggedIn$(): Observable<boolean> {
    return this.isLoggedIn.asObservable();
  }

  login(username: string, password: string): void {
    // const loginUrl = 'http://localhost:8080/user/login';
    // this.http.post(loginUrl, { username, password }).subscribe(
    //   (response: any) => {
    //     const token = response.token;
    //     localStorage.setItem('userToken', token);
    //     this.isLoggedIn.next(true);
    //   },
    //   (error) => {
    //       console.error(error);
    //       // Handle error
    //   });
    localStorage.setItem('userToken', username);
    this.isLoggedIn.next(true);
  }

  logout(): void {
    localStorage.removeItem('userToken');
    this.isLoggedIn.next(false);
  }

}
