import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DocumentService } from '../services/document.service';
import { LoginService } from '../services/login.service';
import { ReviewService } from '../services/review.service';
import { CoverLetterService } from '../services/cover-letter.service';

@Component({
  selector: 'app-cover-letter-viewer',
  templateUrl: './cover-letter-viewer.component.html',
  styleUrls: ['./cover-letter-viewer.component.css']
})
export class CoverLetterViewerComponent implements OnInit {

 
  private path: "";
  public coverLetter: any;
  constructor(private router: Router, private route: ActivatedRoute, private coverLetterService: CoverLetterService, private loginService: LoginService) { }

  ngOnInit() {
    this.getDocument();

  }

  exportFile(){
    const title = this.route.snapshot.paramMap.get('title');

    if(this.path!=""){
      this.coverLetterService.exportDocument(title, this.path);
    }
  }

  getDocument() {
    const title = this.route.snapshot.paramMap.get('title');
    this.coverLetterService.getDocument(title).subscribe(data => this.coverLetter = data,
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
    this.router.navigate(["/homepage"]);
  }

}

