import { Component } from '@angular/core';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { RatingModule } from 'primeng/rating'
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
        
@Component({
    selector: 'employee-profile',
    standalone: true,
    templateUrl: './employee-profile.html',
    styleUrls: ['./employee-profile.css'],
    imports: [CardModule, ButtonModule, ReactiveFormsModule, RatingModule]
})
export class EmployeeProfile {
    formGroup!: FormGroup;

    ngOnInit() {
        this.formGroup = new FormGroup({
            value: new FormControl(4)
        });
    }
}

