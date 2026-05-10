import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
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
    private entrepotService: EntrepotService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.chargerMouvements();
    this.produitService.getAll().subscribe({
      next: (data) => {
        this.produits = data;
        this.cdr.detectChanges();
      }
    });
    this.entrepotService.getAll().subscribe({
      next: (data) => {
        this.entrepots = data;
        this.cdr.detectChanges();
      }
    });
  }

  chargerMouvements(): void {
    this.mouvementService.getAll().subscribe({
      next: (data) => {
        this.mouvements = [...data].reverse();
        this.mouvementsFiltres = [...this.mouvements];
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Erreur chargement mouvements', err)
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
    this.cdr.detectChanges();
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
  // ✅ Convertir les IDs string → number
  const mouvementAEnvoyer = {
    ...this.mouvementForm,
    type: this.typeMouvement,
    produit: { idProduit: Number(this.mouvementForm.produit.idProduit) },
    entrepot: { idEntrepot: Number(this.mouvementForm.entrepot.idEntrepot) }
  };

  const operation = this.typeMouvement === 'ENTREE'
    ? this.mouvementService.entree(mouvementAEnvoyer)
    : this.mouvementService.sortie(mouvementAEnvoyer);

  operation.subscribe({
    next: (msg) => {
      this.afficherFormulaire = false;
      this.chargerMouvements();
      this.afficherMessage(msg, 'success');
    },
    error: (err) => this.afficherMessage(err.error || 'Erreur', 'danger')
  });
}

  supprimer(id: number): void {
    if (confirm('Confirmer la suppression ?')) {
      this.mouvementService.delete(id).subscribe({
        next: (msg) => {
          this.chargerMouvements();
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
    }, 4000);
  }
}