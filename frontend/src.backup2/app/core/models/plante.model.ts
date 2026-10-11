export interface Plante {
  id: number;
  nomCommun: string;
  nomScientifique: string;
  famille: string;
  cycleVegetatif: number; // en jours
  description: string;
  imageUrl?: string;
}