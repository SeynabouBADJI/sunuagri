export interface Alerte {
  id: number;
  titre: string;
  message: string;
  dateCreation: string;
  type: string;
  region: string;
  maladieId: number;
}