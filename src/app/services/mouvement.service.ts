import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MouvementStock } from '../models/mouvement.model';
import { environment } from '../../environments/environment';
@Injectable({
  providedIn: 'root'
})
export class MouvementService {

  private url = `${environment.apiUrl}/mouvement`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<MouvementStock[]> {
    return this.http.get<MouvementStock[]>(`${this.url}/all`);
  }

  getByProduit(idProduit: number): Observable<MouvementStock[]> {
    return this.http.get<MouvementStock[]>(`${this.url}/produit/${idProduit}`);
  }

  getByEntrepot(idEntrepot: number): Observable<MouvementStock[]> {
    return this.http.get<MouvementStock[]>(`${this.url}/entrepot/${idEntrepot}`);
  }

  // ✅ Entrée de stock
  entree(mouvement: MouvementStock): Observable<string> {
    return this.http.post(`${this.url}/entree`, mouvement, { responseType: 'text' });
  }

  // ✅ Sortie de stock
  sortie(mouvement: MouvementStock): Observable<string> {
    return this.http.post(`${this.url}/sortie`, mouvement, { responseType: 'text' });
  }

  delete(id: number): Observable<string> {
    return this.http.delete(`${this.url}/delete/${id}`, { responseType: 'text' });
  }
}