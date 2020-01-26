import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FileUploadServiceService {

  constructor(private http: HttpClient) { }

  uploadDocument(formData: FormData) {
    console.log(formData.get('files'));
    debugger;
    return this.http.post("http://localhost:9000/xscience/publication", formData, {
      //headers: {'Content-Type': 'multipart/form-data'},
      reportProgress: true,
      observe: 'events'
    });
  }

}
