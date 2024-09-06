import { Component } from '@angular/core';
import { KidistService } from './kidist.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageResponse } from 'app/types/MessageType';
import { HttpErrorResponse } from '@angular/common/http';
import { BranchType } from 'app/types/BranchType';
import { EmployeeType } from 'app/types/EmployeeType';
import { PositionType } from 'app/types/PositionType';
import Swal from 'sweetalert2';
import { UserType } from 'app/types/UserType';

@Component({
  selector: 'app-kidistcomponent',
  templateUrl: './kidistcomponent.component.html',
  styleUrls: ['./kidistcomponent.component.css']
})
export class KidistcomponentComponent {

  data: EmployeeType[];
  form!: FormGroup;
  formUserUpdate!: FormGroup;
  submitted = false;
  branchdata: BranchType[] = [];
  branchName: string;
  positions: PositionType[] = [];
  positionTitle: string;
  visible: boolean = false;
  updateVisible: boolean = false;
  activateVisible: boolean = false;
  deactivateVisible: boolean = false;
  existEmpId: string;


  constructor(
    private service: KidistService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.getEmployees();
    this.fetchBranchs();
    this.fetchPositions();
    //this.addEmployee();

    this.form = this.formBuilder.group(
      {
        empid: ['', Validators.required],
        first_name: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]],
        last_name: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]],
        unit: ['', Validators.required],
        position: ['', Validators.required],
        salary: ['', Validators.required],
        email: ['', Validators.required],
        phone: ['', Validators.required]
      }
    );
  }

  get f() {
    return this.form.controls;
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



  addEmployee() {
    this.visible=true;
   this.submitted = true;
    if (this.form.valid) {
      this.service.addEmployee(this.form.value).subscribe(
        (res: MessageResponse) => {
          console.log(res.message)
        },
        (err: HttpErrorResponse) => {
          console.log(err)
        }
      );
    } else {
      this.submitted = false;
    }
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
  showUpdateDialog(payload: UserType) {
    this.updateVisible = true;
    this.existEmpId = payload.empId;
    this.formUserUpdate.patchValue(payload);
  }

  updateEmployee(){
  

 }

  onAddEmployee() {
    this.submitted = true;
    if (this.form.valid) {
      this.service.addEmployee(this.form.value).subscribe(
        (EmployeeType) => {
          this.visible = false;
          Swal.fire({
            icon: 'success',
            title: 'Employee has been saved',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
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

}
