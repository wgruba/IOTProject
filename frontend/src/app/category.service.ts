import { Injectable } from '@angular/core';
import { User } from './models/user.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthenticationService } from './authentication.service';
import { Category } from './models/Category.model';
import { CategoryToAdd } from './admin-category/admin-category.component';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient, public authenticationService: AuthenticationService) { }


  getCategoriesFromDatabase(): Observable<any> {
    const getUserUrl = `http://localhost:8080/categories`;
    return this.http.get<User>(getUserUrl);
  }

  addCategory(category: CategoryToAdd): Observable<Category> {
    const url = `http://localhost:8080/addCategory`;
    const headers = this.authenticationService.getHeadersWithToken()
    return this.http.post<Category>(url, category, {headers});
  }

}
