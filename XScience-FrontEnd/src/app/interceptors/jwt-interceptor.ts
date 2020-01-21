import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginService } from '../services/login.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(private loginService: LoginService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with jwt token if available
        let currentUser = this.loginService.currentUserValue;
        if (currentUser && currentUser.jwttoken) {
            request = request.clone({
                setHeaders: {
                    "X-Auth-T" : `${currentUser.jwttoken}`,
                    "Content-Type" : 'application/json;charset=UTF-8'
                }
            });
        }
        return next.handle(request);
    }
}
