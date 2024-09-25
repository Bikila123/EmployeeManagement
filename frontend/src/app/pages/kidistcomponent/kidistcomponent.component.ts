import { Component } from '@angular/core';
import { KidistService } from './kidist.service';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageResponse } from 'app/types/MessageType';
import { HttpErrorResponse } from '@angular/common/http';
import { BranchType } from 'app/types/BranchType';
import { EmployeeType } from 'app/types/EmployeeType';
import { PositionType } from 'app/types/PositionType';
import Swal from 'sweetalert2';
import { StorageService } from '../pages-login/storage.service';
import { DepartmentType } from 'app/types/DepartmentType';


@Component({
  selector: 'app-kidistcomponent',
  templateUrl: './kidistcomponent.component.html',
  styleUrls: ['./kidistcomponent.component.css']
})
export class KidistcomponentComponent {

  data: EmployeeType[];
  form!: FormGroup;
  formEmployeeUpdate!: FormGroup;
  submitted = false;
  branchdata: BranchType[] = [];
  branchName: string;
  positions: PositionType[] = [];
  department: DepartmentType[]=[];
  positionTitle: string;
  visible: boolean = false;
  updateVisible: boolean = false;
  activateVisible: boolean = false;
  deactivateVisible: boolean = false;
  updatesubmitted: boolean;
  emailexist: boolean;
  phoneexist: boolean;
  gender!: string;
  departmentName: string;
  selectedFile: File;
  imageName: any;
 

  constructor(
    private service: KidistService,
    private formBuilder: FormBuilder,
  ) { }

  ngOnInit(): void {
    this.getEmployees();
    this.fetchBranchs();
    this.fetchPositions();
    this.fetchDepartment();
    this.form = this.formBuilder.group(
      {
        first_name: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]],
        last_name: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]],
        unit: ['', Validators.required],
        position: ['', Validators.required],
        salary: ['', Validators.required],
        email: ['', [Validators.required, ]],
        phone: ['', [Validators.required, Validators.pattern('^(\\+2519|\\+2517|09|07)[0-9]{8}$'), ]],
        gender: ['',Validators.required],
        department: ['', Validators.required]
      }
    );
    this.formEmployeeUpdate = this.formBuilder.group(
      {
        first_name: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]],
        last_name: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]],
        unit: ['', Validators.required],
        position: ['', Validators.required],
        salary: ['', Validators.required],
        email: ['', Validators.required],
        phone: ['', Validators.required],
        gender: ['',Validators.required],
        department: ['', Validators.required]

      }
    );
  }

  get f() {
    return this.form.controls;
  }

  get ftwo() {
    return this.formEmployeeUpdate.controls;
  }

  fetchBranchs() {
    this.service.fetchBranchs().subscribe(
      (ret: BranchType[]) => {
        this.branchdata = ret;
        console.log(ret);
      },
      (error: HttpErrorResponse) => {
      });
  }

  fetchPositions() {
    this.service
      .fetchPositions()
      .subscribe((ret: PositionType[]) => {
        this.positions = ret;
      },
        (error: HttpErrorResponse) => {
        });
  }

  fetchDepartment() {
    this.service
      .fetchDepartment()
      .subscribe((ret: DepartmentType[]) => {
        this.department = ret;
      },
        (error: HttpErrorResponse) => {
        });
  }

  getBranch(id: any) {
    this.branchdata = this.branchdata?.filter(x => x.id === id);
    this.branchName = this.branchdata[0]?.name;
    return this.branchName;
  }

  getPosition(id: any) {
    this.positions = this.positions?.filter(x => x.id === id);
    this.positionTitle = this.positions[0]?.title;
    return this.positionTitle;
  }

  getDepartment(id: any) {
    this.department = this.department?.filter(x => x.id === id);
    this.departmentName = this.department[0]?.department_name;
    return this.departmentName;
  }
  
  getEmployees() {
    this.service.getEmployees().subscribe(
      (res: EmployeeType[]) => {
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

  onAddEmployee() {
    this.submitted = true;
    this.visible = true;
    console.log(this.selectedFile);
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);
    if (this.form.valid) {
      this.service.addEmployee(this.form.value).subscribe(
        (EmployeeType) => {
          Swal.fire({
            icon: 'success',
            title: 'Employee has been saved',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
            position: 'top-right',
          });
          this.getEmployees();
          this.submitted = false;
        },
        (error: HttpErrorResponse) => {
          this.visible = false;
          this.submitted = false;
              Swal.fire({
                icon: 'error',
                title: 'Employee could not be saved',
                showConfirmButton: true,
                confirmButtonText: 'OK',
                confirmButtonColor: '#3B71CA',
              });
        }
      );
    }
  }
  //gets called when the user selects an image
 onFileChanged(event:any){
    //select file
    this.selectedFile= event.target.files[0];
  }

  //gets called when user clicks on submit to upload the image 
  // onUpload(){
  //   console.log(this.selectedFile);
  //   const uploadImageData = new FormData();
  //   uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

  //   //call to backkend to save image
    
  // }

  // emailAlreadyExists(control: AbstractControl) {
  //   this.emailexist = false;
  //   if (control.value) {
  //     this.service
  //       .emailAlreadyExists(control.value)
  //       .subscribe((response) => {
  //         if (response == true) {
  //           this.emailexist = true;
  //           this.f['email'].setErrors({ emailexist: true });
  //         } else {
  //           this.emailexist = false;
  //         }
  //       });
  //   }
  // }

  // phoneAlreadyExists(control: AbstractControl) {
  //   this.phoneexist = false;
  //   if (control.value) {
  //     this.service
  //       .phoneAlreadyExists(control.value)
  //       .subscribe((response) => {
  //         if (response == true) {
  //           this.phoneexist = true;
  //           this.f['phone_number'].setErrors({ phoneexist: true });
  //         }
  //       });
  //   }
  // }

  showUpdateDialog(payload: EmployeeType) {
    this.updateVisible = true;
    this.formEmployeeUpdate.patchValue(payload);
  }

  onUpdateEmployee(): void {
    this.updatesubmitted = true;
    if (this.formEmployeeUpdate.valid) {
      this.service.updateEmployee(this.formEmployeeUpdate.value).subscribe(
        (EmployeeType): void => {
          this.updateVisible = false;
          Swal.fire({
            icon: 'success',
            title: 'Employee data has been updated',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
          });
          this.getEmployees();
          this.updatesubmitted = false;
        },
        (error: HttpErrorResponse) => {
          this.updateVisible = false;
          this.updatesubmitted = false;
          Swal.fire({
            icon: 'error',
            title: 'Employee data could not be updated',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
          });
        }
      );
    }

  }

}
