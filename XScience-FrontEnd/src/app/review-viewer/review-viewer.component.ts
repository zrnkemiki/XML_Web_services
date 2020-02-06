import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DocumentService } from '../services/document.service';
import { LoginService } from '../services/login.service';
import { ReviewService } from '../services/review.service';

@Component({
  selector: 'app-review-viewer',
  templateUrl: './review-viewer.component.html',
  styleUrls: ['./review-viewer.component.css']
})
export class ReviewViewerComponent implements OnInit {

  private path: "";
  public review: any;
  constructor(private router: Router, private route: ActivatedRoute, private reviewService: ReviewService, private documentService: DocumentService, private loginService: LoginService) { }

  ngOnInit() {
    this.getDocument();

  }

  exportFile(){
    const title = this.route.snapshot.paramMap.get('title');

    if(this.path!=""){
      this.documentService.exportDocument(title, this.path);
    }
  }

  getDocument() {
    const title = this.route.snapshot.paramMap.get('title');
    this.reviewService.getDocument(title).subscribe(data => this.review = data,
      error => console.log(error)
    );
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
