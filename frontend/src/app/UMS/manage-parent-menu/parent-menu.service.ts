import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ParentMenuType } from 'app/types/ParentMenuType';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ParentMenuService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http:HttpClient) { }

  fetchParentMenus():Observable<ParentMenuType[]> {
    return this.http.get<ParentMenuType[]>(`${this.apiServerUrl}/ParentMenu/fetchparentmenus`);
  }
  addParentMenu(menu:ParentMenuType):Observable<ParentMenuType> {
    return this.http.post<ParentMenuType>(`${this.apiServerUrl}/ParentMenu/addParentMenu`,menu);
  }
  public updateParentMenu(data: ParentMenuType): Observable<ParentMenuType> {
    return this.http.put<ParentMenuType>(
      `${this.apiServerUrl}/ParentMenu/update`,
      data
    );
  }

}
