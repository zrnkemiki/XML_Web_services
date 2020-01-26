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
  }
  constructor(private http: HttpClient, private router: Router) { }

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




}