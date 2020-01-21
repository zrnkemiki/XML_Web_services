import { Injectable, Injector, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpClientModule, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GenericService {

  constructor(private http: HttpClient, @Inject('BASE_API_URL') private baseUrl: string) {}

  getAll<T>(relativeUrl: string): Observable<T[]> {
    return this.http.get<T[]>(this.baseUrl + relativeUrl);
  }

  get<T>(relativeUrl: string): Observable<T> {
    // const params: HttpParams = new HttpParams().set('_id',id);
    return this.http.get<T>(this.baseUrl + relativeUrl);
  }

  getById<T>(relativeUrl: string, id: number): Observable<T> {
    // const params: HttpParams = new HttpParams().set('_id',id);
    return this.http.get<T>(this.baseUrl + relativeUrl + `/${id}`);
  }

  getListById<T>(relativeUrl: string, id: number): Observable<T[]> {
    // const params: HttpParams = new HttpParams().set('_id',id);
    return this.http.get<T[]>(this.baseUrl + relativeUrl + `/${id}`);
  }

  getByName<T>(relativeUrl: string, name: string): Observable<T> {
    // const params: HttpParams = new HttpParams().set('_id',id);
    return this.http.get<T>(this.baseUrl + relativeUrl + `/${name}`);
  }
  getListByName<T>(relativeUrl: string, name: string): Observable<T[]> {
    // const params: HttpParams = new HttpParams().set('_id',id);
    return this.http.get<T[]>(this.baseUrl + relativeUrl + `/${name}`);
  }

  put<T>(relativeUrl: string, t: T): Observable<T> {
    const headers: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.put<T>(this.baseUrl + relativeUrl, t, { headers });
  }

  save<T>(relativeUrl: string, t: T) {
    const headers: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post(this.baseUrl + relativeUrl, t,  { headers });
  }

  post(relativeUrl: string, t: any) {
    const headers: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post(this.baseUrl + relativeUrl, t, { headers });
  }

  delete(relativeUrl: string, id: number) {
    return this.http.delete(this.baseUrl + relativeUrl + `/${id}` + '/delete');
  }
  search<T>(relativeUrl: string, name: string): Observable<T[]> {
    return this.http.get<T[]>(this.baseUrl + relativeUrl + `/${name}`);
  }
  findQuery<T>(relativeUrl: string, hotelName: string, dateFrom: string, dateUntil: string, numberOfBeds: string): Observable<T[]> {
    return this.http.get<T[]>(this.baseUrl + relativeUrl + `/${hotelName}` + `/${dateFrom}` + `/${dateUntil}` + `/${numberOfBeds}`);
  }

}
