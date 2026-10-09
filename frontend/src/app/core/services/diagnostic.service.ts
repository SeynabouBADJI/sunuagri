import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DiagnosticAnalyseResponse } from '../models/diagnostic-analyse.model';

@Injectable({ providedIn: 'root' })
export class DiagnosticService {

  private apiUrl = 'http://localhost:8080/api';   // ⚠️ Voir note ci-dessous
  // Sur émulateur Android : 10.0.2.2 = localhost du PC
  // Sur téléphone physique : mets l'IP de ton PC (ex: 192.168.1.15)

  constructor(private http: HttpClient) {}

  /**
   * Envoie une image au backend pour analyse IA.
   */
  analyserImage(
    imageDataUrl: string,
    utilisateurId: number
  ): Observable<DiagnosticAnalyseResponse> {

    const blob = this.dataUrlToBlob(imageDataUrl);
    const formData = new FormData();
    formData.append('file', blob, 'photo.jpg');
    formData.append('utilisateurId', utilisateurId.toString());

    return this.http.post<DiagnosticAnalyseResponse>(
      `${this.apiUrl}/diagnostics/analyser`,
      formData
    );
  }

  /**
   * Récupère l'historique des diagnostics d'un utilisateur.
   */
  getHistoriqueUtilisateur(utilisateurId: number): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.apiUrl}/diagnostics/utilisateur/${utilisateurId}`
    );
  }

  /**
   * Convertit un dataUrl (base64) en Blob.
   */
  private dataUrlToBlob(dataUrl: string): Blob {
    const [header, base64] = dataUrl.split(',');
    const mime = header.match(/:(.*?);/)?.[1] || 'image/jpeg';
    const binary = atob(base64);
    const array = new Uint8Array(binary.length);
    for (let i = 0; i < binary.length; i++) {
      array[i] = binary.charCodeAt(i);
    }
    return new Blob([array], { type: mime });
  }
}