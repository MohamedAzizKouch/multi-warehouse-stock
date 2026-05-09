import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EntrepotService } from '../../services/entrepot.service';
import { Entrepot } from '../../models/entrepot.model';

@Component({
  selector: 'app-entrepot',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './entrepot.component.html',
  styleUrl: './entrepot.component.css'
})
export class EntrepotComponent implements OnInit {

  entrepots: Entrepot[] = [];
  entrepotsFiltres: Entrepot[] = [];
  recherche = '';
  message = '';
  messageType = '';
  afficherFormulaire = false;
  modeEdition = false;

  entrepotForm: Entrepot = {
    nom: '', adresse: '', capacite: 0
  };

  constructor(private entrepotService: EntrepotService) {}

  ngOnInit(): void {
    this.chargerEntrepots();
  }

  chargerEntrepots(): void {
    this.entrepotService.getAll().subscribe(data => {
      this.entrepots = data;
      this.entrepotsFiltres = data;
    });
  }

  filtrer(): void {
    this.entrepotsFiltres = this.entrepots.filter(e =>
      e.nom.toLowerCase().includes(this.recherche.toLowerCase()) ||
      e.adresse.toLowerCase().includes(this.recherche.toLowerCase())
    );
  }

  ouvrirAjout(): void {
    this.entrepotForm = { nom: '', adresse: '', capacite: 0 };
    this.modeEdition = false;
    this.afficherFormulaire = true;
  }

  ouvrirEdition(e: Entrepot): void {
    this.entrepotForm = { ...e };
    this.modeEdition = true;
    this.afficherFormulaire = true;
  }

  sauvegarder(): void {
    if (this.modeEdition && this.entrepotForm.idEntrepot) {
      this.entrepotService.update(this.entrepotForm.idEntrepot, this.entrepotForm).subscribe({
        next: (msg) => {
          this.afficherMessage(msg, 'success');
          this.chargerEntrepots();
          this.afficherFormulaire = false;
        },
        error: (err) => this.afficherMessage(err.error || 'Erreur', 'danger')
      });
    } else {
      this.entrepotService.add(this.entrepotForm).subscribe({
        next: (msg) => {
          this.afficherMessage(msg, 'success');
          this.chargerEntrepots();
          this.afficherFormulaire = false;
        },
        error: (err) => this.afficherMessage(err.error || 'Erreur', 'danger')
      });
    }
  }

  supprimer(id: number): void {
    if (confirm('Confirmer la suppression ?')) {
      this.entrepotService.delete(id).subscribe({
        next: (msg) => {
          this.afficherMessage(msg, 'success');
          this.chargerEntrepots();
        },
        error: () => this.afficherMessage('Erreur lors de la suppression', 'danger')
      });
    }
  }

  afficherMessage(msg: string, type: string): void {
    this.message = msg;
    this.messageType = type;
    setTimeout(() => this.message = '', 3000);
  }
}