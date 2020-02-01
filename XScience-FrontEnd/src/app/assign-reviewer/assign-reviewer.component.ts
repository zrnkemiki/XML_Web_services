import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { UserService } from '../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { DocumentService } from '../services/document.service';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-assign-reviewer',
  templateUrl: './assign-reviewer.component.html',
  styleUrls: ['./assign-reviewer.component.css']
})
export class AssignReviewerComponent implements OnInit {
  public users: User[];
  private currentUserRole: string;
  private currentUserEmail: string;

  constructor(private userService: UserService, private route: ActivatedRoute, private documentService: DocumentService, private router: Router, private loginService: LoginService) { }

  ngOnInit() {
    //get reviewers by DOCtitle from URL
    this.getCurrentUser();
    const title = this.route.snapshot.paramMap.get('title');
    this.getReviewerSugestion(title);
  }
  getReviewerSugestion(title: any) {
    this.userService.usersObservable.subscribe(users => this.users = users);
    this.userService.getReviewerSugestion(title);
  }
  getCurrentUser() {
    if (localStorage.getItem('currentUser') != null) {
      const currentUser: any = this.loginService.currentUserValue;
      this.currentUserRole = currentUser.role;
      this.currentUserEmail = currentUser.email;
    }
  }

  assignReviewer(email) {
    const title = this.route.snapshot.paramMap.get('title');
    this.documentService.assignReviewer(title, email);
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
}
