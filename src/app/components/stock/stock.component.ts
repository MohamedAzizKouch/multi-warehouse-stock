import { Component, OnInit } from '@angular/core';
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
    private entrepotService: EntrepotService
  ) {}

  ngOnInit(): void {
    this.chargerStocks();
    this.chargerProduits();
    this.chargerEntrepots();
    this.chargerAlertes();
  }

  chargerStocks(): void {
    this.stockService.getAll().subscribe(data => {
      this.stocks = data;
      this.stocksFiltres = data;
    });
  }

  chargerProduits(): void {
    this.produitService.getAll().subscribe(data => this.produits = data);
  }

  chargerEntrepots(): void {
    this.entrepotService.getAll().subscribe(data => this.entrepots = data);
  }

  chargerAlertes(): void {
    this.stockService.getAlertes().subscribe(data => this.alertes = data);
  }

  filtrer(): void {
    this.stocksFiltres = this.stocks.filter(s =>
      s.produit?.nom.toLowerCase().includes(this.recherche.toLowerCase()) ||
      s.entrepot?.nom.toLowerCase().includes(this.recherche.toLowerCase())
    );
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
    if (this.modeEdition && this.stockForm.idStock) {
      this.stockService.update(this.stockForm.idStock, this.stockForm).subscribe({
        next: (msg) => {
          this.afficherMessage(msg, 'success');
          this.chargerStocks();
          this.chargerAlertes();
          this.afficherFormulaire = false;
        },
        error: (err) => this.afficherMessage(err.error || 'Erreur', 'danger')
      });
    } else {
      this.stockService.add(this.stockForm).subscribe({
        next: (msg) => {
          this.afficherMessage(msg, 'success');
          this.chargerStocks();
          this.chargerAlertes();
          this.afficherFormulaire = false;
        },
        error: (err) => this.afficherMessage(err.error || 'Erreur', 'danger')
      });
    }
  }

  supprimer(id: number): void {
    if (confirm('Confirmer la suppression ?')) {
      this.stockService.delete(id).subscribe({
        next: (msg) => {
          this.afficherMessage(msg, 'success');
          this.chargerStocks();
          this.chargerAlertes();
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
    setTimeout(() => this.message = '', 3000);
  }
}