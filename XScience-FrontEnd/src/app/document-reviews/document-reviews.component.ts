import { Component, OnInit } from '@angular/core';
import { ReviewDTO } from '../model/reviewDTO';
import { Route } from '@angular/compiler/src/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DocumentService } from '../services/document.service';
import { ReviewService } from '../services/review.service';

@Component({
  selector: 'app-document-reviews',
  templateUrl: './document-reviews.component.html',
  styleUrls: ['./document-reviews.component.css']
})
export class DocumentReviewsComponent implements OnInit {
  public reviews: ReviewDTO[];
  public documentTitle: string;
  constructor(private route: ActivatedRoute, private reviewService: ReviewService) { }

  ngOnInit() {
    const title = this.route.snapshot.paramMap.get('title');
    this.getReviews(title)
  }

  getReviews(documentTitle) {
    this.reviewService.reviewsObservable.subscribe(reviews => this.reviews = reviews);
    this.reviewService.findReviewsByDocument(documentTitle);
  }
}
