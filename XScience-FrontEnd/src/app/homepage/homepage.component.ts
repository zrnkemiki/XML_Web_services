import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  private currentUserEmail: string;
  private currentUserUsername: string;
  private currentUserType: string;

  private applicationAdministrator: string;
  private applicationEmployee: string;
  private registeredUser: string;


  constructor(private router: Router, private loginService: LoginService) { }

  ngOnInit() {
    if (localStorage.getItem('currentUser') != null) {
      const currentUser: any = this.loginService.currentUserValue;
      this.currentUserUsername = currentUser.username;
      this.currentUserEmail = currentUser.email;
      this.currentUserType = currentUser.userType;
    }
  }



  login() {
    this.router.navigate(["/login"]);
  }

  logout() {
    this.loginService.logout();
    location.reload()
  }

  profil() {
    this.router.navigate(["/user/" + this.currentUserUsername]);
  }

  searchDocumentsText() {
    this.router.navigate(["searchDocuments"]);
  }

}
