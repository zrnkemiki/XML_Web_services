import { Component, OnInit } from '@angular/core';
import { ReviewDTO } from '../model/reviewDTO';
import { Route } from '@angular/compiler/src/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DocumentService } from '../services/document.service';
import { ReviewService } from '../services/review.service';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-document-reviews',
  templateUrl: './document-reviews.component.html',
  styleUrls: ['./document-reviews.component.css']
})
export class DocumentReviewsComponent implements OnInit {
  public reviews: ReviewDTO[];
  public documentTitle: string;

  private currentUserRole: string;
  private currentUserEmail: string;
  constructor(private router: Router, private route: ActivatedRoute, private reviewService: ReviewService, private loginService: LoginService) { }

  ngOnInit() {
    this.getCurrentUser();
    const title = this.route.snapshot.paramMap.get('title');
    this.getReviews(title)
  }
  getCurrentUser() {
    if (localStorage.getItem('currentUser') != null) {
      const currentUser: any = this.loginService.currentUserValue;
      this.currentUserRole = currentUser.role;
      this.currentUserEmail = currentUser.email;
    }
  }
  getReviews(documentTitle) {
    this.reviewService.reviewsObservable.subscribe(reviews => this.reviews = reviews);
    this.reviewService.findReviewsByDocument(documentTitle);
  }

  openReview(title){
    title = title.split(' ').join('_');
    this.router.navigate(["/review-view/" + title]);
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
