import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DocumentSearchTextService } from '../services/document-search-text.service';
import { HttpClient } from '@angular/common/http';
import { PublicationDTO } from '../model/publicationDTO';
import { DocumentService } from '../services/document.service';

@Component({
  selector: 'app-editor-document-manager',
  templateUrl: './editor-document-manager.component.html',
  styleUrls: ['./editor-document-manager.component.css']
})
export class EditorDocumentManagerComponent implements OnInit {

  searchWord: string;
  public documents: PublicationDTO[];

  constructor(private router: Router, private http: HttpClient, private documentService: DocumentService) { }

  ngOnInit() {
    debugger;
    if (this.router.url === "/editor-document-manager") {
      this.getDocuments();
    }
  }

  getDocuments(){
    this.documentService.documentsObservable.subscribe(documents => this.documents = documents);
    this.documentService.findAll();
  }
  /*
  search() {
    this.documentSearchText.documentsObservable.subscribe(documents => this.documents = documents);
    this.documentSearchText.searchByText(this.searchWord);
  }
  */
  openDocument(title) {
    this.router.navigate(["/document-view/" + title]);
    //Redirektuj na stranicu za prikaz dokumenta... path/documentTitle
  }

  viewReviews(title){
    this.router.navigate(["/document-reviews/" + title]);
  }

  assignReviewer(title){
    this.router.navigate(["/assign-reviewer/" + title]);
  }

  //TODO
  acceptDocument(title){
    alert("Accept document")
  }
  minorRevision(title){
    alert("Minor revision document")
  }
  majorRevision(title){
    alert("Major revision document")
  }
  rejectDocument(title){
    alert("Reject document")
  }

  
  cancel() {
    this.router.navigate(["/homepage"]);
  }

  homepage() {
    this.router.navigate(["/homepage"]);
  }

}
