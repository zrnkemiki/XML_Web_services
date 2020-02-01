import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersSource = new BehaviorSubject<User[]>([]);
  usersObservable = this.usersSource.asObservable();
  public users: User[];
  private currentUserRole: string;

  private documentForView : string;
  constructor(private http: HttpClient, private loginService: LoginService) { }


  getReviewerSugestion(title) {
    this.http.get<User[]>("http://localhost:9000/xscience/publication/" + title + "/reviewer-suggestion")
      .subscribe(users => {
        this.users = users;
        this.usersSource.next(this.users);
      });
  }

  

}
