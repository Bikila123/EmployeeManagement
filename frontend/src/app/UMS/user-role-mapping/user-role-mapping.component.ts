import { Component, OnInit } from '@angular/core';
import { ManageMenuService } from '../manage-menu/manage-menu.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { StorageService } from 'app/pages/pages-login/storage.service';
import { UserRoleService } from './user-role.service';
import { UserType } from 'app/types/UserType';
import { RoleType } from 'app/types/RoleType';
import { PageUserService } from 'app/pages/page-manageuser/page-user.service';
import { UserRole } from 'app/types/UserRoleType';
import Swal from 'sweetalert2';
import { ConfirmationService, Message, MessageService,ConfirmEventType } from 'primeng/api';
import { MessageResponse } from 'app/types/MessageType';


@Component({
  selector: 'app-user-role-mapping',
  templateUrl: './user-role-mapping.component.html',
  styleUrls: ['./user-role-mapping.component.css']
})
export class UserRoleMappingComponent implements OnInit {
  submitted = false;
  data: any = [];
  message: any;
  formUserRoleUpdate!: FormGroup;
  formDeleteUserRole!: FormGroup;
  loading: boolean = true;
  rolesdata: RoleType[] = [];
  usersdata: UserType[] = [];
  userRoledata: UserRole[] = [];
  role: RoleType[] = [];
  roleName: string;
  menuStatus: string;
  severityColor: string;
  visible: boolean = false;
  visibleInternalMessage=false;
  updateVisible: boolean = false;
  position: string;
  // formMenuUpdate!: FormGroup;
  formUserRole!: FormGroup;
  selectedRoleId: RoleType | undefined;
  deletedId:number;
  constructor(private menuService: ManageMenuService,
    private formBuilder: FormBuilder,
    private storageService: StorageService,
    private userService: PageUserService,
    private userRoleService: UserRoleService,
    private messageService: MessageService,
    private confirmationService:ConfirmationService
  ) { }
  ngOnInit(): void {
    this.fetchRoles();
    this.fetchUsers();
    this.fetchUserRoleMappings();
    this.formUserRole = this.formBuilder.group(
      {
        id: [''],
        role_id: ['', Validators.required],
        user_id: ['', Validators.required],
        created_by:[this.storageService.getUser().id]
      }
    );
    this.formUserRoleUpdate = this.formBuilder.group(
      {
        id: [''],
        role_id: ['', Validators.required],
        user_id: ['', Validators.required],
        modified_by:[this.storageService.getUser().id]
      }
    );

    this.formDeleteUserRole = this.formBuilder.group(
      {
        id: [''],
        role_id: ['', Validators.required],
        user_id: ['', Validators.required],
        deleted_by:[this.storageService.getUser().id]
      }
    );
  }

  get f() {
    return this.formUserRole.controls;
  }
  get f2() {
    return this.formUserRole.controls;
  }
  fetchRoles() {
    this.menuService
      .fetchRoles()
      .subscribe((ret: RoleType[]) => {
        this.rolesdata = ret;
      },
        (error: HttpErrorResponse) => {
        });
  }
  fetchUsers() {
    this.userService
      .getUsers()
      .subscribe((ret: UserType[]) => {
        this.usersdata = ret;
      },
        (error: HttpErrorResponse) => {
        });
  }
  fetchUserRoleMappings() {
    this.userRoleService
      .fetchUserRoleMappings()
      .subscribe((ret: UserRole[]) => {
        this.data = ret;
        this.loading = false;
      },
        (error: HttpErrorResponse) => {
        });
  }

  getRolesName(id: any) {
    this.role = this.rolesdata?.filter(x => x.id === id);
    this.roleName = this.role[0]?.description;
    return this.roleName;
  }
  getStatus(status: number) {
    if (status === 0) {
      this.menuStatus = "Deactive";

    } else if (status === 1) {
      this.menuStatus = "Active";
    }
    return this.menuStatus;
  }

  getStatusColor(status: number) {
    if (status === 0) {
      this.severityColor = 'danger';

    } else if (status === 1) {
      this.severityColor = 'success';
    }
    return this.severityColor;
  }

  confirm() {
    this.confirmationService.confirm({
        header: 'User Role Mapping Delete Confirmation',
        message: 'Please confirm to proceed moving forward.',
        acceptIcon: 'pi pi-check mr-2',
        rejectIcon: 'pi pi-times mr-2',
        rejectButtonStyleClass: 'p-button-sm',
        acceptButtonStyleClass: 'p-button-outlined p-button-sm',
        accept: () => {
          this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'You have accepted', life: 3000 });
           this.onDeleteRoleUser();
        },
        reject: () => {
            this.messageService.add({ severity: 'error', summary: 'Rejected', detail: 'You have rejected', life: 3000 });
        }
    });
}

  showDialog(action: any, payload: any) {
    if (action == 'add') {
      this.visible = true;
    } else if (action == 'edit') {
      this.updateVisible = true;
      this.formUserRoleUpdate.patchValue(payload);
    } else if(action=='delete'){
          this.formDeleteUserRole.patchValue(payload);
          this.confirm();
    }
  }
  clearForm() {
    this.formUserRole.reset();
    this.visible = false;
  }
  clearUpdateForm(){
    this.submitted=false;
    this.formUserRoleUpdate.reset();
    this.updateVisible=false;
  }
  onAddUserRole() {
    this.submitted = true;
    if (this.formUserRole.valid) {
      this.userRoleService.onAddUserRole(this.formUserRole.value).subscribe(
        (ret: UserRole) => {

          if (ret == null) {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Oops! User Role Mapping Already Exists!' });
          } else {
            this.visible = false;
            this.fetchUserRoleMappings();
            this.formUserRole.reset;
            this.messageService.add({ severity: 'success', summary: 'Success', detail: 'User Role Mapping Successfully Saved!' });
          }
        },
        (error: HttpErrorResponse) => {
          this.visible = false;
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Oops! Something went wrong!' });
        }
      );
    }
  }
  onUpdateUserRole(){
    this.submitted = true;
    if (this.formUserRoleUpdate.valid) {
      this.userRoleService.onUpdateUserRole(this.formUserRoleUpdate.value).subscribe(
        (ret: MessageResponse) => {
          if (ret == null) {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Oops! User Role Mapping Already Exists!' });
          } else {
            this.updateVisible = false;
            this.fetchUserRoleMappings();
            this.formUserRole.reset;
            this.messageService.add({ severity: 'success', summary: 'Success', detail: 'User Role Mapping Successfully Updated!' });
          }
        },
        (error: HttpErrorResponse) => {
          this.updateVisible = false;
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Oops! Something went wrong!' });
        }
      );
    }
  }
  onDeleteRoleUser(){
    this.userRoleService.onDeleteRoleUser(this.formDeleteUserRole.value).subscribe(
        (ret:MessageResponse)=>{
             this.messageService.add({ severity: 'success', summary: 'Deleted', detail: 'You have deleted', life: 3000 });
              this.fetchUserRoleMappings();
              this.formDeleteUserRole.reset;
        },
        (error:HttpErrorResponse)=>{

        }
    );
  }
}
