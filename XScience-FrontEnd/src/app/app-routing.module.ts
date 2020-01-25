import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent} from './login/login.component'
import { HomepageComponent } from './homepage/homepage.component';
import { SearchDocumentsTextComponent } from './search-documents-text/search-documents-text.component';
import { UploadComponent } from './upload/upload.component';
import { SearchDocumentsMetadataComponent } from './search-documents-metadata/search-documents-metadata.component';
import { MyDocumentsComponent } from './my-documents/my-documents.component';
import { DocumentViewerComponent } from './document-viewer/document-viewer.component';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: HomepageComponent },
  { path: 'homepage', component: HomepageComponent },
  { path: 'my-documents', component: MyDocumentsComponent  },
  { path: 'search-documents-text', component: SearchDocumentsTextComponent  },
  { path: 'search-documents-metadata', component: SearchDocumentsMetadataComponent  },
  { path: 'upload-publication', component: UploadComponent},
  { path: 'document-view/:title', component: DocumentViewerComponent },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
