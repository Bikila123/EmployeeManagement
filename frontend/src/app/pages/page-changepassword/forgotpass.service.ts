import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageResponse } from 'app/types/MessageType';
import { UserType } from 'app/types/UserType';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ForgotpassService {

  phoneNumber!:any;
  otp!:any;

  apiServerUrl:String = environment.apiBaseUrl;
  phoneNumberTobeChecked: string;

  constructor(private http:HttpClient) { }

  
  changePassword(user:UserType):Observable<MessageResponse>{
    if (user.phone_number.startsWith('+251')) {
      user.phone_number = '0' + user.phone_number.substr(4);
    }
     return this.http.post<MessageResponse>(`${this.apiServerUrl}/api/auth/changeforgotpassword`,user);
  }

  authenticateResetOtp(otp: string, phone_number: string) {
    if (phone_number.startsWith('+251')) {
      this.phoneNumberTobeChecked= '0' + phone_number.substr(4);
  } else {
      this.phoneNumberTobeChecked= phone_number;
  }
    const requestBody = { phone_number: this.phoneNumberTobeChecked};

    return this.http.post<Boolean>(`${this.apiServerUrl}/api/auth/authenticateResetOtp?otp=${otp}`,requestBody);
  }
}
