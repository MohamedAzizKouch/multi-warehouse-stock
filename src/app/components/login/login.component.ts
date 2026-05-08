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
        // Trouver l'utilisateur connecté et sauvegarder son rôle
        const user = users.find((u: any) => u.email === this.email);
        if (user) {
          this.authService.saveRole(user.role);
        }
        this.chargement = false;
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.chargement = false;
        this.erreur = 'Email ou mot de passe incorrect';
        localStorage.removeItem('credentials');
      }
    });
  }
}