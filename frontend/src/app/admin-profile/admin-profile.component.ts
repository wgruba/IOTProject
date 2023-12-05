import { Component } from '@angular/core';
import { User } from '../models/user.model';
import { UserService } from '../user.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['./admin-profile.component.scss']
})
export class AdminProfileComponent {
  user!: User;
  areYouSureVisible = false;
  permissionChange = 0;
  constructor(private route: ActivatedRoute, public userService: UserService) {
  }

  ngOnInit(): void {
    const userName = this.route.snapshot.params['name'];
    this.userService.getUserFromDatabaseByName(userName).subscribe(data => {
      this.user = data;
    }, error => console.error(error));
  }

  changePermission(type: number): void {
    this.permissionChange = type;
    this.areYouSureVisible = true;
    
  }

  changePermissionConfirm(): void {
    // this.userService.changePermission(this.user.id, type).subscribe(data => {
    //   this.user = data;
    // }, error => console.error(error));
  }

  changePermissionCancel(): void {
    this.areYouSureVisible = false;
  }
}
