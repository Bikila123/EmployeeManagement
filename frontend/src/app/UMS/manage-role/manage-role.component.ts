import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ManageRoleService } from './manage-role.service';
import Swal from 'sweetalert2';
import { HttpErrorResponse } from '@angular/common/http';
import { RoleType } from 'app/types/RoleType';
import { first } from 'rxjs';

@Component({
  selector: 'app-manage-role',
  templateUrl: './manage-role.component.html',
  styleUrls: ['./manage-role.component.css']
})
export class ManageRoleComponent implements OnInit {
  submitted = false;
  data: any = [];
  message: any;
  formRole!: FormGroup;
  formRoleUpdate!: FormGroup;
  loading: boolean = true;
  visible: boolean = false;
  updateVisible: boolean = false;

  constructor(private roleService: ManageRoleService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.fetchRoles();

    this.formRole = this.formBuilder.group({
      description: ['', Validators.required]
    });

    this.formRoleUpdate = this.formBuilder.group({
      id: [''],
      description: ['', Validators.required],
      status: ['', Validators.required]
    });

  }
  get f() {
    return this.formRole.controls;
  }
  get ftwo() {
    return this.formRoleUpdate.controls;
  }

  getSeverity(status: string) {
    if (status == '1') {
      return 'success';
    }
    else {
      return 'danger';
    }
  }
  getValue(value: any) {
    if (value == '1') {
      return 'Active';
    }
    else {
      return 'Deactive';
    }
  }

  fetchRoles() {
    this.roleService
      .fetchRoles()
      .subscribe((ret: RoleType[]) => {
        this.data = ret;
        this.loading = false;
      },
        (error: HttpErrorResponse) => {
          this.loading = false;
        });
  }

  showDialog(action: any, payload: any) {
    if (action == 'add') {
      this.visible = true;
    } else if (action == 'edit') {
      this.updateVisible = true;
      this.formRoleUpdate.patchValue(payload);
    }
  }

  public onUpdateRole(): void {
    this.submitted = true;
    if (this.formRoleUpdate.valid) {
      this.roleService.updateRole(this.formRoleUpdate.value).subscribe(
        (response: RoleType) => {
          this.updateVisible = false;
          Swal.fire({
            icon: 'success',
            title: 'Role has been updated',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
          });
          this.fetchRoles();
          this.submitted = false;
        },
        (error: HttpErrorResponse) => {
          this.updateVisible = false;
          Swal.fire({
            icon: 'error',
            title: 'Role could not be updated',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
          });
        }
      );
    }

  }

  onAddRole(): void {
    this.submitted = true;
    if (this.formRole.valid) {
      this.roleService
        .addRole(this.formRole.value)
        .subscribe({
          next: () => {
            this.visible = false;
            Swal.fire({
              icon: 'success',
              title: 'Role has been saved',
              showConfirmButton: true,
              confirmButtonText: 'OK',
              confirmButtonColor: '#3B71CA',

            });
            this.fetchRoles();
            this.submitted = false;
          },
          error: () => {
            this.visible = false;
            Swal.fire({
              icon: 'error',
              title: 'Role could not be saved',
              showConfirmButton: true,
              confirmButtonText: 'OK',
              confirmButtonColor: '#3B71CA',
            });
          },
        });
    }
  }
}


