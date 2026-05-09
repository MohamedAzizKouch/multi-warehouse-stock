import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Entrepot } from '../models/entrepot.model';

@Injectable({
  providedIn: 'root'
})
export class EntrepotService {

  private url = 'http://localhost:9090/entrepot';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Entrepot[]> {
    return this.http.get<Entrepot[]>(`${this.url}/all`);
  }

  getById(id: number): Observable<Entrepot> {
    return this.http.get<Entrepot>(`${this.url}/${id}`);
  }

  add(entrepot: Entrepot): Observable<string> {
    return this.http.post(`${this.url}/add`, entrepot, { responseType: 'text' });
  }

  update(id: number, entrepot: Entrepot): Observable<string> {
    return this.http.put(`${this.url}/update/${id}`, entrepot, { responseType: 'text' });
  }

  delete(id: number): Observable<string> {
    return this.http.delete(`${this.url}/delete/${id}`, { responseType: 'text' });
  }
}