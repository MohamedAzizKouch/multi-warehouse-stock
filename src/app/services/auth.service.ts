import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url = 'http://localhost:9090';

  constructor(private http: HttpClient) {}

  login(email: string, motDePasse: string): Observable<any> {
    // Encode les credentials en Base64 pour Basic Auth
    const credentials = btoa(`${email}:${motDePasse}`);
    localStorage.setItem('credentials', credentials);
    localStorage.setItem('email', email);

    // Test de connexion via un endpoint protégé
    return this.http.get(`${this.url}/utilisateur/all`);
  }

  logout(): void {
    localStorage.removeItem('credentials');
    localStorage.removeItem('email');
    localStorage.removeItem('role');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('credentials');
  }

  getEmail(): string {
    return localStorage.getItem('email') || '';
  }

  saveRole(role: string): void {
    localStorage.setItem('role', role);
  }

  getRole(): string {
    return localStorage.getItem('role') || '';
  }
}