import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest, HttpEvent } from '@angular/common/http';
import { LoginService } from '../services/login.service';
import { Observable } from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(private loginService : LoginService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with jwt token if available
        let currentUser = this.loginService.currentUserValue;
        if (currentUser && currentUser.jwtToken) {
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${currentUser.jwtToken}`                    
                },
                
            });
        }
        return next.handle(request);
    }
}