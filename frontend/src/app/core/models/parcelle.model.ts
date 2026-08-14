export interface Parcelle {
  id: number;
  nom: string;
  superficie: number; // en hectares
  localisation: string;
  notes?: string;
  utilisateurId: number;
}