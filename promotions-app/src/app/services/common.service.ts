import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  username: string = "admin";
  password: string = "admin";

  httpBaseCompanyUri: string = "http://localhost:8989/api/v1.0/market/company";


  constructor() { }

}
