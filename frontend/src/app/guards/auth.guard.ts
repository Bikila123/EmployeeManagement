import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthServiceService } from 'app/pages/pages-login/auth-service.service';


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthServiceService, private route: Router) { }

  canActivate() {

    if (sessionStorage.getItem("auth-user") == null) {
      this.route.navigate(['pages-login']);
      return false;

    } else {
      return true;
    }
    
  }
}
