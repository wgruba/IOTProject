import { Injectable } from '@angular/core';
import { User } from './models/user.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthenticationService } from './authentication.service';



@Injectable({
  providedIn: 'root'
})
export class UserService {
  private currentUser = new BehaviorSubject<User>(this.getCurrentUser());
  constructor(private http: HttpClient, public authenticationService: AuthenticationService) {}
  
  getUserFromDatabase(): Observable<any> {
    const username = this.getUsername();
    const getUserUrl = `http://localhost:8080/users/name/${username}`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get<User>(getUserUrl, { headers });
  }

  setCurrentUser(user: User): void {
    localStorage.setItem('currentUser', JSON.stringify(user));
    localStorage.setItem('username', user.name); 
  }

  removeCurrentUser(): void {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('userToken');
    localStorage.removeItem('username');
  }

  getCurrentUser(): User {
    try {
      const userJson = localStorage.getItem('currentUser');
      if (userJson) {
        return JSON.parse(userJson) as User;
      }
    } catch (error) {
      console.error('Error parsing user data from localStorage', error);
    }
    return {id:0, name: "Adam" , mail: "a@mail.com", permissionLevel: "None", token: ""}; // Return null or a default user object
  }

  private getUserToken(): string | null {
    return localStorage.getItem('userToken');
  }

  private getUsername(): string | null {
    return localStorage.getItem('username');
  }
}

