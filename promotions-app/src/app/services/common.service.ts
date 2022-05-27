import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  username: string = "user1";
  password: string = "password1";

  httpBaseCompanyUri: string = "http://localhost:8989/api/v1.0/market/company";


  constructor() { }

}
