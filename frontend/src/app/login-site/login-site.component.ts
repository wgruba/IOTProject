import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { UserService } from '../user.service';
import { MatSnackBar } from '@angular/material/snack-bar';



@Component({
  selector: 'app-login-site',
  templateUrl: './login-site.component.html',
  styleUrls: ['./login-site.component.scss']
})
export class LoginSiteComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthenticationService, private router: Router, public userService: UserService, private snackBar: MatSnackBar) {}

  onSubmit() {
    if (this.username && this.password) {
      this.authService.login(this.username, this.password).subscribe({
        next: (response) => {
          if(response && response.token) {  
            this.authService.setIsLoggedIn(true); 
            localStorage.setItem('userToken', response.token);
            localStorage.setItem('username', this.username)
            this.userService.getUserFromDatabase().subscribe(data => {
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
        },
        error: (error) => {
          this.errorMessage = 'Login failed: ' + error.message;
        }
      });
  }
}
}
