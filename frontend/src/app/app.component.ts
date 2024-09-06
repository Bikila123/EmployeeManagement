import { Component ,ElementRef} from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from './utils/login.service';
import { StorageService } from './pages/pages-login/storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'GMS';
  loginpage: boolean | undefined;
  userName!: any;
  role!: any;
  constructor(private elementRef: ElementRef,  public  _router: Router,private isLoginPage:LoginService
    , private storageService:StorageService,) 
  {}
  iswebsite:boolean=true;
  ngOnInit() {
    var s = document.createElement("script");
    s.type = "text/javascript";
    s.src = "../assets/js/main.js";
    this.elementRef.nativeElement.appendChild(s);
    // if(sessionStorage.getItem("loggedIn")=="1"){
    //   this.iswebsite=false;
    // }else{
    //   this.iswebsite=true;
    // }
  }
  isAuthenticated() {
    return this.storageService.isLoggedIn();
  }


}
