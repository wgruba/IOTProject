import { Injectable } from '@angular/core';
import { User } from './models/user.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'https://localhost:8443';
  private currentUser = new BehaviorSubject<User | null>(this.getCurrentUser());
  
  constructor(private http: HttpClient, public authenticationService: AuthenticationService) {}
  
  getUserFromDatabase(): Observable<any> {
    const username = this.getUsername();
    const url = `${this.baseUrl}/users/name/${username}`;
    const headers = this.authenticationService.getHeadersWithToken();
    return this.http.get<User>(url, { headers });
  }

  getUserFromDatabaseByName(name: string): Observable<any> {
    const url = `${this.baseUrl}/users/name/${name}`;
    const headers = this.authenticationService.getHeadersWithToken();
    return this.http.get<User>(url, { headers });
  }

  getAllUsers(): Observable<User[]> {
    const url = `${this.baseUrl}/users`;
    const headers = this.authenticationService.getHeadersWithToken();
    return this.http.get<User[]>(url, { headers });
  }

  getAllUsersFiltered(name: string, mail: string, admin: boolean, mod: boolean, ver: boolean, nonver: boolean): Observable<User[]> {
    const params = { name: name, mail: mail, admin: admin.toString(), mod: mod.toString(), ver: ver.toString(), nonver: nonver.toString() };
    const url = `${this.baseUrl}/users/filter`;
    const headers = this.authenticationService.getHeadersWithToken();
    return this.http.get<User[]>(url, { headers, params });
  }

  setCurrentUser(user: User): void {
    localStorage.setItem('currentUser', JSON.stringify(user));
    localStorage.setItem('username', user.name); 
  }

  removeCurrentUser(): void {
    this.currentUser.next(null);
    localStorage.removeItem('currentUser');
    localStorage.removeItem('userToken');
    localStorage.removeItem('username');
  }

  addUser(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/unauthorized/addUser`, user);
  }

  getLastID(): Observable<any> {
    return this.http.get(`${this.baseUrl}/unauthorized/user/last`);
  }

  updateUserPermissions(id: number, permissionLevel: number): void {
    const url = `${this.baseUrl}/users/${id}/updatePermissions`;
    const headers = this.authenticationService.getHeadersWithToken();
    this.http.put(url, permissionLevel, { headers }).subscribe();
  }

  updateUserPassword(id: number, password: string): void {
    const url = `${this.baseUrl}/users/${id}/updatePassword`;
    const headers = this.authenticationService.getHeadersWithToken();
    this.http.put(url, password, { headers }).subscribe();
  }

  requestPasswordReset(mail: string): Observable<number> {
    return this.http.post<number>(`${this.baseUrl}/unauthorized/sendResetToken`, mail);
  }

  confirmRequestPasswordReset(token: string, password: string): Observable<boolean> {
    return this.http.put<boolean>(`${this.baseUrl}/unauthorized/resetPasswordConfirm/${token}`, password);
  }

  subscribeEvent(eventId: number): Observable<any> {
    const url = `${this.baseUrl}/users/${this.getCurrentUser().id}/subscribeEvent/${eventId}`;
    return this.http.patch(url, null, this.authenticationService.getOptionsWithToken());
  }

  unsubscribeEvent(eventId: number): Observable<any> {
    const url = `${this.baseUrl}/users/${this.getCurrentUser().id}/unsubscribeEvent/${eventId}`;
    return this.http.patch(url, null, this.authenticationService.getOptionsWithToken());
  }

  subscribeCategory(eventIds: number[]): Observable<any> {
    const url = `${this.baseUrl}/users/${this.getCurrentUser().id}/subscribeCategories`;
    return this.http.patch(url, eventIds, this.authenticationService.getOptionsWithToken());
  }

  unsubscribeCategory(categoryId: number): Observable<any> {
    const url = `${this.baseUrl}/users/${this.getCurrentUser().id}/unsubscribeCategory/${categoryId}`;
    return this.http.patch(url, null, this.authenticationService.getOptionsWithToken());
  }

  getSubscribedCategories(): Observable<any> {
    const url = `${this.baseUrl}/users/${this.getCurrentUser().id}/subscribedCategories`;
    const headers = this.authenticationService.getHeadersWithToken();
    return this.http.get(url, { headers });
  }

  getSubscribedCategoriesById(id: number): Observable<any> {
    const url = `${this.baseUrl}/users/${id}/subscribedCategories`;
    const headers = this.authenticationService.getHeadersWithToken();
    return this.http.get(url, { headers });
  }

  getSubscribedEvents(): Observable<any> {
    const url = `${this.baseUrl}/users/${this.getCurrentUser().id}/subscribedEvents`;
    const headers = this.authenticationService.getHeadersWithToken();
    return this.http.get(url, { headers });
  }

  getSubscribedEventsById(id: number): Observable<any> {
    const url = `${this.baseUrl}/users/${id}/subscribedEvents`;
    const headers = this.authenticationService.getHeadersWithToken();
    return this.http.get(url, { headers });
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
    return { id: 0, name: "Adam", mail: "a@mail.com", permissionLevel: 'None', token: "", subscribedEvents: [] };
  }

  private getUserToken(): string | null {
    return localStorage.getItem('userToken');
  }

  private getUsername(): string | null {
    return localStorage.getItem('username');
  }
}
