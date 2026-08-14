import { Injectable } from '@angular/core';
import { Observable, of, delay } from 'rxjs';
import { MockDataService } from './mock-data.service';
import { Diagnostic } from '../models/diagnostic.model';

@Injectable({ providedIn: 'root' })
export class DiagnosticService {
  constructor(private mock: MockDataService) {}

  getHistorique(): Observable<Diagnostic[]> {
    return of(this.mock.diagnostics).pipe(delay(300));
  }

  analyserImage(imageDataUrl: string, planteId: number): Observable<Diagnostic> {
    const maladie = this.mock.maladies[Math.floor(Math.random() * this.mock.maladies.length)];
    const plante = this.mock.plantes.find(p => p.id === planteId);

    const resultat: Diagnostic = {
      id: this.mock.diagnostics.length + 1,
      dateDiagnostic: new Date().toISOString(),
      image: imageDataUrl,
      confiance: Math.round((0.7 + Math.random() * 0.29) * 100) / 100,
      utilisateurId: this.mock.utilisateurCourant.id,
      planteId,
      maladieId: maladie.id,
      maladieNom: maladie.nom,
      planteNom: plante?.nomCommun,
    };

    this.mock.diagnostics.unshift(resultat);
    return of(resultat).pipe(delay(1500));
  }
}