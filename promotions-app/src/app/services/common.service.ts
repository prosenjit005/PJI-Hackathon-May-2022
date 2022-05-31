import { Injectable } from '@angular/core';
import { Customer } from './promotions.service';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  username: string = "admin";
  password: string = "admin";

  httpBaseCompanyUri: string = "http://localhost:8989/api/v1.0/promotions";

  currentCustomer!: Customer;

  constructor() { }

}
