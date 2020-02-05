import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { PublicationDTO } from '../model/publicationDTO';
import { stringify } from 'querystring';

@Injectable({
  providedIn: 'root'
})
export class DocumentSearchTextService {
  private documentsSource = new BehaviorSubject<PublicationDTO[]>([]);
  documentsObservable = this.documentsSource.asObservable();
  public documents: PublicationDTO[];
  public params: string[];
  public temp: string;


  constructor(private router: Router, private http: HttpClient) { }

  searchByText(searchWord) {
    this.params = searchWord.split(" ");
    this.temp = "";
    for (let i = 0; i < this.params.length; i++) {
      this.temp += "k" + i + "=" + this.params[i] + "&"
    }
    this.temp = this.temp.slice(0, -1);
    this.http.get<PublicationDTO[]>("http://localhost:9000/xscience/publication/search?/" + this.temp)
      .subscribe(documents => {
        this.documents = documents;
        this.documentsSource.next(this.documents);
      });
  }

  searchByMetadata(searchWord) {
    this.http.get<PublicationDTO[]>("http://localhost:9000/xscience/proba/advanced-search?" + searchWord)
      .subscribe(documents => {
        this.documents = documents;
        this.documentsSource.next(this.documents);
      });
  }


}
