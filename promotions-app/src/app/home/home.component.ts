import { Component, OnInit } from '@angular/core';
import { PromotionsService } from '../services/promotions.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private promotionsService:PromotionsService) { }

  ngOnInit(): void {
    this.getAllCustomersService();
  }

  getAllCustomersService(){
    this.promotionsService.getAllCustomers()
      .subscribe(data => {
        console.log(data);
        console.log(data.length);
      });
  }

}
