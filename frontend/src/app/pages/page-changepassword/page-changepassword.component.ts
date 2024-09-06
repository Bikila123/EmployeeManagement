import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ForgotpassService } from './forgotpass.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmedValidator } from '../page-manageuser/ConfirmedValidator';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { MessageService } from 'primeng/api';
import { UserType } from 'app/types/UserType';
import { MessageResponse } from 'app/types/MessageType';

@Component({
  selector: 'app-page-changepassword',
  templateUrl: './page-changepassword.component.html',
  styleUrls: ['./page-changepassword.component.css']
})
export class PageChangepasswordComponent {
  form: any;
  phone_number: string;
  submitted = false;
  changetxt = 'Change Password';
  message: any;
  otp: string;
  user: UserType = new UserType();

  constructor(private forgotpassService: ForgotpassService, private _router: Router,
    private formBuilder: FormBuilder, private messageService: MessageService,
    private route: ActivatedRoute,) { }

  ngOnInit() {


    if (this.forgotpassService.phoneNumber != null) {
      this.phone_number = this.forgotpassService.phoneNumber;

    } else {
      this._router.navigate(['/forgotverify']);
    }

    this.otp = this.forgotpassService.otp;

    this.forgotpassService.authenticateResetOtp(this.otp,this.phone_number).subscribe(
      () => {
      },
      (error: any) => {
        this._router.navigate(['/pages-error404']);
      }
    );


    this.form = this.formBuilder.group({
      password: ['', [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=.*\\d).*$'), Validators.minLength(8), Validators.maxLength(16)]],
      confirmpassword: ['', Validators.required],
      phone_number: [this.phone_number || '']
    },
      {
        validator: ConfirmedValidator('password', 'confirmpassword')
      }
    );

    setTimeout(() => {
      if (sessionStorage.getItem("forgotchangepassword") != null) {
        this.message = sessionStorage.getItem("forgotchangepassword");
        this.messageService.add({ severity: 'success', summary: 'Success', detail: this.message, life: 4000 });

        sessionStorage.removeItem("forgotchangepassword");
      }
    }, 0);

  }
  get f() {
    return this.form.controls;
  }

  changepassword() {
    this.submitted = true;
    if (this.form.valid) {
      this.changetxt = 'changing password...';

      this.user.password = this.form.get('password')?.value;
      this.user.otp = this.otp;
      this.user.phone_number = this.form.get('phone_number')?.value;

      this.forgotpassService.changePassword(this.user).subscribe(
        (response: MessageResponse) => {
          console.log("change response:"+response);
          this.changetxt = 'Change Password';
          this.submitted = false;
          if (response.message == 'success') {
            sessionStorage.setItem("resetbyphone", "Password reset successful! Login with your new password");
            this._router.navigate(['/pages-login'])
          } else{
            this.messageService.add({ severity: 'error', summary: 'Error', detail: response.message});
          }
        },
        (error: HttpErrorResponse) => {
          this.changetxt = 'Change Password';
          if(error.status == 400){
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Invalid or expired otp!' });
          }else{
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Oops! Something went wrong!' });
          }
        }
      );
    }
  }

}
