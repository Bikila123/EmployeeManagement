import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RoleType } from 'app/types/RoleType';
import { UserType } from 'app/types/UserType';
import { ValidationCheckType } from 'app/types/ValidationCheckType';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PageUserService {

  apiServerUrl: String = environment.apiBaseUrl;
  phoneNumberTobeChecked: string;

  constructor(private httpClient: HttpClient) { }

  public addUser(user: UserType): Observable<UserType> {
    if (user.phone_number.startsWith('+251')) {
      user.phone_number = '0' + user.phone_number.substr(4);
    }
    return this.httpClient.post<UserType>(`${this.apiServerUrl}/User/addUser`, user);
  }

  getUsers(): Observable<UserType[]> {
    return this.httpClient.get<UserType[]>(`${this.apiServerUrl}/User/getUsers`);
  }

  public updateUser(user: UserType): Observable<UserType> {
    if (user.phone_number.startsWith('+251')) {
      user.phone_number = '0' + user.phone_number.substr(4);
    }
    return this.httpClient.post<UserType>(`${this.apiServerUrl}/User/updateUser`, user);
  }

  public phoneAlreadyExists(phone_number: any) {
    if (phone_number.startsWith('+251')) {
      this.phoneNumberTobeChecked = '0' + phone_number.substr(4);
    } else {
      this.phoneNumberTobeChecked = phone_number;
    }
    return this.httpClient.get<boolean>(
      `${this.apiServerUrl}/User/phoneexists/${this.phoneNumberTobeChecked}`
    );
  }

  public userAlreadyExists(username: any) {
    return this.httpClient.get<boolean>(
      `${this.apiServerUrl}/User/userexists/${username}`
    );
  }

  public emailAlreadyExists(email: any) {
    return this.httpClient.get<boolean>(
      `${this.apiServerUrl}/User/emailexists/${email}`
    );
  }

  public phoneExistsForUpdate(data: ValidationCheckType): Observable<boolean> {
    if (data.unique.startsWith('+251')) {
      data.unique = '0' + data.unique.substr(4);
    }
    return this.httpClient.post<boolean>(`${this.apiServerUrl}/User/phoneexistsforupdate`, data);
  }

  public userExistsForUpdate(data: ValidationCheckType): Observable<boolean> {
    return this.httpClient.post<boolean>(`${this.apiServerUrl}/User/userexistsforupdate`, data);
  }

  public emailExistsForUpdate(data: ValidationCheckType): Observable<boolean> {
    return this.httpClient.post<boolean>(`${this.apiServerUrl}/User/emailexistsforupdate`, data);
  }

  public empIdAlreadyExistsForUpdate(data: ValidationCheckType): Observable<boolean> {
    return this.httpClient.post<boolean>(`${this.apiServerUrl}/User/empIdAlreadyExistsForUpdate`, data);
  }

}
