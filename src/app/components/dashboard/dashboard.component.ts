import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { StockService } from '../../services/stock.service';
import { ProduitService } from '../../services/produit.service';
import { EntrepotService } from '../../services/entrepot.service';
import { MouvementService } from '../../services/mouvement.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

  totalProduits = 0;
  totalEntrepots = 0;
  totalStocks = 0;
  totalMouvements = 0;
  stocksEnAlerte: any[] = [];
  derniersMouvements: any[] = [];

  constructor(
    private stockService: StockService,
    private produitService: ProduitService,
    private entrepotService: EntrepotService,
    private mouvementService: MouvementService
  ) {}

  ngOnInit(): void {
    this.chargerStats();
  }

  chargerStats(): void {
    this.produitService.getAll().subscribe(data => this.totalProduits = data.length);
    this.entrepotService.getAll().subscribe(data => this.totalEntrepots = data.length);
    this.stockService.getAll().subscribe(data => this.totalStocks = data.length);
    this.mouvementService.getAll().subscribe(data => {
      this.totalMouvements = data.length;
      this.derniersMouvements = data.slice(-5).reverse();
    });
    this.stockService.getAlertes().subscribe(data => this.stocksEnAlerte = data);
  }
}