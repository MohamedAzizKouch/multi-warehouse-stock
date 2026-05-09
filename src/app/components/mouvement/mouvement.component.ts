import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MouvementService } from '../../services/mouvement.service';
import { ProduitService } from '../../services/produit.service';
import { EntrepotService } from '../../services/entrepot.service';
import { MouvementStock } from '../../models/mouvement.model';
import { Produit } from '../../models/produit.model';
import { Entrepot } from '../../models/entrepot.model';

@Component({
  selector: 'app-mouvement',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './mouvement.component.html',
  styleUrl: './mouvement.component.css'
})
export class MouvementComponent implements OnInit {

  mouvements: MouvementStock[] = [];
  mouvementsFiltres: MouvementStock[] = [];
  produits: Produit[] = [];
  entrepots: Entrepot[] = [];
  recherche = '';
  filtreType = '';
  message = '';
  messageType = '';
  afficherFormulaire = false;
  typeMouvement: 'ENTREE' | 'SORTIE' = 'ENTREE';

  mouvementForm: any = {
    quantite: 0,
    produit: { idProduit: null },
    entrepot: { idEntrepot: null }
  };

  constructor(
    private mouvementService: MouvementService,
    private produitService: ProduitService,
    private entrepotService: EntrepotService
  ) {}

  ngOnInit(): void {
    this.chargerMouvements();
    this.produitService.getAll().subscribe(data => this.produits = data);
    this.entrepotService.getAll().subscribe(data => this.entrepots = data);
  }

  chargerMouvements(): void {
    this.mouvementService.getAll().subscribe(data => {
      this.mouvements = data.reverse();
      this.mouvementsFiltres = this.mouvements;
    });
  }

  filtrer(): void {
    this.mouvementsFiltres = this.mouvements.filter(m => {
      const matchRecherche =
        m.produit?.nom.toLowerCase().includes(this.recherche.toLowerCase()) ||
        m.entrepot?.nom.toLowerCase().includes(this.recherche.toLowerCase());
      const matchType = this.filtreType ? m.type === this.filtreType : true;
      return matchRecherche && matchType;
    });
  }

  ouvrirFormulaire(type: 'ENTREE' | 'SORTIE'): void {
    this.typeMouvement = type;
    this.mouvementForm = {
      quantite: 0,
      produit: { idProduit: null },
      entrepot: { idEntrepot: null }
    };
    this.afficherFormulaire = true;
  }

  sauvegarder(): void {
    const operation = this.typeMouvement === 'ENTREE'
      ? this.mouvementService.entree(this.mouvementForm)
      : this.mouvementService.sortie(this.mouvementForm);

    operation.subscribe({
      next: (msg) => {
        this.afficherMessage(msg, 'success');
        this.chargerMouvements();
        this.afficherFormulaire = false;
      },
      error: (err) => this.afficherMessage(err.error || 'Erreur', 'danger')
    });
  }

  supprimer(id: number): void {
    if (confirm('Confirmer la suppression ?')) {
      this.mouvementService.delete(id).subscribe({
        next: (msg) => {
          this.afficherMessage(msg, 'success');
          this.chargerMouvements();
        },
        error: () => this.afficherMessage('Erreur suppression', 'danger')
      });
    }
  }

  afficherMessage(msg: string, type: string): void {
    this.message = msg;
    this.messageType = type;
    setTimeout(() => this.message = '', 4000);
  }
}