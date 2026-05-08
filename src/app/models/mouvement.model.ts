import { Produit } from './produit.model';
import { Entrepot } from './entrepot.model';

export interface MouvementStock {
  idMouvement?: number;
  type?: 'ENTREE' | 'SORTIE';
  quantite: number;
  date?: string;
  produit: Produit;
  entrepot: Entrepot;
}