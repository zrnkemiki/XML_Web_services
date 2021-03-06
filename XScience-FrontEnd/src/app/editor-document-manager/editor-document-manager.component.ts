import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DocumentSearchTextService } from '../services/document-search-text.service';
import { HttpClient } from '@angular/common/http';
import { PublicationDTO } from '../model/publicationDTO';
import { DocumentService } from '../services/document.service';
import { stringify } from 'querystring';
import { User } from '../model/user';
import { UserService } from '../services/user.service';
import { LoginService } from '../services/login.service';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

@Component({
  selector: 'app-editor-document-manager',
  templateUrl: './editor-document-manager.component.html',
  styleUrls: ['./editor-document-manager.component.css']
})
export class EditorDocumentManagerComponent implements OnInit {

  searchWord: string;
  public documents: PublicationDTO[];
  private currentUserRole: string;
  private currentUserEmail: string;


  constructor(private router: Router, private http: HttpClient, private documentService: DocumentService, private loginService: LoginService) { }

  ngOnInit() {
    this.getCurrentUser();
    if (this, this.currentUserRole == "ROLE_EDITOR") {
      this.getDocuments();
    }
    else {
      this.router.navigate(["/homepage"]);
    }
  }


  getCurrentUser() {
    if (localStorage.getItem('currentUser') != null) {
      const currentUser: any = this.loginService.currentUserValue;
      this.currentUserRole = currentUser.role;
      this.currentUserEmail = currentUser.email;
    }
  }

  getDocuments() {
    var status = "UPLOADED"
    this.documentService.documentsObservable.subscribe(documents => this.documents = documents);
    this.documentService.findAllByStatus(status);
  }
  /*
  search() {
    this.documentSearchText.documentsObservable.subscribe(documents => this.documents = documents);
    this.documentSearchText.searchByText(this.searchWord);
  }
  */
  openDocument(title) {
    title = title.split(' ').join('_');
    this.router.navigate(["/document-view/" + title]);
    //Redirektuj na stranicu za prikaz dokumenta... path/documentTitle
  }

  viewReviews(title) {
    title = title.split(' ').join('_');
    this.router.navigate(["/document-reviews/" + title]);
  }

  viewCoverLetter(title) {
    title = title.split(' ').join('_');
    this.router.navigate(["/coverLetter-view/" + title]);
  }

  assignReviewer(title) {
    title = title.split(' ').join('_');
    this.router.navigate(["/assign-reviewer/" + title]);
  }

  acceptDocument(title) {
    title = title.split(' ').join('_');
    this.documentService.acceptPublication(title);
    this.homepage();
  }

  minorRevision(title) {
    title = title.split(' ').join('_');
    this.documentService.minorRevision(title);
    alert("Minor revision document")
    this.homepage();
  }

  majorRevision(title) {
    title = title.split(' ').join('_');
    this.documentService.majorRevision(title);
    alert("Major revision document")
    this.homepage();
  }
  rejectDocument(title) {
    title = title.split(' ').join('_');
    this.documentService.rejectPublication(title);
    alert("Reject document")
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
    this.router.navigate(["/homepage"]);
  }
  myReviews() {
    this.router.navigate(["my-reviews"]);
  }

}
