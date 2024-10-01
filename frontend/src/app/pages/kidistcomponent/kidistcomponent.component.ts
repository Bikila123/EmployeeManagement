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
import { Message } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';


@Component({
  selector: 'app-kidistcomponent',
  templateUrl: './kidistcomponent.component.html',
  styleUrls: ['./kidistcomponent.component.css'],
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
  employeeInfoVisible: boolean = false;
  messages: Message[] ;

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
        gender: [''],
        picture:[''],
        department: ['']
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
        gender: [''],
        department: ['']

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
    // console.log(this.selectedFile);
    // const uploadImageData = new FormData();
    // uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);
    console.log("request reached function");
    if (this.form.valid) {
      console.log("the frorm is valid");
      this.service.addEmployee(this.form.value).subscribe(
        (EmployeeType) => {
          this.visible=false;
          this.getEmployees();
          this.submitted = false;
          this.messages=[
            {severity: 'success', summary: 'Success', detail:'Employee Added Successfuly'}
          ]
        },
        (error: HttpErrorResponse) => {
          this.visible = false;
          this.submitted = false;
          this.messages=[
            {severity: 'error', detail:' Unable to add employee'}
          ]
        }
      );
    }else{
      console.log("the form is invalid");
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
    showempinfo(){
      this.employeeInfoVisible=true;
    }
  


  onUpdateEmployee(): void {
    this.updatesubmitted = true;
    if (this.formEmployeeUpdate.valid) {
      this.service.updateEmployee(this.formEmployeeUpdate.value).subscribe(
        (EmployeeType): void => {
          this.updateVisible = false;
          this.messages=[
            {severity: 'success', summary: 'Success', detail:'Employee Updated Successfuly'}
          ]
          this.getEmployees();
          this.updatesubmitted = false;
        },
        (error: HttpErrorResponse) => {
          this.updateVisible = false;
          this.updatesubmitted = false;
          this.messages=[
            {severity: 'error', detail:' Unable to update employee'}
          ]
        }
      );
    }

  }

}
