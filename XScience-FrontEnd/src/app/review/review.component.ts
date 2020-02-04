import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ReviewDTO } from '../model/reviewDTO';
import { ReviewService } from '../services/review.service';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit {

  public review: ReviewDTO;
  private title: string;

  constructor(private router: Router, private route: ActivatedRoute, private reviewService: ReviewService)
  {
    this.review = {
      title: "",
      author: "",
      originality: "",
      adequate_literature: "",
      methodology: "",
      inference: "",
      readability: "",
      recommendations_for_editor: "",
      comment_for_editor: "",
      comment_for_author: ""
    };
  } 
  


  ngOnInit() {
    this.title = this.route.snapshot.paramMap.get('title');
  }

  saveReview(){
      this.review.title = this.title;
      debugger;
      this.reviewService.saveReview(this.review);
    }
}
