import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS, HttpErrorResponse } from '@angular/common/http';


import { Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';

import { AuthServiceService } from 'app/pages/pages-login/auth-service.service';
import { StorageService } from 'app/pages/pages-login/storage.service';
import { EventBusService } from 'app/pages/pages-login/event-bus.service';
import { EventData } from 'app/pages/pages-login/event.class';
import { JwtRefreshResponse } from 'app/types/RefreshTokenType';
import { RequestRefreshToken } from 'app/types/RequestRefreshToken';
import { Router } from '@angular/router';

@Injectable()
export class AuthenticationInterceptorService implements HttpInterceptor {
  private isRefreshing = false;
  auth: any;
  userRefreshToken: RequestRefreshToken= new RequestRefreshToken();
  // userRefreshToken:RequestRefreshToken;

  constructor(
    private storageService: StorageService,
    private authService: AuthServiceService,
    private eventBusService: EventBusService,
    private route:Router
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    req = req.clone({
      withCredentials: true,
    });
    this.auth = "Bearer "+this.storageService.getUser().token;
     

    if(this.storageService.isLoggedIn()){
      req = req.clone({
        setHeaders: {
          'Authorization': this.auth
        },
      });
    }

    return next.handle(req).pipe(
      catchError((error) => {
        const errorMessage = error.error?.error || 'Unauthorized';
        if (
          error instanceof HttpErrorResponse &&
          !req.url.includes('auth/signin') &&
          error.status === 401 && errorMessage=="Unauthorized"
        ) {
          return this.handle401Error(req, next);
        }else if(errorMessage=="deviceerror"){
              sessionStorage.removeItem('auth-user');
              this.authService.isAuthenticated = false;
              this.route.navigate(["pages-login"]);
        }

        return throwError(() => error);
      })
    );
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      this.isRefreshing = true;

      if (this.storageService.isLoggedIn()) {
        this.userRefreshToken.refreshToken = this.storageService.getUser().refreshToken;
        return this.authService.refreshToken(this.userRefreshToken).pipe(
          switchMap((res:JwtRefreshResponse) => {
             console.log(res);
            this.isRefreshing = false;
            let parsedData = this.storageService.getUser();

              // Update token and refresh token
              parsedData.refreshToken = res.refreshToken;
              parsedData.token = res.accessToken; 

              let updatedJwt = JSON.stringify(parsedData);
              sessionStorage.setItem("auth-user",updatedJwt);
              this.auth = "Bearer "+this.storageService.getUser().token;
              request = request.clone({
                setHeaders: {
                  'Authorization': this.auth
                },
              });
            return next.handle(request);
          }),
          catchError((error) => {
            this.isRefreshing = false;
            if (error.status == '403' || error.status=='500') {
               this.authService.userLoggedOut(this.storageService.getUser().id).subscribe(
                (response:any)=>{
                  sessionStorage.removeItem('auth-user');
                  this.authService.isAuthenticated = false;
                  sessionStorage.setItem('jwt-expiry', "Session Expired....");
                  this.route.navigate(["pages-login"]);
                },
                (error:HttpErrorResponse)=>{}
               );
              // this.eventBusService.emit(new EventData('logout', null));
            }

            return throwError(() => error);
          })
        );
      }
    }

    return next.handle(request);
  }
}

export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: AuthenticationInterceptorService, multi: true },
];