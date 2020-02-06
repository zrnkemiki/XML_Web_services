import { Component, OnInit, VERSION } from '@angular/core';
import { User } from '../model/user';
import { ActivatedRoute, Router } from '@angular/router'
import { LoginService } from '../services/login.service';
import { HttpEventType, HttpClient, HttpResponse } from '@angular/common/http';
import { FileUploadServiceService } from '../services/file-upload-service.service';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit {
  ngOnInit(): void {
    this.getCurrentUser();
  }
  private revisionCheckBox = false;

  private currentUserRole: string;
  private currentUserEmail: string;
  constructor(private http: HttpClient, private router: Router, private loginService: LoginService) { }

  filePublication: File = null;
  fileCoverLetter: File = null;
  previewUrl: any = null;
  fileUploadProgress: string = null;
  uploadedFilePath: string = null;

  fileProgressPublication(fileInput: any) {
    this.filePublication = <File>fileInput.target.files[0];
    //this.preview();
  }

  fileProgressCoverLetter(fileInput: any) {
    this.fileCoverLetter = <File>fileInput.target.files[0];
    //this.preview();
  }

  preview() {
    // Show preview 
    var mimeType = this.filePublication.type;
    if (mimeType.match(/image\/*/) == null) {
      return;
    }

    var reader = new FileReader();
    reader.readAsDataURL(this.filePublication);
    reader.onload = (_event) => {
      this.previewUrl = reader.result;
    }
  }

  onSubmitPublication() {
    const formData = new FormData();
    formData.append('file', this.filePublication);
    if (this.revisionCheckBox) {
      formData.append('revision', "true");
    }
    else {
      formData.append('revision', "false")
    }
    this.http.post('http://localhost:9000/xscience/publication/uploadPublication', formData)
      .subscribe(res => {
        console.log(res);
        alert('successfully UPLOADED PUBLICATION !!');
      })
  }


  onSubmitCoverLetter() {
    const formData = new FormData();
    formData.append('file', this.fileCoverLetter);
    this.http.post('http://localhost:9000/xscience/coverletter/uploadCoverLetter', formData)
      .subscribe(res => {
        console.log(res);
        alert('successfully UPLOADED COVER LETTER !!');
      })
  }

  cancel() {
    this.router.navigate(["/homepage"]);
  }

  getCurrentUser() {
    if (localStorage.getItem('currentUser') != null) {
      const currentUser: any = this.loginService.currentUserValue;
      this.currentUserRole = currentUser.role;
      this.currentUserEmail = currentUser.email;
    }
  }

  searchDocumentsText() {
    this.router.navigate(["search-documents-text"]);
  }

  uploadPublication() {
    this.router.navigate(["upload-publication"]);
  }
  searchDocumentsMetadata() {
    this.router.navigate(["search-documents-metadata"]);
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