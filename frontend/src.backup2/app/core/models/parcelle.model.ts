export interface Parcelle {

  id: number;

  nom: string;

  superficie: number; // en hectares

  localisation: string;

  notes?: string;

  // Coordonnées GPS de la parcelle
  latitude?: number;

  longitude?: number;

  utilisateurId: number;
}