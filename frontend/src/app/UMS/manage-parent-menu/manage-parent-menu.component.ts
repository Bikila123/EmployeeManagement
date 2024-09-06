import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ParentMenuService } from './parent-menu.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ParentMenuType } from 'app/types/ParentMenuType';
import Swal from 'sweetalert2';
import { ManageMenuService } from '../manage-menu/manage-menu.service';
import { RoleType } from 'app/types/RoleType';

@Component({
  selector: 'app-manage-parent-menu',
  templateUrl: './manage-parent-menu.component.html',
  styleUrls: ['./manage-parent-menu.component.css']
})
export class ManageParentMenuComponent implements OnInit {

  formMenu!: FormGroup;
  formMenuUpdate!: FormGroup;
  submitted = false;
  data: any = [];
  message: any;
  loading: boolean = true;
  role: RoleType[] = [];
  roleName: string;
  rolesdata: RoleType[] = [];
  visible: boolean = false;
  updateVisible: boolean = false;


  constructor(private menuService: ParentMenuService, private formBuilder: FormBuilder, private managemenuService: ManageMenuService) { }

  ngOnInit(): void {

    this.fetchParentMenus();
    this.fetchRoles();

    this.formMenu = this.formBuilder.group({
      item_name: ['', Validators.required],
      parent_icon: ['', Validators.required],
      role_id: ['', Validators.required],
    });

    this.formMenuUpdate = this.formBuilder.group({
      id: [''],
      item_name: ['', Validators.required],
      parent_icon: ['', Validators.required],
      role_id: ['', Validators.required],
    });

  }

  get f() {
    return this.formMenu.controls;
  }

  get ftwo() {
    return this.formMenuUpdate.controls;
  }

  fetchParentMenus() {
    this.menuService
      .fetchParentMenus()
      .subscribe((ret: ParentMenuType[]) => {
        this.data = ret;
        this.loading = false;
      },
        (error: HttpErrorResponse) => {
          this.loading = false;
        });
  }
  fetchRoles() {
    this.managemenuService
      .fetchRoles()
      .subscribe((ret: RoleType[]) => {
        this.rolesdata = ret;
      },
        (error: HttpErrorResponse) => {
        });
  }

  getRolesName(id: any) {
    this.role = this.rolesdata?.filter(x => x.id === id);
    this.roleName = this.role[0]?.description;
    return this.roleName;
  }

  showDialog(action: any, payload: any) {
    if (action == 'add') {
      this.visible = true;
    } else if (action == 'edit') {
      this.updateVisible = true;
      this.formMenuUpdate.patchValue(payload);
    }
  }
  onAddParentMenu() {
    this.submitted = true;
    if (this.formMenu.valid) {
      this.menuService.addParentMenu(this.formMenu.value).subscribe(
        (res: ParentMenuType) => {
          this.visible = false;
          Swal.fire({
            icon: 'success',
            title: 'Prent menu has been saved',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
          });
          this.fetchParentMenus();
          this.submitted = false;
        },
        (error: HttpErrorResponse) => {
          this.visible = false;
          Swal.fire({
            icon: 'error',
            title: 'Parent menu could not be saved',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
          });
        }
      );
    }

  }

  public onUpdateParentMenu(): void {
    this.submitted = true;
    if (this.formMenuUpdate.valid) {
      this.menuService.updateParentMenu(this.formMenuUpdate.value).subscribe(
        (response: ParentMenuType) => {
          this.updateVisible = false;
          Swal.fire({
            icon: 'success',
            title: 'Parent menu has been updated',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
          });
          this.fetchParentMenus();
          this.submitted = false;
        },
        (error: HttpErrorResponse) => {
          this.updateVisible = false;
          Swal.fire({
            icon: 'error',
            title: 'Parent menu could not be updated',
            showConfirmButton: true,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3B71CA',
          });
        }
      );
    }

  }

}
