import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MenuType } from 'app/types/MenuType';
import { ParentMenuType } from 'app/types/ParentMenuType';
import { RoleType } from 'app/types/RoleType';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ManageMenuService {


  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http:HttpClient) { }
  fetchMenus():Observable<MenuType[]> {
     return this.http.get<MenuType[]>(`${this.apiServerUrl}/menu/getmenus`);
  }
  fetchRoles():Observable<RoleType[]> {
    return this.http.get<RoleType[]>(`${this.apiServerUrl}/menu/getroles`);
  }
  fetchParentMenus():Observable<ParentMenuType[]> {
    return this.http.get<ParentMenuType[]>(`${this.apiServerUrl}/menu/getparentmenus`);
  }
  addMenu(menu:MenuType):Observable<MenuType> {
    return this.http.post<MenuType>(`${this.apiServerUrl}/menu/addMenu`,menu);
  }
  onUpdateMenu(menu:MenuType):Observable<MenuType> {
    return this.http.put<MenuType>(`${this.apiServerUrl}/menu/updateMenu`,menu);
  }
}
