import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
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

  exportDocument(title, path){
    this.http.post<any>(this.publicationUri + title + "/export/" + path, {}).subscribe((response: any) => response.json())
    title = title.split('_').join(' ');
    alert("Document with title: " + title + " has been exported!");
    location.reload();
  }

  getDocument(title){
    return this.http.get(this.publicationUri + title , {responseType: 'text'});
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
    this.http.post(this.publicationUri + title + "/assign-reviewer/" + email, {}).subscribe((response: any) => response.json())
    alert("Document with title: " + title + " has been assigned to " + email);
    location.reload();
  }

  rejectPublication(title: any) {
    this.http.post<any>(this.publicationUri + title + ".xml/reject", {}).subscribe((response: any) => response.json())
    title = title.split('_').join(' ');
    alert("Document with title: " + title + " has been rejected!");
    location.reload();
  }

  acceptPublication(title: any) {
    this.http.post<any>(this.publicationUri + title + ".xml/accept", {}).subscribe((response: any) => response.json())
    title = title.split('_').join(' ');
    alert("Document with title: " + title + " has been accepted!");
    location.reload();

  }
  minorRevision(title){
    this.http.post<any>(this.publicationUri + title + ".xml/minor-revision", {}).subscribe((response: any) => response.json())
    title = title.split('_').join(' ');
    alert("Document with title: " + title + " - has been set for minor revision");
    location.reload();
  }

  majorRevision(title){
    this.http.post<any>(this.publicationUri + title + ".xml/major-revision", {}).subscribe((response: any) => response.json())
    title = title.split('_').join(' ');
    alert("Document with title: " + title + " - has been set for major revision!");
    location.reload();
  }


  //<----------------------------------------------------------------------------------------------EDITOR authority


}
