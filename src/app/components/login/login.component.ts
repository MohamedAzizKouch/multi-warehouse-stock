import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  email = '';
  motDePasse = '';
  erreur = '';
  chargement = false;

  constructor(private authService: AuthService, private router: Router) {}

  login() {
  if (!this.email || !this.motDePasse) {
    this.erreur = 'Veuillez remplir tous les champs';
    return;
  }

  this.chargement = true;
  this.erreur = '';

  this.authService.login(this.email, this.motDePasse).subscribe({
    next: (users: any[]) => {
      const user = users.find((u: any) => u.email === this.email);
      if (user) {
        this.authService.saveRole(user.role);
      }
      this.chargement = false;
      this.router.navigate(['/dashboard']);
    },
    error: (err) => {
      this.chargement = false;
      console.error('Erreur login:', err);   // ← voir dans F12
      if (err.status === 0) {
        this.erreur = '❌ Backend inaccessible — vérifiez que Spring Boot tourne sur le port 8080';
      } else if (err.status === 401) {
        this.erreur = '❌ Email ou mot de passe incorrect';
      } else {
        this.erreur = `❌ Erreur ${err.status} : ${err.message}`;
      }
      localStorage.removeItem('credentials');
    }
  });
}
}