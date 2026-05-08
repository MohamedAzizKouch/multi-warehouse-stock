import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produit } from '../models/produit.model';

@Injectable({
  providedIn: 'root'
})
export class ProduitService {

  private url = 'http://localhost:8080/produit';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Produit[]> {
    return this.http.get<Produit[]>(`${this.url}/all`);
  }

  getById(id: number): Observable<Produit> {
    return this.http.get<Produit>(`${this.url}/${id}`);
  }

  getByCategorie(categorie: string): Observable<Produit[]> {
    return this.http.get<Produit[]>(`${this.url}/categorie/${categorie}`);
  }

  rechercherParNom(nom: string): Observable<Produit[]> {
    return this.http.get<Produit[]>(`${this.url}/recherche/${nom}`);
  }

  add(produit: Produit): Observable<string> {
    return this.http.post(`${this.url}/add`, produit, { responseType: 'text' });
  }

  update(id: number, produit: Produit): Observable<string> {
    return this.http.put(`${this.url}/update/${id}`, produit, { responseType: 'text' });
  }

  delete(id: number): Observable<string> {
    return this.http.delete(`${this.url}/delete/${id}`, { responseType: 'text' });
  }
}