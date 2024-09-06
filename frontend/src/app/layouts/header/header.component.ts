import { Component, OnInit, Inject, LOCALE_ID } from '@angular/core';
import { DOCUMENT, DatePipe } from '@angular/common'
import { Router } from '@angular/router';
import { AuthServiceService } from 'app/pages/pages-login/auth-service.service';
import { HttpErrorResponse } from '@angular/common/http';
import { JwtDecoderService } from 'app/pages/pages-login/jwt-decoder.service';
import { Observable, Subscription, interval } from 'rxjs';
import { StorageService } from 'app/pages/pages-login/storage.service';
import { BranchType } from 'app/types/BranchType';
import { NotificationSoundService } from 'app/utils/notification-sound.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  userguide: string="#";
  username!:any;
  id: any;
  numberOfDays:any;
  role: any;
  numberofMakerNotification=0;
  guaranteeToBeExpiredNumber=0;
  numberPassChangeNot=0;
  numberOfDaysLeftForExpiry: number;
  gmessage: string;
  constructor(@Inject(DOCUMENT) private document: Document, private _router:Router,
   private authService:AuthServiceService,@Inject(LOCALE_ID) public locale: string,
   private jwtService:JwtDecoderService, private storageService:StorageService,
   private datePipe: DatePipe,
   private soundService:NotificationSoundService
   ) { }
   page!:any;
   jwt!:any;
   currentDate: Date;
   branch:BranchType=new BranchType();
   private subscription: Subscription | null;
   numberofNotification:number;

  ngOnInit(): void {
    this.username= this.storageService.getUser().username;
    this.id= this.storageService.getUser().id;
    this.role = this.storageService.getUser().roles[0];
    this.gmessage = "days left";
    this.getBranchbyUserId();
  }
  sidebarToggle()
  {
    //toggle sidebar function
    this.document.body.classList.toggle('toggle-sidebar');
  }


  getBranchbyUserId(){
     this.authService.getBranchbyUserId(this.id).subscribe(
      (response:BranchType)=>{
         this.branch = response;
      },
      (error:HttpErrorResponse)=>{}
     );
  }

  calculateNoDays(expiryDate:Date){
    this.gmessage='days left';
    const expiryDateFomatted = new Date(expiryDate);
    this.currentDate = new Date();
    const timeDiff = expiryDateFomatted.getTime() - this.currentDate.getTime();
    this.numberOfDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
    if(this.numberOfDays>0){
      return this.numberOfDays;
    }else if(this.numberOfDays==0){
      this.gmessage='';
      return 'hours left'
    }
    else{
      return 'no';
    }
  }
  
  fetchNumberPasswordExpiry(){
    this.authService.getNumberOfDaysLeft(this.storageService.getUser().id).subscribe(
      (res:number)=>{
         if(res>=20){
           this.numberPassChangeNot=1;
           this.numberOfDaysLeftForExpiry = 30-res;
         }else{
          this.numberPassChangeNot=0;
         }
         
      },
      ()=>{}
    );
  }

  renewGuarantee(guaranteeId:any){
    let url: string = '/renewGuarantee/' + guaranteeId;
    this._router.navigate([url]);
  }

  redirectToChangePassword(){
    this._router.navigate(['/user-profile']);
  }

  Logout() {
    this.authService.userLoggedOut(this.id).subscribe(
     (response:any)=>{
      //  sessionStorage.removeItem('role');
       sessionStorage.removeItem('auth-user');
       this.authService.isAuthenticated = false;
      //  sessionStorage.removeItem('loggedIn');
     },
     (error:HttpErrorResponse)=>{}
    );

 }

 ngOnDestroy() {
  this.subscription?.unsubscribe();
}



}
