import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient,HttpHeaders} from '@angular/common/http';

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
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const loginUrl = 'http://localhost:8080/users/login';
    this.http.get<boolean>(loginUrl, {headers: headers,
      params: { loginOrMail: username, password: password }
  }).subscribe(
      (response: any) => {
        const token = response.token;
        localStorage.setItem('userToken', token);
        if(response !== false){
          this.isLoggedIn.next(true);
        }
      },
      (error) => {
          console.error(error);
          this.isLoggedIn.next(false);
      });
    }

  logout(): void {
    localStorage.removeItem('userToken');
    this.isLoggedIn.next(false);
  }

}
