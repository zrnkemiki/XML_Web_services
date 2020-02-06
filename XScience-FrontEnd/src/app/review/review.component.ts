import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ReviewDTO } from '../model/reviewDTO';
import { ReviewService } from '../services/review.service';
import { LoginService } from '../services/login.service';
@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit {

  public review: ReviewDTO;
  private title: string;

  private status: string;

  constructor(private router: Router, 
    private route: ActivatedRoute, 
    private reviewService: ReviewService, 
    private loginService: LoginService) {


    this.review = {title: "", author: "", originality: "", adequate_literature: "", methodology: "",
      inference: "", readability: "", recommendations_for_editor: "", comment_for_editor: "", comment_for_author: ""
    };
  }



  ngOnInit() {
    this.status = "status="
    this.title = this.route.snapshot.paramMap.get('title');
  }

  saveReview() {
    this.review.title = this.title;
    this.reviewService.saveReview(this.review);
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
  myReviews() {
    this.router.navigate(["my-reviews"]);
  }



}
