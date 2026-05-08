export interface Utilisateur {
  idUtilisateur?: number;
  nom: string;
  email: string;
  motDePasse?: string;
  role: 'ADMIN' | 'GESTIONNAIRE' | 'OBSERVATEUR';
}