import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { LoginService } from '../services/login.service';
import { HttpClient, HttpBackend, HttpHeaders } from '@angular/common/http';
@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  totalAngularPackages;
  private currentUserEmail: string;
  private currentUserUsername: string;
  private currentUserRole: string;
  private currentUserToken: string;

  private applicationAdministrator: string;
  private applicationEmployee: string;
  private registeredUser: string;

  
  private http: HttpClient;

  constructor(private router: Router, private loginService: LoginService, private handler: HttpBackend) {
    this.http = new HttpClient(handler);
   }

  ngOnInit() {
    if (localStorage.getItem('currentUser') != null) {
      const currentUser: any = this.loginService.currentUserValue;
      this.currentUserUsername = currentUser.username;
      this.currentUserEmail = currentUser.email;
      this.currentUserRole = currentUser.role;

      if(this.currentUserRole == "ROLE_EDITOR"){

      }
    }
  }
/*
  searchDocumentsMetadata(){
    this.router.navigate(["/search-documents-metadata"]);
  }
*/
  login() {
    this.router.navigate(["/login"]);
  }

  logout() {
    this.loginService.logout();
    location.reload()
  }
  //TESTIRANJE!
  searchDocumentsMetadata() {
    const currentUser: any = this.loginService.currentUserValue;
    this.currentUserToken = currentUser.jwtToken;
    alert("Ovo je user-ov JWTtoken  " + this.currentUserToken);
    this.http.get<any>('http://localhost:9000/xscience/rest/testiranje').subscribe(data => {
            this.totalAngularPackages = data.total;
        })
  }

  searchDocumentsText() {
    this.router.navigate(["search-documents-text"]);
  }

  uploadPublication(){
    this.router.navigate(["upload-publication"]);
  }

  myDocuments(){
    this.router.navigate(["my-documents"]);
  }
  documentsForApproval(){
    this.router.navigate(["editor-document-manager"]);
  }
  

}
