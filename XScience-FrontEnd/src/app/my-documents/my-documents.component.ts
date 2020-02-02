import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';
import { PublicationDTO } from '../model/publicationDTO';
import { DocumentService } from '../services/document.service';

@Component({
  selector: 'app-my-documents',
  templateUrl: './my-documents.component.html',
  styleUrls: ['./my-documents.component.css']
})
export class MyDocumentsComponent implements OnInit {

  public documents: PublicationDTO[];
  private currentUserRole: string;
  private currentUserEmail: string;
  constructor(private router: Router, private loginService: LoginService, private documentService: DocumentService) { }

  ngOnInit() {
    this.getCurrentUser();
    this.getMyDocuments();
  }

  getMyDocuments(){
    this.documentService.documentsObservable.subscribe(documents => this.documents = documents);
    this.documentService.getMyDocuments();
  }

  getCurrentUser() {
    if (localStorage.getItem('currentUser') != null) {
      const currentUser: any = this.loginService.currentUserValue;
      this.currentUserRole = currentUser.role;
      this.currentUserEmail = currentUser.email;
    }
  }
  searchDocumentsText() {
    this.router.navigate(["search-documents-text"]);
  }

  uploadPublication() {
    this.router.navigate(["upload-publication"]);
  }
  searchDocumentsMetadata() {
    this.router.navigate(["search-documents-metadata"]);
  }

  myDocuments() {
    this.router.navigate(["my-documents"]);
  }
  documentsForApproval() {
    this.router.navigate(["editor-document-manager"]);
  }

  login() {
    this.router.navigate(["/login"]);
  }

  logout() {
    this.loginService.logout();
    location.reload()
  }
  myReviews(){
    this.router.navigate(["my-reviews"]);
  }

}
