import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StockService } from '../../services/stock.service';
import { ProduitService } from '../../services/produit.service';
import { EntrepotService } from '../../services/entrepot.service';
import { Stock } from '../../models/stock.model';
import { Produit } from '../../models/produit.model';
import { Entrepot } from '../../models/entrepot.model';

@Component({
  selector: 'app-stock',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './stock.component.html',
  styleUrl: './stock.component.css'
})
export class StockComponent implements OnInit {

  stocks: Stock[] = [];
  stocksFiltres: Stock[] = [];
  produits: Produit[] = [];
  entrepots: Entrepot[] = [];
  alertes: Stock[] = [];
  recherche = '';
  message = '';
  messageType = '';
  afficherFormulaire = false;
  modeEdition = false;
  afficherAlertes = false;

  stockForm: any = {
    quantite: 0,
    seuilAlerte: 0,
    produit: { idProduit: null },
    entrepot: { idEntrepot: null }
  };

  constructor(
    private stockService: StockService,
    private produitService: ProduitService,
    private entrepotService: EntrepotService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.chargerStocks();
    this.chargerProduits();
    this.chargerEntrepots();
    this.chargerAlertes();
  }

  chargerStocks(): void {
    this.stockService.getAll().subscribe({
      next: (data) => {
        this.stocks = data;
        this.stocksFiltres = [...data];
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Erreur chargement stocks', err)
    });
  }

  chargerProduits(): void {
    this.produitService.getAll().subscribe({
      next: (data) => {
        this.produits = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Erreur chargement produits', err)
    });
  }

  chargerEntrepots(): void {
    this.entrepotService.getAll().subscribe({
      next: (data) => {
        this.entrepots = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Erreur chargement entrepots', err)
    });
  }

  chargerAlertes(): void {
    this.stockService.getAlertes().subscribe({
      next: (data) => {
        this.alertes = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Erreur alertes', err)
    });
  }

  filtrer(): void {
    this.stocksFiltres = this.stocks.filter(s =>
      s.produit?.nom.toLowerCase().includes(this.recherche.toLowerCase()) ||
      s.entrepot?.nom.toLowerCase().includes(this.recherche.toLowerCase())
    );
    this.cdr.detectChanges();
  }

  ouvrirAjout(): void {
    this.stockForm = {
      quantite: 0, seuilAlerte: 0,
      produit: { idProduit: null },
      entrepot: { idEntrepot: null }
    };
    this.modeEdition = false;
    this.afficherFormulaire = true;
  }

  ouvrirEdition(s: Stock): void {
    this.stockForm = {
      idStock: s.idStock,
      quantite: s.quantite,
      seuilAlerte: s.seuilAlerte,
      produit: { idProduit: s.produit?.idProduit },
      entrepot: { idEntrepot: s.entrepot?.idEntrepot }
    };
    this.modeEdition = true;
    this.afficherFormulaire = true;
  }

  sauvegarder(): void {
  // ✅ Convertir les IDs string → number
  const stockAEnvoyer = {
    ...this.stockForm,
    produit: { idProduit: Number(this.stockForm.produit.idProduit) },
    entrepot: { idEntrepot: Number(this.stockForm.entrepot.idEntrepot) }
  };

  if (this.modeEdition && this.stockForm.idStock) {
    this.stockService.update(this.stockForm.idStock, stockAEnvoyer).subscribe({
      next: (msg) => {
        this.afficherFormulaire = false;
        this.chargerStocks();
        this.chargerAlertes();
        this.afficherMessage(msg, 'success');
      },
      error: (err) => this.afficherMessage(err.error || 'Erreur', 'danger')
    });
  } else {
    this.stockService.add(stockAEnvoyer).subscribe({
      next: (msg) => {
        this.afficherFormulaire = false;
        this.chargerStocks();
        this.chargerAlertes();
        this.afficherMessage(msg, 'success');
      },
      error: (err) => this.afficherMessage(err.error || 'Erreur', 'danger')
    });
  }
}
  supprimer(id: number): void {
    if (confirm('Confirmer la suppression ?')) {
      this.stockService.delete(id).subscribe({
        next: (msg) => {
          this.chargerStocks();
          this.chargerAlertes();
          this.afficherMessage(msg, 'success');
        },
        error: () => this.afficherMessage('Erreur suppression', 'danger')
      });
    }
  }

  estEnAlerte(s: Stock): boolean {
    return s.quantite <= s.seuilAlerte;
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