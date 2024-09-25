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

  constructor(private elementRef: ElementRef, private _router: Router, private storageService: StorageService) { }
    data: any;

    options: any;

    ngOnInit() {
      const documentStyle = getComputedStyle(document.documentElement);
      const textColor = documentStyle.getPropertyValue('--text-color');

        this.data = {
            labels: ['Female', 'Male'],
            datasets: [
                {
                    data: [50, 100],
                    backgroundColor: [documentStyle.getPropertyValue('--pink-300'), documentStyle.getPropertyValue('--blue-300')],
                    hoverBackgroundColor: [documentStyle.getPropertyValue('--pink-200'), documentStyle.getPropertyValue('--blue-200')]
                }
            ]
        };


        this.options = {
            cutout: '60%',
            plugins: {
                legend: {
                    labels: {
                        color: textColor
                    }
                }
            }
        };
    }


    //     const documentStyle = getComputedStyle(document.documentElement);
    //     const textColor = documentStyle.getPropertyValue('--text-color');
    //     const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    //     const surfaceBorder = documentStyle.getPropertyValue('--surface-border');
        
    //     this.data = {
    //         labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
    //         datasets: [
    //             {
    //                 label: 'First Dataset',
    //                 data: [65, 59, 80, 81, 56, 55, 40],
    //                 fill: false,
    //                 tension: 0.4,
    //                 borderColor: documentStyle.getPropertyValue('--blue-500')
    //             },
    //             {
    //                 label: 'Second Dataset',
    //                 data: [28, 48, 40, 19, 86, 27, 90],
    //                 fill: false,
    //                 borderDash: [5, 5],
    //                 tension: 0.4,
    //                 borderColor: documentStyle.getPropertyValue('--teal-500')
    //             },
    //             {
    //                 label: 'Third Dataset',
    //                 data: [12, 51, 62, 33, 21, 62, 45],
    //                 fill: true,
    //                 borderColor: documentStyle.getPropertyValue('--orange-500'),
    //                 tension: 0.4,
    //                 backgroundColor: 'rgba(255,167,38,0.2)'
    //             }
    //         ]
    //     };
        
    //     this.options = {
    //         maintainAspectRatio: false,
    //         aspectRatio: 0.6,
    //         plugins: {
    //             legend: {
    //                 labels: {
    //                     color: textColor
    //                 }
    //             }
    //         },
    //         scales: {
    //             x: {
    //                 ticks: {
    //                     color: textColorSecondary
    //                 },
    //                 grid: {
    //                     color: surfaceBorder
    //                 }
    //             },
    //             y: {
    //                 ticks: {
    //                     color: textColorSecondary
    //                 },
    //                 grid: {
    //                     color: surfaceBorder
    //                 }
    //             }
    //         }
    //     };
    // }

  //   var s = document.createElement("script");
  //   s.type = "text/javascript";
  //   s.src = "../assets/js/main.js";
  //   this.elementRef.nativeElement.appendChild(s);

  //   this.jwt = sessionStorage.getItem("jwt");
  //   this.role = this.storageService.getUser().roles[0];
  //   this.username = this.storageService.getUser().username;

  //   if (this.storageService.getUser().expired == true) {
  //       this._router.navigate(['user-profile']);
  //   } else {
  //     if (this.role == 'rolefrombackend') {
  //       this._router.navigate(['makerdashboard']);
  //     } 

  //   }
  // 
  }





