import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BranchType } from 'app/types/BranchType';
import { EmployeeType } from 'app/types/EmployeeType';
import { MessageResponse } from 'app/types/MessageType';
import { PositionType } from 'app/types/PositionType';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class KidistService {
  apiBaseUrl:string = environment.apiBaseUrl;
  constructor(private http:HttpClient) { }

  fetchBranchs():Observable<BranchType[]> {
    return this.http.get<BranchType[]>(`${this.apiBaseUrl}/employee/getBranch`);
  }

  fetchPositions():Observable<PositionType[]> {
    return this.http.get<PositionType[]>(`${this.apiBaseUrl}/employee/getPosition`);
  }

  addEmployee(data:EmployeeType):Observable<MessageResponse>{
    return this.http.post<MessageResponse>(`${this.apiBaseUrl}/employee/addEmployee`,data);
  }

  getEmployees():Observable<EmployeeType[]>{
    return this.http.get<EmployeeType[]>(`${this.apiBaseUrl}/employee/getEmployees`);
  }

  updateEmployee(data:EmployeeType):Observable<MessageResponse>{
    return this.http.put<MessageResponse>(`${this.apiBaseUrl}/employee/updateEmployee`,data);
  }

  // deleteEmployee(empid: string):Observable<MessageResponse>{
  //   return this.http.delete<MessageResponse>(`${this.apiBaseUrl}/employee/deleteEmployee`,empid);
  // }
}
