import { Component ,OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { User } from '../models/user.model';
import { MatDialog } from '@angular/material/dialog';
import { ChangingPasswordModalComponent } from '../changing-password-modal/changing-password-modal.component';


@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit{
  user !: User;

  constructor(private userService: UserService, public dialog: MatDialog) {}

  ngOnInit(): void {
   this.user = this.userService.getCurrentUser();
  }

  // Method to handle password change
  changePassword(): void {
    const dialogRef = this.dialog.open(ChangingPasswordModalComponent, {
      width: '350px',
      data: {title: 'Zmień hasło', message: 'Zmień swoje hasło'}
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        
      }
    });
  }
  
}
