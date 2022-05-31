import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-monitor-chat',
  templateUrl: './monitor-chat.component.html',
  styleUrls: ['./monitor-chat.component.css']
})
export class MonitorChatComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  backToHome() {
    //redirect to home Page
    this.router.navigate(['/home']);
  }

}
