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


  private currentUserRole: string;
  private currentUserEmail: string;
  constructor(private http: HttpClient, private router: Router, private loginService: LoginService) { }

  fileData: File = null;
  previewUrl: any = null;
  fileUploadProgress: string = null;
  uploadedFilePath: string = null;

  fileProgress(fileInput: any) {
    this.fileData = <File>fileInput.target.files[0];
    //this.preview();
  }

  preview() {
    // Show preview 
    var mimeType = this.fileData.type;
    if (mimeType.match(/image\/*/) == null) {
      return;
    }

    var reader = new FileReader();
    reader.readAsDataURL(this.fileData);
    reader.onload = (_event) => {
      this.previewUrl = reader.result;
    }
  }

  onSubmit() {
    const formData = new FormData();
    formData.append('file', this.fileData);
    this.http.post('http://localhost:9000/xscience/publication/rest/uploadPublication', formData)
      .subscribe(res => {
        console.log(res);
        alert('SUCCESS !!');
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