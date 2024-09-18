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
  positionTitle: string;
  visible: boolean = false;
  updateVisible: boolean = false;
  activateVisible: boolean = false;
  deactivateVisible: boolean = false;
  updatesubmitted: boolean;
  empid: any;
  emailexist: boolean;
  phoneexist: boolean;
  num: number;
  gender: string;
  id:number;
  prifix: string;
  year: string;
  newId: number;
  


  constructor(
    private service: KidistService,
    private formBuilder: FormBuilder,
   // private storageService: StorageService
  ) { }

  ngOnInit(): void {
    this.getEmployees();
    this.fetchId();
    this.fetchBranchs();
    this.fetchPositions();
    //this.addEmployee();
    //this.empid = this.storageService.getUser().empid

    this.form = this.formBuilder.group(
      {
        empid: ['', Validators.required],
        first_name: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]],
        last_name: ['', [Validators.required, Validators.pattern(/^[A-Za-z\s]+$/)]],
        unit: ['', Validators.required],
        position: ['', Validators.required],
        salary: ['', Validators.required],
        email: ['', [Validators.required, this.emailAlreadyExists.bind(this),Validators.email]],
        phone: ['', [Validators.required, Validators.pattern('^(\\+2519|\\+2517|09|07)[0-9]{8}$'), this.phoneAlreadyExists.bind(this)]],
        gender: ['',Validators.required],
        department: ['', Validators.required]
      }
    );
    this.formEmployeeUpdate = this.formBuilder.group(
      {
        empid: ['', Validators.required],
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
    this.generateId();
    this.visible = true;
  }

  fetchId() {
    this.service.fetchId().subscribe(
      (ret: EmployeeType[]) => {
        this.data = ret;
        console.log(ret);
      },
      (error: HttpErrorResponse) => {
      });
  }

  getId(id: any) {
    this.data = this.data?.filter(x => x.id === id);
    this.id= this.data[11]?.id;
    return this.id;
  }

  public generateEmployeeId() {
    prefix = "AB/";
    year = String.valueOf(LocalDate.now().getYear());
    Optional<Employee> lastEmployee = employeeRepository.findTopByOrderByIdDesc();

    newId = lastEmployee.map(emp -> Integer.parseInt(emp.getEmployeeId().split("/")[1]) + 1).orElse(10000);
    return prefix + newId + "/" + year;
}

  onAddEmployee() {
    this.submitted = true;
    this.visible = true;
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

  showUpdateDialog(payload: EmployeeType) {
    this.updateVisible = true;
   // this.existEmpId = payload.empid;
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
