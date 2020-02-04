import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ReviewDTO } from '../model/reviewDTO';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private reviewsSource = new BehaviorSubject<ReviewDTO[]>([]);
  reviewsObservable = this.reviewsSource.asObservable();
  private reviews = [];

  private reviewUri = "http://localhost:9000/xscience/reviewer-mng/";


  constructor(private http: HttpClient) { }

  saveReview(review: ReviewDTO) {
    this.http.post<any>(this.reviewUri + "saveReview", review).subscribe((response: any) => response.json())
    alert("Review saved")

  }


  findReviewsByDocument(documentTitle) {
    this.http.get<ReviewDTO[]>(this.reviewUri + documentTitle)
      .subscribe(reviews => {
        this.reviews = reviews;
        this.reviewsSource.next(this.reviews);
      });
  }


  declineReviewRequest(title: any) {
    this.http.post<any>(this.reviewUri + "/publication/" + title + "/decline-review", {}).subscribe((response: any) => response.json())
    title = title.split('_').join(' ');
    alert("Reviewing for document with title: " + title + " has been declined!");
    location.reload();

  }
}
