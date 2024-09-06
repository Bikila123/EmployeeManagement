import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { StorageService } from 'app/pages/pages-login/storage.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CheckerGuard implements CanActivate {

  constructor(private storageService: StorageService) { }

 
  canActivate() {
    //this is just example
    if ((this.storageService.getUser().roles[0] == "CHECKER") && this.storageService.getUser().expired != true) {
      return true;
    } else {
      return false;
    }
  }

}
