
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { PagesBlankComponent } from './pages/pages-blank/pages-blank.component';
import { PagesError404Component } from './pages/pages-error404/pages-error404.component';
import { PagesLoginComponent } from './pages/pages-login/pages-login.component';
import { UsersProfileComponent } from './pages/users-profile/users-profile.component';
import { PageUserComponent } from './pages/page-user/page-user.component';
import { PageManageuserComponent } from './pages/page-manageuser/page-manageuser.component';
import { AdminGuard } from './guards/admin.guard';
import { AuthGuard } from './guards/auth.guard';
import { PageChangepasswordComponent } from './pages/page-changepassword/page-changepassword.component';
import { SuperAdminGuard } from './guards/super-admin.guard';
import { ManageMenuComponent } from './UMS/manage-menu/manage-menu.component';
import { ManageRoleComponent } from './UMS/manage-role/manage-role.component';
import { ManageParentMenuComponent } from './UMS/manage-parent-menu/manage-parent-menu.component';
import { UserRoleMappingComponent } from './UMS/user-role-mapping/user-role-mapping.component';
import { AdminDashboardComponent } from './dashboards/admin-dashboard/admin-dashboard.component';
import { WebhomeComponent } from './webbook/webhome/webhome.component';
import { AddBookComponent } from './webbook/Book/add-book/add-book.component';
import { KidistcomponentComponent } from './pages/kidistcomponent/kidistcomponent.component';
import { EmployeeProfile } from './pages/employee-profile/employee-profile';
import { DepartmentComponent } from './pages/department/departmentComponent';
const routes: Routes = [
  { path: '', component: WebhomeComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'pages-blank', component: PagesBlankComponent },
  { path: 'pages-error404', component: PagesError404Component },
  { path: 'pages-login', component: PagesLoginComponent },
  { path: 'user-profile', component: UsersProfileComponent, canActivate: [AuthGuard] },
  {path:'createuser',component:PageUserComponent, canActivate: [AuthGuard,AdminGuard]},
  {path:'manageuser',component:PageManageuserComponent, canActivate: [AuthGuard,AdminGuard]},
  {path:'forgotchangepassword', component:PageChangepasswordComponent},
  {path:'managemenu', component:ManageMenuComponent, canActivate: [AuthGuard,SuperAdminGuard]},
  {path:'managerole', component: ManageRoleComponent, canActivate: [AuthGuard,SuperAdminGuard]},
  {path:'manageparentmenu', component: ManageParentMenuComponent, canActivate: [AuthGuard,SuperAdminGuard]},
  {path:'userrole', component:UserRoleMappingComponent, canActivate: [AuthGuard,AdminGuard]},
  {path:'admindashboard', component: AdminDashboardComponent, canActivate: [AuthGuard,AdminGuard]},
  {path:'addbook', component:AddBookComponent, canActivate:[AuthGuard]},
  {path:'webhome', component:WebhomeComponent},
  // {path:'addEmployee', component:KidistcomponentComponent},
  {path:'getEmployees', component:KidistcomponentComponent},
  {path:'manageemployee', component:KidistcomponentComponent},
  {path:'emplyeeprofile', component:EmployeeProfile},
  {path:'managedepartment', component: DepartmentComponent}
 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
