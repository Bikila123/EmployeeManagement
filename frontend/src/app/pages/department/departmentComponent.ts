  import { Component } from '@angular/core';
  import{ DepartmentService } from './department.service';
  import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
  import { HttpErrorResponse } from '@angular/common/http';
  import Swal from 'sweetalert2';
  import { DepartmentType } from 'app/types/DepartmentType';
  
  @Component({
    selector: 'departmentComponent',
    templateUrl: './departmentComponent.html',
    styleUrls: ['./departmentComponent.css']
  })
  export class DepartmentComponent {
  
    data: DepartmentType[];
    form!: FormGroup;
    submitted = false;
    visible: boolean = false;
    departmentName: string;
   
  
    constructor(
      private service: DepartmentService,
      private formBuilder: FormBuilder,
    ) { }
  
    ngOnInit(): void {
      this.getDepartment();
      this.form = this.formBuilder.group(
        {
            id: [''],
            department_name: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]] 
        }
      );
    }
  
    get f() {
      return this.form.controls;
    }
    
    getDepartment() {
      this.service.getDepartment().subscribe(
        (res: DepartmentType[]) => {
          this.data = res;
        },
        (err: HttpErrorResponse) => {
          console.log(err)
        }
      );
    }
  
    showAddDialog() {
      this.visible = true;
    }
  
    onAddDepartment() {
      this.submitted = true;
      this.visible = true;
      if (this.form.valid) {
        this.service.addDepartment(this.form.value).subscribe(
          (DepartmentType) => {
            Swal.fire({
              icon: 'success',
              title: 'department has been saved',
              showConfirmButton: true,
              confirmButtonText: 'OK',
              confirmButtonColor: '#3B71CA',
              position: 'top-right',
            });
            this.getDepartment();
            this.submitted = false;
          },
          (error: HttpErrorResponse) => {
            this.visible = false;
            this.submitted = false;
                Swal.fire({
                  icon: 'error',
                  title: 'Department could not be saved',
                  showConfirmButton: true,
                  confirmButtonText: 'OK',
                  confirmButtonColor: '#3B71CA',
                });
          }
        );
      }
    }
}