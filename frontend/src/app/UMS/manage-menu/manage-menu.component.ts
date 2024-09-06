import { Component, OnInit } from '@angular/core';
import { ManageMenuService } from './manage-menu.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MenuType } from 'app/types/MenuType';
import { HttpErrorResponse } from '@angular/common/http';
import { ParentMenuType } from 'app/types/ParentMenuType';
import Swal from 'sweetalert2';
import { RoleType } from 'app/types/RoleType';


@Component({
  selector: 'app-manage-menu',
  templateUrl: './manage-menu.component.html',
  styleUrls: ['./manage-menu.component.css']
})
export class ManageMenuComponent implements OnInit {

  submitted = false;
  data: any = [];
  message: any;
  // formCriteria!: FormGroup;
  // formCriteriaUpdate!: FormGroup;
  loading: boolean = true;
  parentmenusdata: ParentMenuType[] = [];
  rolesdata: RoleType[] = [];
  parentMenu: ParentMenuType[] = [];
  parentMenuName: string;
  role: RoleType[] = [];
  roleName: string;
  menuStatus: string;
  severityColor: string;
  visible: boolean = false;
  updateVisible: boolean = false;
  position: string;
  formMenuUpdate!: FormGroup;
  formMenu!: FormGroup;
  selectedRoleId: RoleType | undefined;


  constructor(private menuService: ManageMenuService, private formBuilder: FormBuilder) {
    this.severityColor = 'success';
    this.position = 'top';

  }


  ngOnInit(): void {
    this.fetchMenus();
    this.fetchParentMenus();
    this.fetchRoles();
    this.formMenu = this.formBuilder.group({
      id: [],
      item_name: ['', Validators.required],
      link: [
        '',
        [Validators.required],
      ],
      icon: ['', Validators.required],
      parent_item_id: ['', Validators.required],
      role_id: ['', Validators.required],
      status: [''],
    });
    this.formMenuUpdate = this.formBuilder.group({
      id: [],
      item_name: ['', Validators.required],
      link: [
        '',
        [Validators.required],
      ],
      icon: ['', Validators.required],
      parent_item_id: ['', Validators.required],
      role_id: ['', Validators.required],
      status: [''],
    });
  }

  get f() {
    return this.formMenu.controls;
  }

  get f2() {
    return this.formMenuUpdate?.controls;
  }

  fetchMenus() {
    this.menuService
      .fetchMenus()
      .subscribe((ret: MenuType[]) => {
        this.data = ret;
        this.loading = false;
      },
        (error: HttpErrorResponse) => {
          this.loading = false;
        });
  }
  fetchParentMenus() {
    this.menuService
      .fetchParentMenus()
      .subscribe((ret: ParentMenuType[]) => {
        this.parentmenusdata = ret;
      },
        (error: HttpErrorResponse) => {
        });
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
  getParentMenuName(id: any) {
    this.parentMenu = this.parentmenusdata?.filter(x => x.id === id);
    this.parentMenuName = this.parentMenu[0]?.item_name;
    return this.parentMenuName;
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

  showDialog(action: any, payload: any) {
    if (action == 'add') {
      this.visible = true;
    } else if (action == 'edit') {
      this.updateVisible = true;
      this.formMenuUpdate.patchValue(payload);
    }
  }
  onAddMenu() {
    this.submitted = true;

    if (this.formMenu.valid) {
      this.menuService.addMenu(this.formMenu.value).subscribe(
        (res: MenuType) => {
          this.visible = false;
          Swal.fire({
            icon: 'success',
            title: 'Menu has been saved',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
          });
          this.fetchMenus();
          this.submitted = false;

        },
        (error: HttpErrorResponse) => {
          this.visible = false;
          Swal.fire({
            icon: 'error',
            title: 'Menu could not be saved',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
          });
        }
      );
    }

  }

  onUpdateMenu() {
    this.submitted = true;

    if (this.formMenuUpdate.valid) {
      this.menuService.onUpdateMenu(this.formMenuUpdate.value).subscribe(
        (res: MenuType) => {
          this.updateVisible = false;

          Swal.fire({
            icon: 'success',
            title: 'Menu has been updated',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
          });
          this.fetchMenus();
          this.submitted = false;

        },
        (error: HttpErrorResponse) => {
          this.updateVisible = false;

          Swal.fire({
            icon: 'error',
            title: 'Menu could not be updated',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
          });
        }
      );
    }
  }
}
