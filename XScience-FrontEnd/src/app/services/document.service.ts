import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { PublicationDTO } from '../model/publicationDTO';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  private documentsSource = new BehaviorSubject<PublicationDTO[]>([]);
  documentsObservable = this.documentsSource.asObservable();
  public documents: PublicationDTO[];

  private documentForView : string;
  constructor(private http: HttpClient) { }


  findAll() {
    this.http.get<PublicationDTO[]>("http://localhost:9000/xscience/publication/getAll")
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
}
