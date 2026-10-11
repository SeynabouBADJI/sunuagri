export interface Diagnostic {
  id: number;
  dateDiagnostic: string;
  image: string;
  confiance: number; // entre 0 et 1
  utilisateurId: number;
  planteId: number;
  maladieId: number | null;
  // champs derives, pratiques cote UI
  maladieNom?: string;
  planteNom?: string;
}