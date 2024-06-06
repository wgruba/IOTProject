import { Injectable, provideZoneChangeDetection } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { jwtDecode } from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private isLoggedIn = new BehaviorSubject<boolean>(false);
  baseUrl = 'https://localhost:8443';

  constructor(private http: HttpClient) {
    this.checkTokenValidity();
  }

  get isLoggedIn$(): Observable<boolean> {
    return this.isLoggedIn.asObservable();
  }

  login(username: string, password: string): Observable<any> {
    console.log('Attempting login with username:', username);
    const body = { username, password }
    return this.http.post(this.baseUrl + '/unauthorized/login', body);
  }

  register(user: any): Observable<any> {
    return this.http.post(this.baseUrl + '/unauthorized/register', user);
  }

  logout(): void {
    localStorage.removeItem('userToken');
    this.isLoggedIn.next(false);
  }

  setIsLoggedIn(value: boolean) {
    this.isLoggedIn.next(value);
  }

  private isTokenExpired(token: string): boolean {
    if (!token) {
      return true;
    }

    try {
      const { exp } = jwtDecode(token);
      if (!exp) {
        return false;
      }
      return Date.now() >= exp * 1000;
    } catch (error) {
      console.error('Error decoding token:', error);
      return false;
    }
  }

  private checkTokenValidity(): void {
    const token = localStorage.getItem('userToken');
    if (token) {
      const isExpired = this.isTokenExpired(token);
      this.isLoggedIn.next(!isExpired);
      if (isExpired) {
        this.logout();
      }
    }
  }

  public getHeadersWithToken(): HttpHeaders {
    const token = localStorage.getItem('userToken');
    console.log(token);
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  public getOptionsWithToken() {
    const headers = this.getHeadersWithToken();
    const options = {
      headers: headers
    };
    return (options);
  }
}
