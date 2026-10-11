import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Parcelle } from '../models/parcelle.model';

@Injectable({
  providedIn: 'root'
})
export class ParcelleService {

  private apiUrl = `${environment.apiUrl}/parcelles`;

  constructor(private http: HttpClient) {}

  // Récupérer toutes les parcelles d'un utilisateur
  getParcellesParUtilisateur(utilisateurId: number): Observable<Parcelle[]> {
    return this.http.get<Parcelle[]>(
      `${this.apiUrl}/utilisateur/${utilisateurId}`
    );
  }

  // Récupérer une parcelle
  getParcelle(id: number): Observable<Parcelle> {
    return this.http.get<Parcelle>(
      `${this.apiUrl}/${id}`
    );
  }

  // Ajouter une parcelle
  ajouterParcelle(parcelle: Parcelle): Observable<Parcelle> {
    return this.http.post<Parcelle>(
      this.apiUrl,
      parcelle
    );
  }

  // Modifier une parcelle
  modifierParcelle(id: number, parcelle: Parcelle): Observable<Parcelle> {
    return this.http.put<Parcelle>(
      `${this.apiUrl}/${id}`,
      parcelle
    );
  }

  // Supprimer une parcelle
  supprimerParcelle(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );
  }
}