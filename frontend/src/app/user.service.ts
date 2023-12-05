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

  getUserFromDatabaseByName(name: string): Observable<any> {
    const getUserUrl = `http://localhost:8080/users/name/${name}`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get<User>(getUserUrl, { headers });
  }

  getAllUsers(): Observable<User[]>{
    const getUserUrl = `http://localhost:8080/users`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get<User[]>(getUserUrl, { headers });
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

  addUser(user: any): Observable<any> {
    return this.http.post('http://localhost:8080/addUser', user);
  }

  getLastID(): Observable<any> {
    return this.http.get('http://localhost:8080/user/last');
  }


  //Zarządzanie Subskrypcją
  
  subscribeEvent(eventId: number){
    const getUserUrl = `http://localhost:8080/users/${this.getCurrentUser().id}/subscribeEvent/${eventId}`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.patch(getUserUrl, {headers});
  }

  unsubscribeEvent(eventId: number){
    const getUserUrl = `http://localhost:8080/users/${this.getCurrentUser().id}/unsubscribeEvent/${eventId}`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.patch(getUserUrl, {headers});
  }


  subscribeCategory(eventId: number[]){
    const getUserUrl = `http://localhost:8080/users/${this.getCurrentUser().id}/subscribeCategories`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.patch(getUserUrl, eventId, {headers});
  }

  unsubscribeCategory(categoryId: number){
    const getUserUrl = `http://localhost:8080/users/${this.getCurrentUser().id}/unsubscribeCategory/${categoryId}`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.patch(getUserUrl, {headers});
  }

  getSubscribedCategories(): Observable<any> {
    const getUserUrl = `http://localhost:8080/users/${this.getCurrentUser().id}/subscribedCategories`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get(getUserUrl, {headers});
  }
  getSubscribedCategoriesById(id: number): Observable<any> {
    const getUserUrl = `http://localhost:8080/users/${id}/subscribedCategories`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get(getUserUrl, {headers});
  }

  getSubscribedEvents(): Observable<any> {
    const getUserUrl = `http://localhost:8080/users/${this.getCurrentUser().id}/subscribedEvents`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get(getUserUrl, {headers});
  }
  getSubscribedEventsById(id: number): Observable<any> {
    const getUserUrl = `http://localhost:8080/users/${id}/subscribedEvents`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.get(getUserUrl, {headers});
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
    return {id:0, name: "Adam" , mail: "a@mail.com", permissionLevel: "None", token: "", subscribedEvents: []};
  }

  private getUserToken(): string | null {
    return localStorage.getItem('userToken');
  }

  private getUsername(): string | null {
    return localStorage.getItem('username');
  }
}

