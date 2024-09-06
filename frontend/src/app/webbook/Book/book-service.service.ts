import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BookType } from 'app/types/BookDto';
import { MessageResponse } from 'app/types/MessageType';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookServiceService {
  apibaseUrl:string = environment.apiBaseUrl;
  constructor(
    private http:HttpClient
  ) { }

  addBookApiCall(bookdata:BookType):Observable<MessageResponse>{
     return this.http.post<MessageResponse>(`${this.apibaseUrl}/book/addbook`, bookdata);
  }

  getBooks():Observable<BookType[]>{
    return this.http.get<BookType[]>(`${this.apibaseUrl}/book/getbooks`);
  }
}
