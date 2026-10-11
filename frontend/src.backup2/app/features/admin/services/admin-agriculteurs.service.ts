
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';

export interface Agriculteur {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  localisation?: string;
  role: string;
  dateCreation?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AdminAgriculteursService {

  private readonly API_URL =
    'http://localhost:8080/api/utilisateurs';

  constructor(private http: HttpClient) {}

  obtenirAgriculteurs(): Observable<Agriculteur[]> {
    return this.http.get<Agriculteur[]>(this.API_URL).pipe(
      map(utilisateurs =>
        utilisateurs.filter(
          utilisateur => utilisateur.role === 'AGRICULTEUR'
        )
      )
    );
  }
}