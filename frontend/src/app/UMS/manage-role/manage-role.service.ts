import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RoleType } from 'app/types/RoleType';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ManageRoleService {

  apiServerUrl: String = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) { }

  public fetchRoles(): Observable<RoleType[]> {
    return this.httpClient.get<RoleType[]>(
      `${this.apiServerUrl}/Role/fetchAll`
    );
  }

  public updateRole(data: RoleType): Observable<RoleType> {
    return this.httpClient.put<RoleType>(
      `${this.apiServerUrl}/Role/update`,
      data
    );
  }

  addRole(value: RoleType): Observable<any> {
    
    return this.httpClient.post(`${this.apiServerUrl}/Role/add`,value);
  }
}
