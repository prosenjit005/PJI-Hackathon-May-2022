import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  hide = true;
  username: string = "";
  password: string = "";
  userPassErrorShowFlag = false;

  constructor(private router: Router, public commonService: CommonService) { }

  ngOnInit(): void {
  }

  submitLogin() {
    console.log(this.username);
    console.log(this.password);

    if (this.username == this.commonService.username && this.password == this.commonService.password) {
      //redirect to Home Page
      this.router.navigate(['/home']);
    } else {
      this.userPassErrorShowFlag = true;
    }

  }

}
