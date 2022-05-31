import { Component, OnInit } from '@angular/core';
import { Customer, Promotion, PromotionsService, WhatsAppMsgDto } from '../services/promotions.service';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';

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

  selectedCustomerMonitor = new FormControl();

  constructor(private promotionsService: PromotionsService, private router: Router,
    private commonService: CommonService) { }

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

  monitorChat() {
    if (null != this.selectedCustomerMonitor.value && this.selectedCustomerMonitor.value != "") {
      this.commonService.currentCustomer = this.selectedCustomerMonitor.value;
      //redirect to monitorchat Page
      this.router.navigate(['/monitorchat']);
    }

  }

}
