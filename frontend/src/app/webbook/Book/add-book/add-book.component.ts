import { Component } from '@angular/core';
import { BookServiceService } from '../book-service.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageResponse } from 'app/types/MessageType';
import { HttpErrorResponse } from '@angular/common/http';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css']
})
export class AddBookComponent {
   bookform!:FormGroup;
   submitted=false;
   constructor(
    private bookservice:BookServiceService,
    private formBuilder:FormBuilder,
    private messageService:MessageService
   ){

   }

   ngOnInit():void{
      this.bookform = this.formBuilder.group(
        {
          title:['', [Validators.required,Validators.pattern(/^[A-Za-z\s]+$/)]],
          year:['', [Validators.required]]
        }
      );
   }

   get f(){
     return this.bookform.controls;
   }

   //this function is to add book
   onAddBook(){
       this.submitted=true;
       if(this.bookform.valid){
            console.log("book data from html",this.bookform.value);
            this.bookservice.addBookApiCall(this.bookform.value).subscribe(
              (response:MessageResponse)=>{
                this.messageService.add({ severity: 'success', detail: response.message, life: 3000 }); 
              },
              (error:HttpErrorResponse)=>{
                this.messageService.add({ severity: 'error', detail: 'Server Error', life: 3000 }); 
              }
            );
       }else{
       }
   }
}
