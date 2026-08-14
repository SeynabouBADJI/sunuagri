export interface CalendrierCultural {
  id: number;
  region: string;
  saison: string;
  periodeSemis: string;
  periodeFloraison: string;
  periodeRecolte: string;
  planteId: number;
  planteNom?: string;
}