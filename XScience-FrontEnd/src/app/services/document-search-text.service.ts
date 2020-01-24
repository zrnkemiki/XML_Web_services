import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DocumentSearchTextService {
  private documentsSource = new BehaviorSubject<String[]>([]);
  documentsObservable = this.documentsSource.asObservable();
  private documentsTitle = [];//Lista titlova dokumenata

  constructor(private router: Router, private http: HttpClient) { }
  //TODO BACKEND
  searchByText(searchWord) {
    //Lista publikacijaDTO(samo title da ima npr)
    this.http.get<String[]>("http://localhost:9000/xscience/testiranje")
      .subscribe(documents => {
        this.documentsTitle = documents;
        this.documentsSource.next(this.documentsTitle);
      });
  }

}
