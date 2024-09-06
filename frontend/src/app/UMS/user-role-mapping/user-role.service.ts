import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageResponse } from 'app/types/MessageType';
import { UserRole } from 'app/types/UserRoleType';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserRoleService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http:HttpClient) { }
  fetchUserRoleMappings():Observable<UserRole[]> {
   return this.http.get<UserRole[]>(`${this.apiServerUrl}/User/userrole`);
  }
  onAddUserRole(value: UserRole):Observable<UserRole> {
     return this.http.post<UserRole>(`${this.apiServerUrl}/User/mapuserrole`,value);
  }
  onUpdateUserRole(value: any):Observable<MessageResponse> {
    return this.http.put<MessageResponse>(`${this.apiServerUrl}/User/updateMapuserrole`,value);
  }
  onDeleteRoleUser(value: any):Observable<MessageResponse> {
    return this.http.post<MessageResponse>(`${this.apiServerUrl}/User/deleteMapuserrole`,value);
  }
}
