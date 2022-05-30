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

export interface WhatsAppMsgDto {
  body: string;
  from: string;
  ApiVersion: string;
  SmsSid: string;
  SmsStatus: string;
  SmsMessageSid: string;
  ProfileName: string;
  NumSegments: string;
  WaId: string;
  MessageSid: string;
  AccountSid: string;
  ReferralNumMedia: string;
  to: string;
  NumMedia: string;
}

@Injectable({
  providedIn: 'root'
})
export class PromotionsService {

  getAllCustomersUrl = '/getAllCustomers';
  getAllPromotionsUrl = '/getAllPromotions';
  sendWhatsAppMsgUrl = '/sendWhatsAppMsg';

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

  sendWhatsAppMsg(whatsAppMsgDto: WhatsAppMsgDto): Observable<void> {
    console.log(whatsAppMsgDto);
    return this.http.post<void>(this.commonService.httpBaseCompanyUri + this.sendWhatsAppMsgUrl,
      whatsAppMsgDto, this.httpOptions);
  }

}
