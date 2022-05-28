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

export interface Promotion {
  promoId: number;
  promoCode: string;
  startDate: string;
  endDate: string;
  promoMessage: string;
}

@Injectable({
  providedIn: 'root'
})
export class PromotionsService {

  getAllCustomersUrl = '/getAllCustomers';
  getAllPromotionsUrl = '/getAllPromotions';

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

  getAllPromotions(): Observable<Promotion[]> {
    return this.http.get<Promotion[]>(this.commonService.httpBaseCompanyUri + this.getAllPromotionsUrl);
  }

}
