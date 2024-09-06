import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BranchType } from 'app/types/BranchType';
import { FetchMenuType } from 'app/types/FetchMenuType';
import { LoginHistory } from 'app/types/LoginHistory';
import { MenuType } from 'app/types/MenuType';
import { JwtRefreshResponse } from 'app/types/RefreshTokenType';
import { RequestRefreshToken } from 'app/types/RequestRefreshToken';
import { UserType } from 'app/types/UserType';
import { UserDetails } from 'app/types/userdetail';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  private apiServerUrl = environment.apiBaseUrl;
  constructor(private http: HttpClient) {}
  isAuthenticated!: boolean ;
  isUserAlreadyLogged!: boolean;
  role: any = this.getRole();
  jwtToken = sessionStorage.getItem('jwt');


  
  // isAuthenticated(): boolean {
  //   return sessionStorage.getItem("loggedIn")==='1'?true:false;
  // }
  public getRole() {
    return sessionStorage.getItem('role');
  }
  //By this I will get JWT if it is successfully authenticated
  public generate(auth: UserDetails): Observable<any> {
   const awashSuffix="@awashbank.com";
    if (!auth.username.endsWith(awashSuffix)) {
      auth.username += awashSuffix;
    }
    return this.http.post<UserDetails>(
      `${this.apiServerUrl}/api/auth/signin`,
      auth
    );
  }


  //To check if the user already logged in
  public isLoggedIn(username: String): Observable<LoginHistory> {
    return this.http.get<LoginHistory>(
      `${this.apiServerUrl}/User/isLogged/${username}`
    );
  }
  public userLoggedOut(id:number){
    return this.http.post(this.apiServerUrl + `/api/auth/signout/${id}`, { }, httpOptions);
  }

 
  refreshToken(userRefreshToken:RequestRefreshToken):Observable<JwtRefreshResponse> {
    console.log(userRefreshToken);
    return this.http.post<JwtRefreshResponse>(this.apiServerUrl + '/api/auth/refreshtoken', userRefreshToken);
  }

  getMenuByRole(roles: string[]) :Observable<FetchMenuType[]>{
    return this.http.post<any>(`${this.apiServerUrl}/api/auth/fetchmenu`,
    roles);
  }

  //Get Branch by user id
  getBranchbyUserId(user_id: number):Observable<BranchType> {
    return this.http.get<BranchType>(
      `${this.apiServerUrl}/User/getBranch/${user_id}`
    );  }

    //get number of days left for expiry
    getNumberOfDaysLeft(user_id:number):Observable<any>{
      return this.http.get<any>(
        `${this.apiServerUrl}/User/getNumberOfDaysLeft/${user_id}`
      );  
    }

}
