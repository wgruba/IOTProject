import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { UserService } from '../user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Event } from '../models/event.model';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';


@Component({
  selector: 'app-login-site',
  templateUrl: './login-site.component.html',
  styleUrls: ['./login-site.component.scss']
})
export class LoginSiteComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';
  subscribedEvents!: Event[]

  constructor(private authService: AuthenticationService, private router: Router, public userService: UserService, private snackBar: MatSnackBar) {}

  onSubmit() {
    if (this.username && this.password) {
      this.authService.login(this.username, this.password).subscribe({
        next: (response) => {
          this.login(response)
        },
        error: (error) => {
          this.errorMessage = 'Login failed: ' + error.message;
        }
      });
  }
}

login(response: User){
  if(response && response.token) {  
    this.authService.setIsLoggedIn(true); 
    localStorage.setItem('userToken', response.token);
    localStorage.setItem('username', this.username)
    this.userService.getUserFromDatabase().subscribe(data => {
      this.userService.getSubscribedEvents().subscribe(response => {
        this.subscribedEvents = response;
        const event_ids = this.subscribedEvents.map(event => event.id);
        data['subscribedEvents'] = event_ids

      });
      this.userService.setCurrentUser(data);
      this.snackBar.open("Zalogowano pomyślnie", 'Zamknij', {
        duration: 3000,
        horizontalPosition: 'right',
        verticalPosition: 'top', });
      this.router.navigate(['/home']);
    }, error => console.error(error));
  } else {
    this.errorMessage = 'Login failed: Invalid username or password';
  }
}
}
