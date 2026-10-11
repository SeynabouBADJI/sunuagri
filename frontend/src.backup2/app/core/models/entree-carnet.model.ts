export type TypeEntree = 'ARROSAGE' | 'TRAITEMENT' | 'RECOLTE' | 'OBSERVATION' | 'AUTRE';

export interface EntreeCarnet {
  id: number;
  date: string;
  type: TypeEntree;
  description: string;
  photoUrl?: string;
  parcelleId: number;
}