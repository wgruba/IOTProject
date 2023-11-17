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

  constructor(private authService: AuthenticationService, private router: Router) {}

  onSubmit(): void {
    // Replace with actual authentication logic
    if (this.username && this.password) {
      this.authService.login('userToken');
      this.router.navigate(['/']); // Navigate to the home page or dashboard after login
    }
  }

}
