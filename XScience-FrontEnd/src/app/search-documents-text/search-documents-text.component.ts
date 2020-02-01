import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { DocumentSearchTextService } from '../services/document-search-text.service';
import { PublicationDTO } from '../model/publicationDTO';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-search-documents-text',
  templateUrl: './search-documents-text.component.html',
  styleUrls: ['./search-documents-text.component.css']
})
export class SearchDocumentsTextComponent implements OnInit {
  searchWord: string;
  public documents: PublicationDTO[];

  private currentUserRole: string;
  private currentUserEmail: string;

  constructor(private router: Router, private http: HttpClient, private documentSearchText: DocumentSearchTextService, private loginService: LoginService) { }

  ngOnInit() {
    this.getCurrentUser();
  }

  search() {
    this.documentSearchText.documentsObservable.subscribe(documents => this.documents = documents);
    this.documentSearchText.searchByText(this.searchWord);
  }

  getCurrentUser() {
    if (localStorage.getItem('currentUser') != null) {
      const currentUser: any = this.loginService.currentUserValue;
      this.currentUserRole = currentUser.role;
      this.currentUserEmail = currentUser.email;
    }
  }

  openDocument(title) {
    title = title.split(' ').join('_');
    this.router.navigate(["/document-view/" + title]);
    //Redirektuj na stranicu za prikaz dokumenta... path/documentTitle
  }

  cancel() {
    this.router.navigate(["/homepage"]);
  }

  homepage() {
    this.router.navigate(["/homepage"]);
  }

  searchDocumentsText() {
    this.router.navigate(["search-documents-text"]);
  }

  searchDocumentsMetadata() {
    this.router.navigate(["search-documents-metadata"]);
  }
  uploadPublication() {
    this.router.navigate(["upload-publication"]);
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

}
