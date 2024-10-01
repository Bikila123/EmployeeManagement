import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from 'app/utils/login.service';
import { AuthServiceService } from './auth-service.service';
import { JwtResponse } from 'app/types/jwtResponse';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { StorageService } from './storage.service';
import { ConfirmationService, Message, MessageService } from 'primeng/api';

@Component({
  selector: 'app-pages-login',
  templateUrl: './pages-login.component.html',
  styleUrls: ['./pages-login.component.css']
})
export class PagesLoginComponent implements OnInit {
  form!: FormGroup;
  submitted = false;
  public jwt: any = {};
  sessionMessage: Message[];
  public role!: any;
  usernameLogout!: any;
  userName: any;
  usernamePassError!: any;
  srcimg!: string;
  isLoggedIn = false;
  isLoginFailed = false;
  showDialogUserLoggedIn=false;
  errorMessage = '';
  logintext = 'Login';
  roles: string[] = [];
  message: any;
  currentYear: number;
  showSessionErrorMessage:string='';
  showErrorMessage=false;
  constructor(private loginpage: LoginService, private _router: Router,
    private auth: AuthServiceService, private formBuilder: FormBuilder,
    private confirmationService: ConfirmationService,
    private storageService: StorageService, private messageService: MessageService) {

  }

  ngOnInit(): void {
    this.currentYear = new Date().getFullYear();
    this.loginpage.loginpage = true;

    this.sessionMessage = [
      { severity: 'info', summary: 'Info', detail: 'Session Expired...' },
      ];

    if (this.storageService.isLoggedIn()) {
      this.isLoggedIn = true;
      this.roles = this.storageService.getUser().roles;
    }
    if(sessionStorage.getItem("auth-token")){
      this._router.navigate(['dashboard']);
    }

    setTimeout(() => {
      if (sessionStorage.getItem("resetpassword") != null) {
        this.message = sessionStorage.getItem("resetpassword");
        this.messageService.add({ severity: 'success', summary: 'Success', detail: this.message, life: 4000 });

        sessionStorage.removeItem("resetpassword");
      }
    }, 0);
    
    setTimeout(() => {
      if (sessionStorage.getItem("resetbyphone") != null) {
        this.message = sessionStorage.getItem("resetbyphone");
        this.messageService.add({ severity: 'success', summary: 'Success', detail: this.message, life: 4000 });

        sessionStorage.removeItem("resetbyphone");
      }
    }, 0);

    setTimeout(() => {
      if (sessionStorage.getItem("createpassword") != null) {
        this.message = sessionStorage.getItem("createpassword");
        this.messageService.add({ severity: 'success', summary: 'Success', detail: this.message, life: 4000 });

        sessionStorage.removeItem("createpassword");
      }
    }, 0);

    setTimeout(() => {
      if (sessionStorage.getItem("chpassword") != null) {
        this.message = sessionStorage.getItem("chpassword");
        this.messageService.add({ severity: 'success', summary: 'Success', detail: this.message, life: 4000 });

        sessionStorage.removeItem("chpassword");
      }
    }, 0);

  
    if (sessionStorage.getItem("jwt-expiry") != null) {
         this.showError();
    }

   // this.srcimg = 'assets/img/AB Horizontal logo with tag line-01.jpg';
    this.form = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      forcelogout:['']
    });
  }


  get f() {
    return this.form.controls;
  }
  filled(changed: any) {
    this.usernamePassError = false;
  }


  generateJwt() {
    this.submitted = true;
    if (this.form.valid) {
      this.userName = this.form.controls['username'].value;
      this.auth.generate(this.form.value).subscribe(
        (response: JwtResponse) => {
          this.jwt = response.token;
          this.role = response.roles;
          this.auth.isAuthenticated = true;
          //  sessionStorage.setItem('role', this.role);
          sessionStorage.setItem('jwt', this.jwt);
          sessionStorage.setItem('username', this.userName);
          sessionStorage.setItem("loggedIn", "1");
          sessionStorage.setItem('role', this.role);
          this._router.navigate(["/manageemployee"]);
          // this.logintext="Login";
        },
        (error: HttpErrorResponse) => {
          // this.logintext="Login";
          if (error.status === 403) {
            this.usernamePassError = true;
          } else if (error.status === 500) {
            console.log("Server error");
          }
        }
      );
    }
    // sessionStorage.setItem("loggedIn","1");

  }

  confirm() {
    this.confirmationService.confirm({
      header: 'Clear the other session with this user',
      message: 'Please confirm to proceed moving forward.',
      acceptIcon: 'pi pi-check mr-2',
      rejectIcon: 'pi pi-times mr-2',
      rejectButtonStyleClass: 'p-button-sm',
      acceptButtonStyleClass: 'p-button-outlined p-button-sm',
      accept: () => {
        this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'You have accepted', life: 3000 });
        // this.approvenewGuarantee(payload);
        this.f['forcelogout'].setValue("yes");
        this.onSubmit();
      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Cancelled', detail: 'You have canceled', life: 3000 });
      }
    });
  }

    // Method to display Angular p-message component
    showError() {
      this.showErrorMessage = true;
      this.showSessionErrorMessage = sessionStorage.getItem("jwt-expiry") || "Session expired....";
    }

  onSubmit(): void {
    this.submitted=true;
    if (this.form.valid) {
      this.logintext = "Logging...";
      this.auth.generate(this.form.value).subscribe({
        next: data => {
            this.storageService.saveUser(data);
            this.isLoginFailed = false;
            this.isLoggedIn = true;
            this.roles = this.storageService.getUser().roles;
            this._router.navigate(["/dashboard"]);
            this.logintext = "Login";
            sessionStorage.removeItem("jwt-expiry");
            this.showErrorMessage=false;
        },
        error: err => {
          sessionStorage.removeItem("jwt-expiry");
          this.showErrorMessage=false;
          this.errorMessage = err.error.message;
          if(err.error.text=="Locked"){
            this.messageService.add({ severity: 'error', detail: 'User locked out, contact system administrator', life: 3000 }); 
          }else if(err.error.text=="notexist"){
             this.messageService.add({ severity: 'error', detail: 'Incorrect username', life: 3000 });
          }else if(err.error.text=="UserLoggedIn"){
              this.showDialogUserLoggedIn = true;
              this.confirm();
          }else if(err.error.text=="notconnected"){
            this.messageService.add({ severity: 'error', detail: 'AD server is not responding, try again!', life: 3000 });
          }
           else{
            this.messageService.add({ severity: 'error', detail: err.error.text, life: 5000 });
          }
          this.isLoginFailed = true;
          this.logintext = "Login";
        }
      });

    }

  }
  reloadPage(): void {
    window.location.reload();
  }


}
