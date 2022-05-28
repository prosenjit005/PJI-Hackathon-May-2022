import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonService } from './common.service';
import { HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Customer {
  customerId: number;
  name: string;
  contactNumber: string;
  address: string;
  zipcode: string;
}

@Injectable({
  providedIn: 'root'
})
export class PromotionsService {

  getAllCustomersUrl = '/getAllCustomers';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa('admin:admin')
    })
  };

  constructor(private http: HttpClient, private commonService: CommonService) { }


  getAllCustomers(): Observable<Customer[]> {
    return this.http.get<Customer[]>(this.commonService.httpBaseCompanyUri + this.getAllCustomersUrl);
  }



}
