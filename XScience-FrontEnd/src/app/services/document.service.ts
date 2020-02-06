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

  //GET ALL DOCUMENTS FOR APPROVAL ---- EDITOR!
  findAllByStatus(status) {
    this.http.get<PublicationDTO[]>(this.publicationUri + "documents-for-approval")
      .subscribe(documents => {
        this.documents = documents;
        this.documentsSource.next(this.documents);
      });
  }
  //GET MY DOCUMENTS author-editor-reviewer
  getMyDocuments() {
    this.http.get<PublicationDTO[]>(this.publicationUri + "my-documents")
      .subscribe(documents => {
        this.documents = documents;
        this.documentsSource.next(this.documents);
      });
  }

  //GET DOCUMUMENT FOR SHOWING IN BROWSER
  getDocument(title) {
    return this.http.get<string>(this.publicationUri + "getPublication/" + title)
      .pipe(tap(
        document => {
          this.documentForView = document;//DA LI UOPSTE TREBA ILI SAMO return document
          return this.documentForView;
        })
      )
  }

  //GET DOCUMENTS IN WHICH I AM ASSIGNED AS REVIEWER
  getDocumentsForMyReview() {
    this.http.get<PublicationDTO[]>(this.publicationUri + "documents-for-review")
      .subscribe(documents => {
        this.documents = documents;
        this.documentsSource.next(this.documents);
      });
  }
  //title = Publication title /// email- reviewerEMAIL
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
