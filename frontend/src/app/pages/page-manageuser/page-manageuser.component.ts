
import { Component, OnInit, ViewChild } from '@angular/core';
import { UserType } from 'app/types/UserType';
import { PageUserService } from './page-user.service';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RoleType } from 'app/types/RoleType';
import { MenuItem } from 'primeng/api';
import { ConfirmedValidator } from './ConfirmedValidator';
import { StorageService } from '../pages-login/storage.service';
import { ValidationCheckType } from 'app/types/ValidationCheckType';
import { LookupsService } from 'app/utils/lookups.service';
import { PositionType } from 'app/types/PositionType';
// import { SignupUserService } from '../pages-register/signup-user.service';
import { ManageMenuService } from 'app/UMS/manage-menu/manage-menu.service';

@Component({
  selector: 'app-page-manageuser',
  templateUrl: './page-manageuser.component.html',
  styleUrls: ['./page-manageuser.component.css']
})
export class PageManageuserComponent implements OnInit {

  formUser!: FormGroup;
  formUserUpdate!: FormGroup;
  submitted = false;
  updatesubmitted = false;
  users: UserType[] = [];
  message: any;
  loading: boolean = true;
  visible: boolean = false;
  updateVisible: boolean = false;
  activateVisible: boolean = false;
  deactivateVisible: boolean = false;
  phoneexist: boolean = false;
  userexist: boolean = false;
  emailexist: boolean = false;
  username!: any
  validationData: ValidationCheckType = new ValidationCheckType();
  id!: any;
  positions:PositionType[]=[];
  empIdHrexists:boolean=true;
  empIdexists: boolean=false;
  existEmpId: string;
  rolesdata: RoleType[] = [];


  constructor(
    private service: PageUserService,
    private formBuilder: FormBuilder,
    private storageService: StorageService,
    private lookupService:LookupsService,
    // private signupService:SignupUserService,
    private menuService:ManageMenuService
  ) { }

  ngOnInit(): void {
    this.getUsers();
    // this.getPositions();
    this.fetchRoles();

    this.username = this.storageService.getUser().username;
    this.id = this.storageService.getUser().id
  
    this.formUser = this.formBuilder.group({
      id: [''],
      first_name: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]],
      middle_name: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]],
      last_name: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]],
      email: ['', [Validators.required, this.emailAlreadyExists.bind(this),Validators.email]],
      username: ['', [Validators.required, this.userAlreadyExists.bind(this)]],
      phone_number: ['', [Validators.required, Validators.pattern('^(\\+2519|\\+2517|09|07)[0-9]{8}$'), this.phoneAlreadyExists.bind(this)]],
      // password: ['', [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=.*\\d).*$'), Validators.maxLength(16), Validators.minLength(8)]],
      // confirmpassword: ['', Validators.required],
      created_by: [this.id],
      created_date: [''],
      role_id:['',Validators.required],
      // branch_id:['', Validators.required],
      empId:['', [Validators.required]],  //this.empIdAlreadyExists.bind(this), this.empIdNotExistInHr.bind(this)
    },
      // {
      //   validator: ConfirmedValidator('password', 'confirmpassword')
      // }
    );

    this.formUserUpdate = this.formBuilder.group({
      id: [''],
      first_name: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]],
      middle_name: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]],
      last_name: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]],
      email: ['', [Validators.required, this.emailExistsForUpdate.bind(this),Validators.email]],
      phone_number: ['', [Validators.required, Validators.pattern('^(\\+2519|\\+2517|09|07)[0-9]{8}$'), this.phoneExistsForUpdate.bind(this)]],
      modified_by: [this.id],
      modified_date: [''],
      is_enabled: [''],
      is_locked:[''],
      username: [''],
      // branch_id:['', Validators.required],
      empId:['', [Validators.required]],  //this.empIdNotExistInHr.bind(this), this.empIdAlreadyExistsForUpdate.bind(this)
      role_id:['',Validators.required],
    });
  }


  get f() {
    return this.formUser.controls;
  }

  get ftwo() {
    return this.formUserUpdate.controls;
  }

  // getBranchs(){
  //   this.lookupService.fetchBranchs().subscribe(
  //     (response:BranchType[])=>{
  //       this.units = response;
  //     },
  //     (error:HttpErrorResponse)=>{}
  //   );
  // }
  
  // getRegions(){
  //   this.lookupService.fetchRegion().subscribe(
  //     (response:RegionType[])=>{
  //       this.regions = response;
  //     },
  //     (error:HttpErrorResponse)=>{}
  //   );
  // }
  

  fetchRoles() {
    this.menuService
      .fetchRoles()
      .subscribe((ret: RoleType[]) => {
        this.rolesdata = ret;
      },
        (error: HttpErrorResponse) => {
        });
  }

  // empIdAlreadyExists(control: AbstractControl) {
  //   this.empIdexists = false;
  //   if (control.value) {
  //     this.signupService
  //       .empIdAlreadyExists(control.value)
  //       .subscribe((response) => {
  //         if (response == true) {
  //           this.empIdexists = true;
  //           //this line here is very crucial to make the form invalid
  //           this.f['empId'].setErrors({ empIdexists: true });
  //         }else{
  //           this.empIdexists=false;
  //         }
  //       });
  //   }
  // }

  // empIdAlreadyExistsForUpdate(control: AbstractControl) {
  //   this.empIdexists = false;
  //   if (control.value && control.value!=this.existEmpId) { 
  //     this.signupService
  //       .empIdAlreadyExists(control.value)
  //       .subscribe((response) => {
  //         if (response == true) {
  //           this.empIdexists = true;
  //           this.ftwo['empId'].setErrors({ empIdexists: true });
  //         }
  //       });
  //   }
  // }

  // empIdNotExistInHr(control: AbstractControl){
  //   this.empIdHrexists = true;
  //   if (control.value) {
  //     this.signupService
  //       .empIdNotExistInHr(control.value)
  //       .subscribe((response) => {
  //         if (response == true) {
  //           this.empIdHrexists = true;
  //         }else{
  //           this.empIdHrexists=false;
  //           this.f['empId'].setErrors({ empIdHrexists: true });
  //         }
  //       });
  //   }
  // }

  phoneAlreadyExists(control: AbstractControl) {
    this.phoneexist = false;
    if (control.value) {
      this.service
        .phoneAlreadyExists(control.value)
        .subscribe((response) => {
          if (response == true) {
            this.phoneexist = true;
            this.f['phone_number'].setErrors({ phoneexist: true });
          }
        });
    }
  }
 
  userAlreadyExists(control: AbstractControl) {
    this.userexist = false;
    if (control.value) {
      this.service
        .userAlreadyExists(control.value)
        .subscribe((response) => {
          if (response == true) {
            this.userexist = true;
            this.f['username'].setErrors({ userexist: true });
          } else {
            this.userexist = false;
          }
        });
    }
  }

  emailAlreadyExists(control: AbstractControl) {
    this.emailexist = false;
    if (control.value) {
      this.service
        .emailAlreadyExists(control.value)
        .subscribe((response) => {
          if (response == true) {
            this.emailexist = true;
            this.f['email'].setErrors({ emailexist: true });
          } else {
            this.emailexist = false;
          }
        });
    }
  }

  phoneExistsForUpdate(control: AbstractControl) {
    this.phoneexist = false;

    if (control.value) {
      this.validationData.id = this.ftwo['id'].value;
      this.validationData.unique = control.value;
      
      this.service
        .phoneExistsForUpdate(this.validationData)
        .subscribe((response) => {
          if (response == true) {
            this.phoneexist = true;
            this.ftwo['phone_number'].setErrors({ phoneexist: true });
          }
        });
    }
  }

  userExistsForUpdate(control: AbstractControl) {
    this.userexist = false;

    if (control.value) {
      this.validationData.id = this.ftwo['id'].value;
      this.validationData.unique = control.value;
      
      this.service
        .userExistsForUpdate(this.validationData)
        .subscribe((response) => {
          if (response == true) {
            this.userexist = true;
            this.ftwo['username'].setErrors({ userexist: true });
          }
        });
    }
  }

  emailExistsForUpdate(control: AbstractControl) {
    this.emailexist = false;

    if (control.value) {
      this.validationData.id = this.ftwo['id'].value;
      this.validationData.unique = control.value;
      
      this.service
        .emailExistsForUpdate(this.validationData)
        .subscribe((response) => {
          if (response == true) {
            this.emailexist = true;
            this.ftwo['email'].setErrors({ emailexist: true });
          }
        });
    }
  }

  getSeverity(status: string) {
    if (status == '1') {
      return 'success';
    }
    else {
      return 'danger';
    }
  }
  getIcon(status:string){
    if (status == '1') {
      return 'pi pi-check';
    }
    else {
      return 'pi pi-times';
    }
  }
  getIconStatus(status:string){
    if (status == '0') {
      return 'pi pi-check';
    }
    else {
      return 'pi pi-times';
    }
  }
  getValue(value: any) {
    if (value == '1') {
      return 'Yes';
    }
    else {
      return 'No';
    }
  }

  getStatusValue(value: any) {
    if (value == '1') {
      return 'Locked';
    }
    else {
      return 'Active';
    }
  }

  getStatusSeverity(status: string) {
    if (status == '0') {
      return 'success';
    }
    else {
      return 'danger';
    }
  }

  getUsers() {
    this.service.getUsers().subscribe(
      (response: UserType[]) => {
         
        this.users = response;
        this.loading = false;
      },
      (error: HttpErrorResponse) => {
        this.loading = false;
      }
    );
  }


  showUpdateDialog(payload: UserType) {
    this.updateVisible = true;
    this.existEmpId = payload.empId;
    this.formUserUpdate.patchValue(payload);
  }

  showAddDialog() {
    this.visible = true;
  }

  onAddUser() {
    this.submitted = true;
    this.visible = true;

    if (this.formUser.valid) {
      this.service.addUser(this.formUser.value).subscribe(
        (res: UserType) => {
          this.visible = false;
          Swal.fire({
            icon: 'success',
            title: 'User has been saved',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
          });
          this.getUsers();
          this.submitted = false;
        },
        (error: HttpErrorResponse) => {
          this.visible = false;
          this.submitted = false;
          Swal.fire({
            icon: 'error',
            title: 'User could not be saved',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
          });
        }
      );
    }

  }

  public onUpdateUser(): void {
    this.updatesubmitted = true;

    if (this.formUserUpdate.valid) {
      this.ftwo['modified_by'].setValue(this.id);
   
      this.service.updateUser(this.formUserUpdate.value).subscribe(
        (response: UserType) => {
          this.updateVisible = false;
          Swal.fire({
            icon: 'success',
            title: 'User data has been updated',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
          });
          this.getUsers();
          this.updatesubmitted = false;
        },
        (error: HttpErrorResponse) => {
          this.updateVisible = false;
          this.updatesubmitted = false;
          Swal.fire({
            icon: 'error',
            title: 'User data could not be updated',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
          });
        }
      );
    }

  }


}
