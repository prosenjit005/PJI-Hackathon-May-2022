import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonService } from './common.service';
import { HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Company {
  id: number;
  companyCode: string;
  companyName: string;
  companyCEO: string;
  companyTurnover: string;
  companyWebsite: string;
  stockExchange: string;
}

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  getAllCompaniesUrl = '/getall';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa('user1:password1')
    })
  };

  constructor(private http: HttpClient, private commonService: CommonService) { }


  // getAllCompanies() {
  //   return this.http.get<Company[]>(this.commonService.httpBaseCompanyUri + this.getAllCompaniesUrl, this.httpOptions);
  // }



getAllCompanies(): Observable<Company[]> {
  return this.http.get<Company[]>(this.commonService.httpBaseCompanyUri + this.getAllCompaniesUrl, this.httpOptions)
}



}
