import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { User } from '../model/user';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { UserCredentials } from '../model/UserCredentials';
import { HttpBackend, HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  //private userUrl = "localhost:9000/xscience/user/login";
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private handler: HttpBackend, private router: Router, private http: HttpClient) {
    this.http = new HttpClient(handler);
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    let user: User = new User();
    if (localStorage.getItem('currentUser')) {
      user.deserialize(JSON.parse(localStorage.getItem('currentUser')));
      if (user.email != this.currentUserSubject.value.email) {
        this.currentUserSubject.next(user);
      }
    }
    return this.currentUserSubject.value;
  }


  login(credentials: UserCredentials) {
    return this.http.post<any>("http://localhost:9000/xscience/login", credentials)
      .pipe(map(userDTO => {
        if (userDTO && userDTO.jwtToken) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          let user: User = new User().deserialize(userDTO);
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
        }
        return userDTO;
      })).subscribe(
        (data) => { },
        error => { alert("Pogrešno ime i/ili lozinka, pokušajte ponovo") }
      );

  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.router.navigate(["/homepage"]);
  }

}
