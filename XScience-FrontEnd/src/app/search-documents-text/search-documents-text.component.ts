import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { Publication } from '../model/publication';
import { DocumentSearchTextService } from '../services/document-search-text.service';

@Component({
  selector: 'app-search-documents-text',
  templateUrl: './search-documents-text.component.html',
  styleUrls: ['./search-documents-text.component.css']
})
export class SearchDocumentsTextComponent implements OnInit {
  searchWord: string;
  public documentsTitle: String[];

  constructor(private router: Router, private http: HttpClient, private documentSearchText: DocumentSearchTextService) { }

  ngOnInit() {
  }

  search() {
    this.documentSearchText.documentsObservable.subscribe(documentsTitle => this.documentsTitle = documentsTitle);
    this.documentSearchText.searchByText(this.searchWord);
  }

  openDocument(title) {
    //Redirektuj na stranicu za prikaz dokumenta... path/documentTitle
  }

  cancel() {
    this.router.navigate(["/homepage"]);
  }

  homepage() {
    this.router.navigate(["/homepage"]);
  }

}
