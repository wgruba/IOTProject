import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from '../user.service';
import { AuthenticationService } from '../authentication.service';
import { Event } from '../models/event.model'; // Upewnij się, że importujesz właściwy model

@Component({
  selector: 'app-mfa',
  templateUrl: './mfa.component.html',
  styleUrls: ['./mfa.component.scss']
})
export class MfaComponent {
  username: string = '';
  code: string = '';

  constructor(
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar,
    private userService: UserService,
    private authService: AuthenticationService
  ) {
    this.route.queryParams.subscribe(params => {
      this.username = params['username'];
    });
  }

  verifyCode() {
    this.http.post<any>('https://localhost:8443/unauthorized/verify', { username: this.username, code: this.code })
      .subscribe({
        next: (response) => {
          if (response.token) {
            this.login(response);
          } else {
            alert('Invalid verification code');
          }
        },
        error: (error) => {
          alert('Invalid verification code');
        }
      });
  }

  login(response: any) {
    if (response && response.token) {
      this.authService.setIsLoggedIn(true);
      localStorage.setItem('userToken', response.token);
      localStorage.setItem('username', this.username);
      this.userService.getUserFromDatabase().subscribe(data => {
        this.userService.getSubscribedEvents().subscribe(subscribedEvents => {
          const event_ids = subscribedEvents.map((event: Event) => event.id); // Określenie typu Event
          data['subscribedEvents'] = event_ids;
          this.userService.setCurrentUser(data);
          this.router.navigate(['/home']);
        });
        this.snackBar.open("Zalogowano pomyślnie", 'Zamknij', {
          duration: 3000,
          horizontalPosition: 'right',
          verticalPosition: 'top',
        });
      }, error => console.error(error));
    } else {
      alert('Login failed: Invalid verification code');
    }
  }
}
