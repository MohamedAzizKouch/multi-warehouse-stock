import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Stock } from '../models/stock.model';
import { environment } from '../../environments/environment';
@Injectable({
  providedIn: 'root'
})
export class StockService {

  private url = `${environment.apiUrl}/stock`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Stock[]> {
    return this.http.get<Stock[]>(`${this.url}/all`);
  }

  getById(id: number): Observable<Stock> {
    return this.http.get<Stock>(`${this.url}/${id}`);
  }

  getByEntrepot(idEntrepot: number): Observable<Stock[]> {
    return this.http.get<Stock[]>(`${this.url}/entrepot/${idEntrepot}`);
  }

  getByProduit(idProduit: number): Observable<Stock[]> {
    return this.http.get<Stock[]>(`${this.url}/produit/${idProduit}`);
  }

  // ⚠️ Stocks en alerte
  getAlertes(): Observable<Stock[]> {
    return this.http.get<Stock[]>(`${this.url}/alertes`);
  }

  add(stock: Stock): Observable<string> {
    return this.http.post(`${this.url}/add`, stock, { responseType: 'text' });
  }

  update(id: number, stock: Stock): Observable<string> {
    return this.http.put(`${this.url}/update/${id}`, stock, { responseType: 'text' });
  }

  delete(id: number): Observable<string> {
    return this.http.delete(`${this.url}/delete/${id}`, { responseType: 'text' });
  }
}