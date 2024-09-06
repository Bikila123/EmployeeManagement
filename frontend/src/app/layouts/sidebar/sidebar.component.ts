import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthServiceService } from 'app/pages/pages-login/auth-service.service';
import { JwtDecoderService } from 'app/pages/pages-login/jwt-decoder.service';
import { StorageService } from 'app/pages/pages-login/storage.service';
import { FetchMenuType } from 'app/types/FetchMenuType';
import { MenuType } from 'app/types/MenuType';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
i: any;

  constructor(private storageService:StorageService, private authService:AuthServiceService) { }
  jwt!:any;
  roles: string[] = [];
  menus: FetchMenuType[]=[];
  ngOnInit(): void {

    this.roles = this.storageService.getUser().roles;
    this.getMenuByRole();
  }

  public getMenuByRole(){
        this.authService.getMenuByRole(this.roles).subscribe(
               (res:FetchMenuType[])=>{
                this.menus = res;
               },
               (error:HttpErrorResponse)=>{}
        );
  }

}
