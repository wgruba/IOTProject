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
   this.user = this.userService.getCurrentUser();
  }

  // Method to handle password change
  changePassword(): void {
    //password change logic
  }
  
}
