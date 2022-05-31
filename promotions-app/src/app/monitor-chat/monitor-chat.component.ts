import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';

@Component({
  selector: 'app-monitor-chat',
  templateUrl: './monitor-chat.component.html',
  styleUrls: ['./monitor-chat.component.css']
})
export class MonitorChatComponent implements OnInit {

  constructor(private router: Router, public commonService: CommonService) { }

  ngOnInit(): void {
    console.log("Current Monitor Customer=", this.commonService.currentCustomer);
  }

  backToHome() {
    //redirect to home Page
    this.router.navigate(['/home']);
  }

}
