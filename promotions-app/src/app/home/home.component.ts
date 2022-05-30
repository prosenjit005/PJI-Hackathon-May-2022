import { Component, OnInit } from '@angular/core';
import { Customer, Promotion, PromotionsService, WhatsAppMsgDto } from '../services/promotions.service';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  customersList: Customer[] = [];
  promotionsList: Promotion[] = [];

  selectedCustomers = new FormControl();
  selectedPromotions = new FormControl();

  constructor(private promotionsService: PromotionsService) { }

  ngOnInit(): void {
    this.getAllCustomersService();
    this.getAllPromotionsService();
  }

  getAllCustomersService() {
    this.promotionsService.getAllCustomers()
      .subscribe(data => {
        this.customersList = data;
      });
  }

  getAllPromotionsService() {
    this.promotionsService.getAllPromotions()
      .subscribe(data => {
        this.promotionsList = data;
      });
  }

  sendMsg() {
    let whatsAppMsgDto = {} as WhatsAppMsgDto;
    let msg = "Please select one of the below offers:";
    let count = 1;
    this.selectedPromotions.value.forEach((promo: { promoMessage: string; }) => {
      msg += "\n" + count + ". " + promo.promoMessage;
      ++count;
    });
    console.log(msg);
    whatsAppMsgDto.body = msg;
    whatsAppMsgDto.to = this.selectedCustomers.value.contactNumber;

    this.promotionsService.sendWhatsAppMsg(whatsAppMsgDto)
      .subscribe(data => {
        console.log("sending message success!");
      });


  }

}
