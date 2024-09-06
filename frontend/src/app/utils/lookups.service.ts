import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageResponse } from 'app/types/MessageType';
import { TypeDto } from 'app/types/TypeDto';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LookupsService {


  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }


  fetchChargeType(): Observable<TypeDto[]> {
    return this.http.get<TypeDto[]>(`${this.apiServerUrl}/lookups/fetchChargeType`);
  }

  fetchCollateralType(): Observable<TypeDto[]> {
    return this.http.get<TypeDto[]>(`${this.apiServerUrl}/lookups/fetchCollateralType`);
  }

  fetchCommissionType(): Observable<TypeDto[]> {
    return this.http.get<TypeDto[]>(`${this.apiServerUrl}/lookups/fetchCommissionType`);
  }

  fetchGuaranteeType(): Observable<TypeDto[]> {
    return this.http.get<TypeDto[]>(`${this.apiServerUrl}/lookups/fetchGuaranteeType`);
  }

  fetchLimitType(): Observable<TypeDto[]> {
    return this.http.get<TypeDto[]>(`${this.apiServerUrl}/lookups/fetchLimitType`);
  }

  //add guarantee type 
  addGuaranteeType(formValue: any): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(`${this.apiServerUrl}/lookups/addGuaranteeType`, formValue);
  }

  //update guarantee type
  updateGuaranteeType(formValue: any): Observable<MessageResponse> {
    return this.http.put<MessageResponse>(`${this.apiServerUrl}/lookups/updateGuaranteeType`, formValue);
  }

  //add collateral type
  addCollateralType(formValue: any): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(`${this.apiServerUrl}/lookups/addCollateralType`, formValue);
  }

  //update collateral type
  updateCollateralType(formValue: any): Observable<MessageResponse> {
    return this.http.put<MessageResponse>(`${this.apiServerUrl}/lookups/updateCollateralType`, formValue);
  }

  //add laf approver
  addLAFApprover(formValue: any): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(`${this.apiServerUrl}/lookups/addLAFApprover`, formValue);
  }

  //update laf approver
  updateLAFApprover(formValue: any): Observable<MessageResponse> {
    return this.http.put<MessageResponse>(`${this.apiServerUrl}/lookups/updateLAFApprover`, formValue);
  }

  //add segment
  addSegment(formValue: any): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(`${this.apiServerUrl}/lookups/addSegment`, formValue);
  }

  //update segment
  updateSegment(formValue: any): Observable<MessageResponse> {
    return this.http.put<MessageResponse>(`${this.apiServerUrl}/lookups/updateSegment`, formValue);
  }



}
