export interface DiagnosticAnalyseResponse {
  diagnosticId: number;
  dateDiagnostic: string;

  // Résultat IA
  maladieCode: string;      // "Tomato___Late_blight"
  maladieNom: string;       // "Mildiou (tomate)"
  culture: string;          // "Tomate"
  saine: boolean;
  confiance: number;        // 99.96

  // Infos BDD
  maladieId?: number;
  symptomes?: string;
  traitement?: string;

  // Top 3
  top3?: Top3Item[];
}

export interface Top3Item {
  code: string;
  nomFr: string;
  confiance: number;
}