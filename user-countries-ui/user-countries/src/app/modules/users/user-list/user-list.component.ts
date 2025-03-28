import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { UserWebclientService } from '../services/user-webclient.service';
import { User } from '../models/user';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  loading = false;
  errorMessage = '';

  constructor(private userService: UserWebclientService) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.loading = true;
    this.errorMessage = '';

    this.userService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
        this.loading = false;
      },
      error: (error: HttpErrorResponse) => {
        this.handleError(error);
        this.loading = false;
      }
    });
  }

  private handleError(error: HttpErrorResponse): void {
    console.error('Error:', error);
    this.errorMessage = error.status === 0 ? 
      'Unable to connect to the server.' :
      `Server returned error: ${error.status}`;
  }
}