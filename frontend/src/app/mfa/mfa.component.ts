import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-mfa',
  templateUrl: './mfa.component.html',
  styleUrls: ['./mfa.component.scss']
})
export class MfaComponent {
  username: string = '';
  code: string = '';

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => {
      this.username = params['username'];
    });
  }

  verifyCode() {
    this.http.post<any>('http://localhost:8080/unauthorized/verify', { username: this.username, code: this.code })
      .subscribe({
        next: (response) => {
          if (response.success) {
            this.router.navigate(['/']);
          } else {
            alert('Invalid verification code');
          }
        },
        error: (error) => {
          alert('Invalid verification code');
        }
      });
  }
}
