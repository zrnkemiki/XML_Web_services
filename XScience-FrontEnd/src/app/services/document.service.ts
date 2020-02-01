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
  private publicationUri = "http://localhost:9000/xscience/publication/";

  private documentForView: string;
  constructor(private http: HttpClient) { }

  findAllByStatus(status) {
    this.http.get<PublicationDTO[]>(this.publicationUri + "getAll/" + status)
      .subscribe(documents => {
        this.documents = documents;
        this.documentsSource.next(this.documents);
      });
  }

  //GET DOCUMUMENT FOR SHOWING
  getDocument(title) {
    return this.http.get<string>(this.publicationUri + "getPublication/" + title)
      .pipe(tap(
        document => {
          this.documentForView = document;//DA LI UOPSTE TREBA ILI SAMO return document
          return this.documentForView;
        })
      )
  }
  //EDITOR authority---------------------------------------------------------------------------------------------->
  assignReviewer(title: string, email: any) {
    this.http.post(this.publicationUri + title + "/assign-reviewer/" + email, {})
      .pipe(map((response: any) => response.json()));
  }

  rejectPublication(title: any) {
    this.http.post(this.publicationUri + title + "/reject", {})
      .pipe(map((response: any) => response.json()));
  }

  acceptPublication(title: any) {
    this.http.post<any>(this.publicationUri + title + ".xml/accept", {}).subscribe((response: any) => response.json())
    title = title.split('_').join(' ');
    alert("Document with title: " + title + " has been accepted!");
    location.reload();

  }

  //<----------------------------------------------------------------------------------------------EDITOR authority


}
