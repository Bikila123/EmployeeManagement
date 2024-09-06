import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from 'app/pages/pages-login/storage.service';
import { AdminService } from './admin.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  numberOfUser: number=0;
  numberOfLockedUser: number=0;
  numberOfActiveUser: number=0;
  numberOfonlineUser: number=0;
  constructor(
    private storageService:StorageService,
    private router:Router,
    private adminService:AdminService
  ){
  
  }
  ngOnInit(): void {
    this.getNumberOfUsers();
    this.getNumberOfLockedUsers();
    this.getNumberOfActiveUsers();
    this.getNumberOfOnlineUsers();
  }
  getNumberOfUsers() {
    this.adminService.getNumberOfUsers().subscribe(
      (res: number) => {
        this.numberOfUser = res;
      },
      (error: HttpErrorResponse) => { }
    );
  }
  getNumberOfLockedUsers() {
    this.adminService.getNumberOfLockedUsers().subscribe(
      (res: number) => {
        this.numberOfLockedUser = res;
      },
      (error: HttpErrorResponse) => { }
    );
  }

  getNumberOfActiveUsers() {
    this.adminService.getNumberOfActiveUsers().subscribe(
      (res: number) => {
        this.numberOfActiveUser = res;
      },
      (error: HttpErrorResponse) => { }
    );
  }

  
  getNumberOfOnlineUsers() {
    this.adminService.getNumberOfOnlineUsers().subscribe(
      (res: number) => {
        this.numberOfonlineUser = res;
      },
      (error: HttpErrorResponse) => { }
    );
  }

}
