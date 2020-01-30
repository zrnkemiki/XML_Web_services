import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DocumentSearchTextService } from '../services/document-search-text.service';
import { HttpClient } from '@angular/common/http';
import { PublicationDTO } from '../model/publicationDTO';
import { DocumentService } from '../services/document.service';
import { stringify } from 'querystring';

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
    this.getDocuments();
  }

  getDocuments() {
    var status = "UPLOADED"
    this.documentService.documentsObservable.subscribe(documents => this.documents = documents);
    this.documentService.findAllByStatus(status);
  }
  /*
  search() {
    this.documentSearchText.documentsObservable.subscribe(documents => this.documents = documents);
    this.documentSearchText.searchByText(this.searchWord);
  }
  */
  openDocument(title) {
    title = title.split(' ').join('_');
    this.router.navigate(["/document-view/" + title]);
    //Redirektuj na stranicu za prikaz dokumenta... path/documentTitle
  }

  viewReviews(title) {
    title = title.split(' ').join('_');
    this.router.navigate(["/document-reviews/" + title]);
  }

  assignReviewer(title) {
    title = title.split(' ').join('_');
    this.router.navigate(["/assign-reviewer/" + title]);
  }

  //TODO
  acceptDocument(title) {
    title = title.split(' ').join('_');
    this.documentService.acceptPublication(title);
    alert("Accept document")
  }

  minorRevision(title) {
    title = title.split(' ').join('_');
    alert("Minor revision document")
  }

  majorRevision(title) {
    title = title.split(' ').join('_');
    alert("Major revision document")
  }
  rejectDocument(title) {
    title = title.split(' ').join('_');
    this.documentService.rejectPublication(title);
    alert("Reject document")
  }

  cancel() {
    this.router.navigate(["/homepage"]);
  }

  homepage() {
    this.router.navigate(["/homepage"]);
  }

}
