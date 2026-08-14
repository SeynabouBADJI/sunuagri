export type Role = 'AGRICULTEUR' | 'ADMINISTRATEUR';

export interface Utilisateur {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  role: Role;
  localisation?: string;
}