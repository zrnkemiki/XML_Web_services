import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Route } from '@angular/compiler/src/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ThrowStmt } from '@angular/compiler';
import { debuglog } from 'util';
import { PublicationDTO } from '../model/publicationDTO';
import { DocumentSearchTextService } from '../services/document-search-text.service';

@Component({
  selector: 'app-search-documents-metadata',
  templateUrl: './search-documents-metadata.component.html',
  styleUrls: ['./search-documents-metadata.component.css']
})
export class SearchDocumentsMetadataComponent implements OnInit {

  public documents: PublicationDTO[];


  private currentUserRole: string;
  private currentUserEmail: string;
  isShow = false;
  private searchData = "";

  private titleTemp = "";
  //private statusTemp = "";
  private fieldOfStudyTemp = "";
  private authorTemp = "";
  private dateFromTemp: Date;
  private dateUntilTemp: Date;
  private keyWordTemp = "";
  private paperTypeTemp = "";

  private title = "title=";
  //private status = "status=";
  private fieldOfStudy = "fieldOfStudy=";
  private author = "authoredBy=@";
  private dateRange = "recieved=$";
  private keyWord = "keyword=";
  private paperType = "paperType=";

  constructor(private loginService: LoginService, private router: Router, private documentSearchText: DocumentSearchTextService) { }

  ngOnInit() {
    this.getCurrentUser();
    this.documents = [];
  }

  getCurrentUser() {
    if (localStorage.getItem('currentUser') != null) {
      const currentUser: any = this.loginService.currentUserValue;
      this.currentUserRole = currentUser.role;
      this.currentUserEmail = currentUser.email;
    }
  }

  search() {
    //this.addStatus();
    this.addTitle();
    this.addKeyword();
    this.addFieldOfStudy();
    this.addAuthor();
    this.addDate();
    this.addPaperType();
    if (this.title != "title=") {
      this.searchData += this.title + "&";
    }
    //if (this.status != "status=") {
    //  this.searchData += this.status + "&";
    //}
    if (this.keyWord != "keyword=") {
      this.searchData += this.keyWord + "&";
    }
    if (this.fieldOfStudy != "fieldOfStudy=") {
      this.searchData += this.fieldOfStudy + "&";
    }
    if (this.author != "authoredBy=@") {
      this.searchData += this.author + "&";
    }
    if (this.dateRange != "recieved=$") {
      this.searchData += this.dateRange + "&";
    }
    if (this.paperType != "paperType=") {
      this.searchData += this.paperType + "&";

    }
    this.title = "title=";
    // this.status = "status=";
    this.fieldOfStudy = "fieldOfStudy=";
    this.author = "authoredBy=@";
    this.dateRange = "recieved=$";
    this.keyWord = "keyword=";
    this.paperType = "paperType=";

    this.searchData = this.searchData.substring(0, this.searchData.length - 1);

    this.documentSearchText.documentsObservable.subscribe(documents => this.documents = documents);
    this.documentSearchText.searchByMetadata(this.searchData);

    this.isShow = !this.isShow;

  }

  searchAgain() {
    location.reload();
  }




  addTitle() {
    if (this.titleTemp != "") {
      if (this.title === "title=") {
        this.title = this.title + "\"" + this.titleTemp + "\"";
      }
      else {
        this.title = this.title + ";" + "\"" + this.titleTemp + "\"";
      }
    }
    this.titleTemp = "";
  }

  addKeyword() {
    if (this.keyWordTemp != "") {
      if (this.keyWord === "keyword=") {
        this.keyWord = this.keyWord + "\"" + this.keyWordTemp + "\"";
      }
      else {
        this.keyWord = this.keyWord + ";" + "\"" + this.keyWordTemp + "\"";
      }
    }
    this.keyWordTemp = "";
  }
  addPaperType() {
    if (this.paperTypeTemp != "") {
      if (this.paperType === "paperType=") {
        this.paperType = this.paperType + "\"" + this.paperTypeTemp + "\"";
      }
      else {
        this.paperType = this.paperType + ";" + "\"" + this.paperTypeTemp + "\"";
      }
    }
    this.paperTypeTemp = "";
  }
  /*
    addStatus() {
      if (this.statusTemp != "") {
        if (this.status === "status=") {
          this.status = this.status + "\"" + this.statusTemp + "\"";
        }
        else {
          this.status = this.status + ";" + "\"" + this.statusTemp + "\"";
        }
      }
      this.statusTemp = "";
    }
  */
  addFieldOfStudy() {
    if (this.fieldOfStudyTemp != "") {
      if (this.fieldOfStudy === "fieldOfStudy=") {
        this.fieldOfStudy = this.fieldOfStudy + "\"" + this.fieldOfStudyTemp + "\"";
      }
      else {
        this.fieldOfStudy = this.fieldOfStudy + ";" + "\"" + this.fieldOfStudyTemp + "\"";
      }
    }
    this.fieldOfStudyTemp = "";
  }

  addAuthor() {
    if (this.authorTemp != "") {
      this.authorTemp = this.authorTemp.split("@").join("-").toLowerCase();;
      if (this.author === "authoredBy=@") {
        this.author = this.author + "\"" + "user-" + this.authorTemp + "\"";
      }
      else {
        this.author = this.author + ";" + "\"" + this.authorTemp + "\"";
      }
    }
    this.authorTemp = "";
  }

  addDate() {
    if (this.dateFromTemp != undefined) {
      if (this.dateRange === "recieved=$") {
        if (this.dateUntilTemp == undefined) {
          this.dateRange = this.dateRange + "gt:" + "\"" + this.dateFromTemp + "\"";
        }
        else if (this.dateUntilTemp != undefined) {
          this.dateRange = this.dateRange + "\"" + this.dateFromTemp + "\"" + ":" + "\"" + this.dateUntilTemp + "\"";
        }
      }
    }
    else if (this.dateUntilTemp != undefined) {
      if (this.dateRange === "recieved=$") {
        if (this.dateFromTemp == undefined)
          this.dateRange = this.dateRange + "lt:" + "\"" + this.dateUntilTemp + "\"";
      }
    }
  }

  openDocument(title) {
    title = title.split(' ').join('_');
    this.router.navigate(["/document-view/" + title]);
    //Redirektuj na stranicu za prikaz dokumenta... path/documentTitle
  }
  cancel() {
    this.router.navigate(["/homepage"]);
  }
  homepage() {
    this.router.navigate(["/homepage"]);
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
  myReviews() {
    this.router.navigate(["my-reviews"]);
  }

}
