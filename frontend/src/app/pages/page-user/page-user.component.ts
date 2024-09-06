import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PageUserService } from '../page-manageuser/page-user.service';
import Swal from 'sweetalert2';
import { HttpErrorResponse } from '@angular/common/http';
import { UserType } from 'app/types/UserType';
import { Router } from '@angular/router';
import { ConfirmedValidator } from '../page-manageuser/ConfirmedValidator';
import { RoleType } from 'app/types/RoleType';

@Component({
  selector: 'app-page-user',
  templateUrl: './page-user.component.html',
  styleUrls: ['./page-user.component.css']
})
export class PageUserComponent implements OnInit {
  [x: string]: any;
  form!: FormGroup;
  phoneexist: boolean = false;
  emailexist: boolean = false;
  submitted = false;
  roles:RoleType[]=[];
  

  constructor(private service: PageUserService,
    private route: Router,
    private formBuilder: FormBuilder,
    // private signupService: SignupUserService,
    // private verifyService:VerifyService,
  ) { }

  ngOnInit(): void {
    // this.getRoles();
    
    this.form = this.formBuilder.group({
      id: [''],
      // this.emailAlreadyExists.bind(this)
      username: ['', [Validators.required]],
      first_name: ['', Validators.required],
      last_name: ['', Validators.required],
      // this.phoneAlreadyExists.bind(this)
      phone_number: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      password: ['',[Validators.required,Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=.*\\d).*$'),Validators.maxLength(16),Validators.minLength(8)]],
      role_id: ['',Validators.required],
      created_by: [(sessionStorage.getItem('username'))],
      created_date: [''],
      is_loggedin: [''],
      is_enabled: [''],
      otp: [''],
      confirmpassword: ['', Validators.required]
    },
      {
        validator: ConfirmedValidator('password', 'confirmpassword')
      }
    );

  }


  get f() {
    return this.form.controls;
  }

  // phoneAlreadyExists(control: AbstractControl) {
  //   this.phoneexist = false;
  //   if (control.value) {
  //     this.signupService
  //       .phoneAlreadyExists(control.value)
  //       .subscribe((response) => {
  //         if (response == true) {
  //           this.phoneexist = true;
  //           this.f['phone_number'].setErrors({ phoneexist: true });
  //         }
  //       });
  //   }
  // }

  // emailAlreadyExists(control: AbstractControl) {
  //   this.emailexist = false;
  //   if (control.value) {
  //     this.signupService
  //       .emailAlreadyExists(control.value)
  //       .subscribe((response) => {
  //         if (response == true) {
  //           this.emailexist = true;
  //           this.f['username'].setErrors({ emailexist: true });
  //         } else {
  //           this.emailexist = false;
  //         }
  //       });
  //   }
  // }

   addUser() {
    this.submitted = true;
    if (this.form.valid) {
      this.service.addUser(this.form.value).subscribe(
        (response: UserType) => {
          Swal.fire(
            'Great!',
            'You have successfully created a new user.',
            'success'
          )
          this.route.navigate(['/manageuser']);
        },
        (error: HttpErrorResponse) => {
          Swal.fire(
            'Oops...',
            'Something went wrong, try again!',
            'error'
          )
        }
      );
    } 
  }

  // getRoles() {
  //   this.service.getRoles().subscribe(
  //     (response: Role[]) => {
  //       this.roles = response;
  //     },
  //     (error: HttpErrorResponse) => {
  //       alert(error.message);
  //     }
  //   );
  // }
}