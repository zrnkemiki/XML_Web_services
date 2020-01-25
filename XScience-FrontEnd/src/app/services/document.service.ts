import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  private documentForView : string;
  constructor(private http: HttpClient) { }


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
