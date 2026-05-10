import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProduitService } from '../../services/produit.service';
import { Produit } from '../../models/produit.model';

@Component({
  selector: 'app-produit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './produit.component.html',
  styleUrl: './produit.component.css'
})
export class ProduitComponent implements OnInit {

  produits: Produit[] = [];
  produitsFiltres: Produit[] = [];
  recherche = '';
  message = '';
  messageType = '';
  afficherFormulaire = false;
  modeEdition = false;

  produitForm: Produit = {
    nom: '', categorie: '', prix: 0, fournisseur: '', seuilMin: 0
  };

  constructor(
    private produitService: ProduitService,
    private cdr: ChangeDetectorRef   // ← ajouter
  ) {}

  ngOnInit(): void {
    this.chargerProduits();
  }

  chargerProduits(): void {
    this.produitService.getAll().subscribe({
      next: (data) => {
        this.produits = data;
        this.produitsFiltres = [...data];
        this.cdr.detectChanges();   // ← forcer la mise à jour
      },
      error: (err) => console.error('Erreur', err)
    });
  }

  filtrer(): void {
    this.produitsFiltres = this.produits.filter(p =>
      p.nom.toLowerCase().includes(this.recherche.toLowerCase()) ||
      p.categorie.toLowerCase().includes(this.recherche.toLowerCase()) ||
      p.fournisseur.toLowerCase().includes(this.recherche.toLowerCase())
    );
    this.cdr.detectChanges();
  }

  ouvrirAjout(): void {
    this.produitForm = { nom: '', categorie: '', prix: 0, fournisseur: '', seuilMin: 0 };
    this.modeEdition = false;
    this.afficherFormulaire = true;
  }

  ouvrirEdition(p: Produit): void {
    this.produitForm = { ...p };
    this.modeEdition = true;
    this.afficherFormulaire = true;
  }

  sauvegarder(): void {
    if (this.modeEdition && this.produitForm.idProduit) {
      this.produitService.update(this.produitForm.idProduit, this.produitForm).subscribe({
        next: (msg) => {
          this.afficherFormulaire = false;
          this.chargerProduits();
          this.afficherMessage(msg, 'success');
        },
        error: (err) => this.afficherMessage(err.error || 'Erreur', 'danger')
      });
    } else {
      this.produitService.add(this.produitForm).subscribe({
        next: (msg) => {
          this.afficherFormulaire = false;
          this.chargerProduits();
          this.afficherMessage(msg, 'success');
        },
        error: (err) => this.afficherMessage(err.error || 'Erreur', 'danger')
      });
    }
  }

  supprimer(id: number): void {
    if (confirm('Confirmer la suppression ?')) {
      this.produitService.delete(id).subscribe({
        next: (msg) => {
          this.chargerProduits();
          this.afficherMessage(msg, 'success');
        },
        error: () => this.afficherMessage('Erreur suppression', 'danger')
      });
    }
  }

  afficherMessage(msg: string, type: string): void {
    this.message = msg;
    this.messageType = type;
    this.cdr.detectChanges();
    setTimeout(() => {
      this.message = '';
      this.cdr.detectChanges();
    }, 3000);
  }
}