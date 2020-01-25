import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomepageComponent } from './homepage/homepage.component';
import { SearchDocumentsTextComponent } from './search-documents-text/search-documents-text.component';
import { JwtInterceptor } from './interceptors/jwt-interceptor';
import { UploadComponent } from './upload/upload.component';
import { MyDocumentsComponent } from './my-documents/my-documents.component';
import { SearchDocumentsMetadataComponent } from './search-documents-metadata/search-documents-metadata.component';
import { DocumentViewerComponent } from './document-viewer/document-viewer.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomepageComponent,
    SearchDocumentsTextComponent,
    UploadComponent,
    MyDocumentsComponent,
    SearchDocumentsMetadataComponent,
    DocumentViewerComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule
    ],
  providers:[
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
