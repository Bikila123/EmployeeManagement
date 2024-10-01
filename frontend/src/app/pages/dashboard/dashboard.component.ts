import { Component, OnInit, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { JwtDecoderService } from '../pages-login/jwt-decoder.service';
import { HttpErrorResponse } from '@angular/common/http';
import Chart from 'chart.js/auto';
import { DashboardService } from './dashboard.service';
import Swal from 'sweetalert2';
import { formatDate } from '@angular/common';
import { StorageService } from '../pages-login/storage.service';
import { ChartModule } from 'primeng/chart';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor() { }
    data: any;
    options: any;
    datai: any;
    optionsi: any;
    ngOnInit() {
        this.salaryChart(); 
        // this.branchChart();
    }
salaryChart(){
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');
    
    this.data = {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        datasets: [
            {
                label: '2022',
                data: [5, 19, 10, 10, 25, 15, 10],
                fill: false,
                tension: 0.4,
                borderColor: documentStyle.getPropertyValue('--blue-500')
            },
            {
                label: '2023',
                data: [18, 25, 10, 19, 24, 17, 9],
                fill: false,
                borderDash: [5, 5],
                tension: 0.4,
                borderColor: documentStyle.getPropertyValue('--teal-500')
            },
            {
                label: '2024',
                data: [8, 20, 23, 13, 11, 22, 10],
                fill: true,
                borderColor: documentStyle.getPropertyValue('--orange-500'),
                tension: 0.4,
                backgroundColor: 'rgba(255,167,38,0.2)'
            }
        ]
    };
    
    this.options = {
        maintainAspectRatio: false,
        aspectRatio: 0.6,
        plugins: {
            legend: {
                labels: {
                    color: textColor
                }
            }
        },
        scales: {
            x: {
                ticks: {
                    color: textColorSecondary
                },
                grid: {
                    color: surfaceBorder
                }
            },
            y: {
                ticks: {
                    color: textColorSecondary
                },
                grid: {
                    color: surfaceBorder
                }
            }
        }
    };
}
//   branchChart(){
//     const documentStyle = getComputedStyle(document.documentElement);
//     const textColor = documentStyle.getPropertyValue('--text-color');
//     const surfaceBorder = documentStyle.getPropertyValue('--surface-border');
     
//     this.data= {
//         datasets: [
//             {
//                 data: [11, 16, 7, 3, 14],
//                 backgroundColor: [
//                     documentStyle.getPropertyValue('--red-500'),
//                     documentStyle.getPropertyValue('--green-500'),
//                     documentStyle.getPropertyValue('--yellow-500'),
//                     documentStyle.getPropertyValue('--bluegray-500'),
//                     documentStyle.getPropertyValue('--blue-500')
//                 ],
//                 label: 'My dataset'
//             }
//         ],
//         labels: ['Red', 'Green', 'Yellow', 'Grey', 'Blue']
//     };
    
//     this.options = {
//         plugins: {
//             legend: {
//                 labels: {
//                     color: textColor
//                 }
//             }
//         },
//         scales: {
//             r: {
//                 grid: {
//                     color: surfaceBorder
//                 }
//             }
//         }
//     };

//   }

}


 