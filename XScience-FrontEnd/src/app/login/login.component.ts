import { Component, OnInit } from '@angular/core';
import { UserCredentials } from '../model/UserCredentials';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private credentials: UserCredentials;
  constructor(private loginService: LoginService, private router: Router) { }

  ngOnInit() {
    this.credentials = new UserCredentials();
  }

  register() {
  }

  onClick() {
    debugger;
    this.loginService.login();
    this.loginService.currentUser.subscribe(

      (result) => {
        if (result) {
          alert("Pogodio login")
          this.router.navigate(['/homepage'])
        }
        else {
          //this.toastr.error('error logging');
        }
      });


  }

}
