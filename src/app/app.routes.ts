import { Routes } from '@angular/router';
import { authGuard } from './guards/auth-guard';
import { LoginComponent } from './components/login/login.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  {
    path: 'dashboard',
    loadComponent: () => import('./components/dashboard/dashboard.component')
      .then(m => m.DashboardComponent),
    canActivate: [authGuard]
  },
  {
    path: 'produits',
    loadComponent: () => import('./components/produit/produit.component')
      .then(m => m.ProduitComponent),
    canActivate: [authGuard]
  },
  {
    path: 'entrepots',
    loadComponent: () => import('./components/entrepot/entrepot.component')
      .then(m => m.EntrepotComponent),
    canActivate: [authGuard]
  },
  {
    path: 'stocks',
    loadComponent: () => import('./components/stock/stock.component')
      .then(m => m.StockComponent),
    canActivate: [authGuard]
  },
  {
    path: 'mouvements',
    loadComponent: () => import('./components/mouvement/mouvement.component')
      .then(m => m.MouvementComponent),
    canActivate: [authGuard]
  },
  { path: '**', redirectTo: 'login' }
];