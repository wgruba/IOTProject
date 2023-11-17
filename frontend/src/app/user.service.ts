import { Injectable } from '@angular/core';
import { User } from './models/user.model';
import { BehaviorSubject, Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userSubject = new BehaviorSubject<User>({
    id: '1',
    username: 'Adamus12',
    password: "sadas",
    name: 'Adam',
    email: 'adam.nowak@gmail.com',
    isVerified: true,
    permissonLevel: "User", 
  });

  constructor() { }

  getUser(): Observable<User> {
    return this.userSubject.asObservable();
  }

}
