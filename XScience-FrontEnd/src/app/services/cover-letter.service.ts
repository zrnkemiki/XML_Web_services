import { Injectable } from '@angular/core';
import { CoverLetterDTO } from '../model/coverLetterDTO';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CoverLetterService {

  private clSource = new BehaviorSubject<CoverLetterDTO[]>([]);
  clsObservable = this.clSource.asObservable();
  private coverLetters = [];

  private coverLetterUri = "http://localhost:9000/xscience/coverletter/";


  constructor(private http: HttpClient) { }

  

  exportDocument(title, path){
    this.http.post<any>(this.coverLetterUri + title + "/export/" + path, {}).subscribe((response: any) => response.json())
    title = title.split('_').join(' ');
    alert("Review with title: " + title + " has been exported!");
    location.reload();
  }

  getDocument(title){
    return this.http.get(this.coverLetterUri+ title , {responseType: 'text'});
  }
/*
  //PROVERITI NA BEKENDU?
  findReviewsByDocument(documentTitle) {
    this.http.get<ReviewDTO[]>(this.reviewUri + documentTitle)
      .subscribe(reviews => {
        this.reviews = reviews;
        this.reviewsSource.next(this.reviews);
      });
  }
*/


}
