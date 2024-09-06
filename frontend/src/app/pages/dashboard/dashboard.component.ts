import { Component, OnInit, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { JwtDecoderService } from '../pages-login/jwt-decoder.service';
import { HttpErrorResponse } from '@angular/common/http';
import Chart from 'chart.js/auto';
import { DashboardService } from './dashboard.service';
import Swal from 'sweetalert2';
import { formatDate } from '@angular/common';
import { StorageService } from '../pages-login/storage.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  jwt: any;
  role!: any;

  username!: any
  noOfUsers!: any;

  constructor(private elementRef: ElementRef, private _router: Router, private storageService: StorageService) { }

  ngOnInit(): void {

    var s = document.createElement("script");
    s.type = "text/javascript";
    s.src = "../assets/js/main.js";
    this.elementRef.nativeElement.appendChild(s);

    this.jwt = sessionStorage.getItem("jwt");
    this.role = this.storageService.getUser().roles[0];
    this.username = this.storageService.getUser().username;

    if (this.storageService.getUser().expired == true) {
        this._router.navigate(['user-profile']);
    } else {
      if (this.role == 'rolefrombackend') {
        this._router.navigate(['makerdashboard']);
      } 

    }
  }


}


