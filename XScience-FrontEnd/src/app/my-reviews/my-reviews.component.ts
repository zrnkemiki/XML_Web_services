import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';
import { ReviewService } from '../services/review.service';
import { PublicationDTO } from '../model/publicationDTO';
import { DocumentService } from '../services/document.service';

@Component({
  selector: 'app-my-reviews',
  templateUrl: './my-reviews.component.html',
  styleUrls: ['./my-reviews.component.css']
})
export class MyReviewsComponent implements OnInit {
  public documents: PublicationDTO[];
  private currentUserRole: string;
  private currentUserEmail: string;
  constructor(private router: Router, private loginService: LoginService, private reviewService: ReviewService, private documentService: DocumentService) { }

  ngOnInit() {
    this.getCurrentUser();
    this.getDocumentsForReview();
  }

  getDocumentsForReview(){
    this.documentService.documentsObservable.subscribe(documents => this.documents = documents);
    this.documentService.getDocumentsForMyReview();
  }

  getCurrentUser() {
    if (localStorage.getItem('currentUser') != null) {
      const currentUser: any = this.loginService.currentUserValue;
      this.currentUserRole = currentUser.role;
      this.currentUserEmail = currentUser.email;
    }
  }

  declineReview(title: any){
    title = title.split(' ').join('_');
    this.reviewService.declineReviewRequest(title);
    //REVEWER NE ZELI DA RADI REVIEW
  }

  openDocument(title:any){
    //to view in browser
  }

  writeReview(title:any){
    this.router.navigate(["review/" + title]);
    //write review for TITLE document
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
    this.router.navigate(["/homepage"]);
  }
  myReviews(){
    this.router.navigate(["my-reviews"]);
  }

}
