import { Routes } from '@angular/router';
import { authGuard } from './guards/auth-guard';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ProduitComponent } from './components/produit/produit.component';
import { EntrepotComponent } from './components/entrepot/entrepot.component';
import { StockComponent } from './components/stock/stock.component';
import { MouvementComponent } from './components/mouvement/mouvement.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard',  component: DashboardComponent,  canActivate: [authGuard] },
  { path: 'produits',   component: ProduitComponent,    canActivate: [authGuard] },
  { path: 'entrepots',  component: EntrepotComponent,   canActivate: [authGuard] },
  { path: 'stocks',     component: StockComponent,      canActivate: [authGuard] },
  { path: 'mouvements', component: MouvementComponent,  canActivate: [authGuard] },
  { path: '**', redirectTo: 'login' }
];