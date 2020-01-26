import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { UserService } from '../services/user.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-assign-reviewer',
  templateUrl: './assign-reviewer.component.html',
  styleUrls: ['./assign-reviewer.component.css']
})
export class AssignReviewerComponent implements OnInit {
  public users: User[];

  constructor(private userService: UserService, private route: ActivatedRoute) { }

  ngOnInit() {
    //get reviewers by DOCtitle from URL
    const title = this.route.snapshot.paramMap.get('title');
    this.getReviewerSugestion(title);
  }
  getReviewerSugestion(title: any) {
    this.userService.usersObservable.subscribe(users => this.users = users);
    this.userService.getReviewerSugestion(title);
  }
  

  assignReviewer(email){
    //assign reviewer (email + docTitle)
    //redirect to homepage
  }
}
