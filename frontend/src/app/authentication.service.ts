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


  login( username: string, password: string ): void {
     this.http.post('http://localhost:8080/login', {username, password})
     .subscribe(
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
          });;
  }

  register(user: any): Observable<any> {
      return this.http.post('/api/auth/register', user);
  }

  logout(): void {
    localStorage.removeItem('userToken');
    this.isLoggedIn.next(false);
  }

}
