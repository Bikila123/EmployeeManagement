import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { BookType } from 'app/types/BookDto';
import { BookServiceService } from '../Book/book-service.service';

@Component({
  selector: 'app-webhome',
  templateUrl: './webhome.component.html',
  styleUrls: ['./webhome.component.css']
})
export class WebhomeComponent {
    books:BookType[]=[];
   constructor(
    private bookservice:BookServiceService
   ){}

   ngOnInit(){
      this.getBooks();
   }


   getBooks(){
       this.bookservice.getBooks().subscribe(
          (res:BookType[])=>{
            console.log("books:",res);
            this.books=res;
          },
          (err:HttpErrorResponse)=>{
            console.log("err");
          }
       );
   }
}
