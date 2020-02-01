import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { tap, map } from 'rxjs/operators';
import { PublicationDTO } from '../model/publicationDTO';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  private documentsSource = new BehaviorSubject<PublicationDTO[]>([]);
  documentsObservable = this.documentsSource.asObservable();
  public documents: PublicationDTO[];

  private documentForView: string;
  constructor(private http: HttpClient) { }

  findAllByStatus(status) {
    this.http.get<PublicationDTO[]>("http://localhost:9000/xscience/publication/getAll/" + status)
      .subscribe(documents => {
        this.documents = documents;
        this.documentsSource.next(this.documents);
      });
  }

  //GET DOCUMUMENT FOR SHOWING
  getDocument(title) {
    return this.http.get<string>("http://localhost:9000/xscience/publication/getPublication" + "/" + title)
      .pipe(tap(
        document => {
          this.documentForView = document;//DA LI UOPSTE TREBA ILI SAMO return document
          return this.documentForView;
        })
      )
  }
  //EDITOR authority---------------------------------------------------------------------------------------------->
  assignReviewer(title: string, email: any) {
    this.http.post("http://localhost:9000/xscience/publication/" + title + "/assign-reviewer/" + email, {})
      .pipe(map((response: any) => response.json()));
  }

  rejectPublication(title: any) {
    this.http.post("http://localhost:9000/xscience/publication/" + title + "/reject", {})
      .pipe(map((response: any) => response.json()));
  }

  acceptPublication(title: any) {
    this.http.post("http://localhost:9000/xscience/publication/" + title + "/accept", {})
      .pipe(map((response: any) => response.json()));
  }
  //<----------------------------------------------------------------------------------------------EDITOR authority


}
