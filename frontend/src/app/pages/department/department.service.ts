import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DepartmentType } from 'app/types/DepartmentType';
import { EmployeeType } from 'app/types/EmployeeType';
import { MessageResponse } from 'app/types/MessageType';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {
  apiBaseUrl:string = environment.apiBaseUrl;
  constructor(private http:HttpClient) { }

  addDepartment(data:DepartmentType):Observable<MessageResponse>{
    return this.http.post<MessageResponse>(`${this.apiBaseUrl}/department/addDepartment`,data);
  }

  getDepartment():Observable<DepartmentType[]>{
    return this.http.get<DepartmentType[]>(`${this.apiBaseUrl}/department/getDepartment`);
  }

}
