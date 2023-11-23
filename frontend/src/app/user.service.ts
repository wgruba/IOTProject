import { Injectable } from '@angular/core';
import { User } from './models/user.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';



@Injectable({
  providedIn: 'root'
})
export class UserService {
  private currentSavedUser!: User;
  private currentUser = new BehaviorSubject<User>({
    id: '1',
    name: 'Adam',
    mail: 'adam.nowak@gmail.com',
    permissionLevel: "User", 
  });

  constructor(private http: HttpClient) {}

  getUserFromDatabase(id: number): Observable<any> {
    const getUserUrl = `http://localhost:8080/users/${id}`;
    return this.http.get<User>(getUserUrl);
}

setCurrentUser(user: User): void {
  localStorage.setItem('currentUser', JSON.stringify(user));
}

removeCurrentUser(): void {
  localStorage.removeItem('currentUser');
}

getCurrentUser(): User {
  try {
    const userJson = localStorage.getItem('currentUser');
    if (userJson) {
      return JSON.parse(userJson) as User;
    }
  } catch (error) {
    console.error('Error parsing event data from localStorage', error);
  }
  return  {
    id: '1',
    name: 'Adam',
    mail: 'adam.nowak@gmail.com',
    permissionLevel: "User", 
  };
}


}
