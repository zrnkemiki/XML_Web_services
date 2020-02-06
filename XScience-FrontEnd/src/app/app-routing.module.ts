import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent} from './login/login.component'
import { HomepageComponent } from './homepage/homepage.component';
import { SearchDocumentsTextComponent } from './search-documents-text/search-documents-text.component';
import { UploadComponent } from './upload/upload.component';
import { SearchDocumentsMetadataComponent } from './search-documents-metadata/search-documents-metadata.component';
import { MyDocumentsComponent } from './my-documents/my-documents.component';
import { DocumentViewerComponent } from './document-viewer/document-viewer.component';
import { EditorDocumentManagerComponent } from './editor-document-manager/editor-document-manager.component';
import { DocumentReviewsComponent } from './document-reviews/document-reviews.component';
import { AssignReviewerComponent } from './assign-reviewer/assign-reviewer.component';
import { MyReviewsComponent } from './my-reviews/my-reviews.component';
import { ReviewComponent } from './review/review.component';
import { ReviewViewerComponent } from './review-viewer/review-viewer.component';
import { CoverLetterViewerComponent } from './cover-letter-viewer/cover-letter-viewer.component';



const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: HomepageComponent },
  { path: 'homepage', component: HomepageComponent },
  { path: 'my-documents', component: MyDocumentsComponent  },
  { path: 'search-documents-text', component: SearchDocumentsTextComponent  },
  { path: 'search-documents-metadata', component: SearchDocumentsMetadataComponent  },
  { path: 'upload-publication', component: UploadComponent},
  { path: 'document-view/:title', component: DocumentViewerComponent },
  { path: 'editor-document-manager', component: EditorDocumentManagerComponent},
  { path: 'document-reviews/:title', component: DocumentReviewsComponent},
  { path: 'assign-reviewer/:title', component: AssignReviewerComponent},
  { path: 'my-reviews', component: MyReviewsComponent},
  { path: 'review/:title', component: ReviewComponent},
  { path: 'review-view/:title', component: ReviewViewerComponent},
  { path: 'coverLetter-view/:title', component: CoverLetterViewerComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
