import { Component ,OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { User } from '../models/user.model';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit{
  user !: User;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getUserFromDatabase().subscribe(data => {
      this.user = data;
    }, error => console.error(error));
  }

  // Method to handle password change
  changePassword(): void {
    //password change logic
  }

  editName(): void {
    // Logic to handle name editing
  }

  editEmail(): void {
    // Logic to handle email editing
  }
}
