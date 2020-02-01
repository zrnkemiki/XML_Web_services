import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DocumentService } from '../services/document.service';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-document-viewer',
  templateUrl: './document-viewer.component.html',
  styleUrls: ['./document-viewer.component.css']
})
export class DocumentViewerComponent implements OnInit {

  public document: string;
  constructor(private router: Router, private route: ActivatedRoute, private documentService: DocumentService, private loginService: LoginService) { }

  ngOnInit() {
    if (this.router.url != "/document-viewer") {
      this.getDocument();
    }
  }

  getDocument() {
    const title = this.route.snapshot.paramMap.get('title');
    alert("GET DOCUMENT:  " + title);
    this.documentService.getDocument(title).subscribe(document => this.document = document);
  }

  searchDocumentsText() {
    this.router.navigate(["search-documents-text"]);
  }
  searchDocumentsMetadata() {
    this.router.navigate(["search-documents-metadata"]);
  }

  uploadPublication() {
    this.router.navigate(["upload-publication"]);
  }

  myDocuments() {
    this.router.navigate(["my-documents"]);
  }
  documentsForApproval() {
    this.router.navigate(["editor-document-manager"]);
  }

  login() {
    this.router.navigate(["/login"]);
  }

  logout() {
    this.loginService.logout();
    location.reload()
  }

}
