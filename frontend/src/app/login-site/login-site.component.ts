import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-login-site',
  templateUrl: './login-site.component.html',
  styleUrls: ['./login-site.component.scss']
})
export class LoginSiteComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';


  constructor(private authService: AuthenticationService, private router: Router) {}


  onSubmit() {
    if (this.username && this.password) {
      this.authService.login(this.username, this.password).subscribe({
        next: (response) => {
          if(response && response.token) {  
            this.authService.setIsLoggedIn(true); 
            localStorage.setItem('userToken', response.token);
            localStorage.setItem('username', this.username)
            this.router.navigate(['/home']);
          } else {
            // Handle the case where login is unsuccessful but no error is thrown
            this.errorMessage = 'Login failed: Invalid username or password';
            // Implement logic to show the error popup here
          }
        },
        error: (error) => {
          // Handle error response and show an error message
          this.errorMessage = 'Login failed: ' + error.message;
          // Implement logic to show the error popup here
        }
      });
  }
}
}
