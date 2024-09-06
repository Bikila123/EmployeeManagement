import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { StorageService } from 'app/pages/pages-login/storage.service';


@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  
  constructor(private storageService: StorageService) { }

  canActivate() {
    if (this.storageService.getUser().roles[0] == "ROLE_ADMIN" && this.storageService.getUser().expired != true) {
      return true;
    } else {
      return false;
    }
  }
  
}
