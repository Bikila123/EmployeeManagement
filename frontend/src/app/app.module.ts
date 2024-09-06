import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './layouts/header/header.component';
import { TableModule, TableRadioButton } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { SidebarComponent } from './layouts/sidebar/sidebar.component';
import { UsersProfileComponent } from './pages/users-profile/users-profile.component';
import { PagesLoginComponent } from './pages/pages-login/pages-login.component';
import { PagesError404Component } from './pages/pages-error404/pages-error404.component';
import { PagesBlankComponent } from './pages/pages-blank/pages-blank.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { CommonModule, DatePipe } from '@angular/common';
import { AuthenticationInterceptorService } from './htt-interceptor/Auth-interceptor';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PageUserComponent } from './pages/page-user/page-user.component';
import { PageManageuserComponent } from './pages/page-manageuser/page-manageuser.component';
// import { PageForgotpasswordComponent } from './pages/page-forgotpassword/page-forgotpassword.component';
import { PageChangepasswordComponent } from './pages/page-changepassword/page-changepassword.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ManageMenuComponent } from './UMS/manage-menu/manage-menu.component';
import { ManageRoleComponent } from './UMS/manage-role/manage-role.component';
import { TagModule } from 'primeng/tag';
import { BadgeModule } from 'primeng/badge';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { ManageParentMenuComponent } from './UMS/manage-parent-menu/manage-parent-menu.component';
import { UserRoleMappingComponent } from './UMS/user-role-mapping/user-role-mapping.component';
import { SplitButtonModule } from 'primeng/splitbutton';
import { MessagesModule } from 'primeng/messages';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { RadioButtonModule } from 'primeng/radiobutton';
import { FileUploadModule } from 'primeng/fileupload';
import { SelectButtonModule } from 'primeng/selectbutton';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { CalendarModule } from 'primeng/calendar';
import { CardModule } from 'primeng/card';
import { PaginatorModule } from 'primeng/paginator';
import { SkeletonModule } from 'primeng/skeleton';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { AdminDashboardComponent } from './dashboards/admin-dashboard/admin-dashboard.component';
import { ChartModule } from 'primeng/chart';
import { WebheaderComponent } from './webbook/webheader/webheader.component';
import { WebfooterComponent } from './webbook/webfooter/webfooter.component';
import { WebhomeComponent } from './webbook/webhome/webhome.component';
import { AddBookComponent } from './webbook/Book/add-book/add-book.component';
import { ManageBookComponent } from './webbook/Book/manage-book/manage-book.component';
import { KidistcomponentComponent } from './pages/kidistcomponent/kidistcomponent.component';
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    SidebarComponent,
    DashboardComponent,
    UsersProfileComponent,
     PagesLoginComponent,
    PagesError404Component,
    PagesBlankComponent,
    PageUserComponent,
    PageManageuserComponent,
    PageChangepasswordComponent,
    ManageMenuComponent,
    ManageRoleComponent,
    ManageParentMenuComponent,
    UserRoleMappingComponent,
    AdminDashboardComponent,
    AddBookComponent,
    ManageBookComponent,
    WebheaderComponent,
    WebfooterComponent,
    WebhomeComponent,
    KidistcomponentComponent,
    
  ],
  imports: [
    BrowserModule,
    CommonModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    TableModule,
    ButtonModule,
    TagModule,
    BadgeModule,
    DialogModule,
    DropdownModule,
    SplitButtonModule,
    MessagesModule,
    ToastModule,
    ConfirmDialogModule,
    RadioButtonModule,
    FileUploadModule,
    SelectButtonModule,
    InputTextModule,
    InputNumberModule,
    CalendarModule,
    CardModule,
    PaginatorModule,
    SkeletonModule,
    PdfViewerModule,
    ChartModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptorService,
      multi: true,
    },
    MessageService,
    ConfirmationService,
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
