import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { ForgotService } from '../page-forgotpassword/forgot.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { UserType } from 'app/types/UserType';
import { ConfirmedValidator } from '../page-manageuser/ConfirmedValidator';
import { StorageService } from '../pages-login/storage.service';
import { AuthServiceService } from '../pages-login/auth-service.service';

@Component({
  selector: 'app-users-profile',
  templateUrl: './users-profile.component.html',
  styleUrls: ['./users-profile.component.css']
})
export class UsersProfileComponent implements OnInit {
  form!: FormGroup;
  submitted = false;
  oldPass: any;
  newPass: any;
  isSame: any = false;
  user!: UserType;
  oldpassworderror = false;


  constructor(
    private formBuilder: FormBuilder,
    //  private service: ForgotService,
    private router: Router, private storageService: StorageService,private authService:AuthServiceService
  ) { }

  ngOnInit(): void {

    this.form = this.formBuilder.group({
      username: [this.storageService.getUser().username],
      oldPassword: ['', Validators.required],
      newPassword: ['', [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=.*\\d).*$'), Validators.minLength(8), Validators.maxLength(16)]],
      confirmPassword: ['', Validators.required],
    },
      {
        validator: ConfirmedValidator('newPassword', 'confirmPassword')
      }
    );
  }

  get f() {
    return this.form.controls;
  }

  public oldpassFilled(changed: any) {
    this.oldpassworderror = false;
  }

  public newpassFilled(changed: any) {
    this.isSame = false;
  }

  public changePassword() {
    this.submitted = true;
    
    if (this.form.valid) {
      if (this.isOld() == false) {
        // this.service.changePassword(this.form.value).subscribe(
        //   (response: any) => {
        //     this.authService.userLoggedOut(this.storageService.getUser().id).subscribe(
        //       (response:any)=>{
        //         sessionStorage.removeItem('auth-user');
        //         this.authService.isAuthenticated = false;

        //         sessionStorage.setItem("chpassword", "Your password has been successfully changed, Login with the new password!");
        //         this.router.navigate(['/pages-login'])

        //       },
        //       (error:HttpErrorResponse)=>{}
        //      );
        //   },
        //   (error: HttpErrorResponse) => {
        //     this.oldpassworderror = true;
        //   }
        // );
      }
      else {
        this.isSame = true;
      }
    }
    else {
    }
  }

  public isOld() {
    if (this.oldPass == this.newPass) {
      this.isSame = true;
      return true;
    } else {
      return false;
    }
  }

}
