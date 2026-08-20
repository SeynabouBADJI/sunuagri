import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Utilisateur } from '../models/utilisateur.model';

interface LoginPayload {
  email: string;
  motDePasse: string;
}

interface RegisterPayload {
  nom: string;
  prenom: string;
  email: string;
  motDePasse: string;
  telephone: string;
  localisation?: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {

  private apiUrl = `${environment.apiUrl}/auth`;

  private utilisateurActuel: Utilisateur | null = null;

  constructor(private http: HttpClient) {
    // Restaurer l'utilisateur après un rafraîchissement de la page
    const utilisateur = localStorage.getItem('utilisateur');

    if (utilisateur) {
      this.utilisateurActuel = JSON.parse(utilisateur);
    }
  }

  login(email: string, motDePasse: string): Observable<Utilisateur> {

    const payload: LoginPayload = {
      email,
      motDePasse
    };

    return this.http
      .post<Utilisateur>(`${this.apiUrl}/login`, payload)
      .pipe(
        tap(utilisateur => {

          this.utilisateurActuel = utilisateur;

          localStorage.setItem(
            'utilisateur',
            JSON.stringify(utilisateur)
          );
        })
      );
  }

  register(
    nom: string,
    prenom: string,
    email: string,
    motDePasse: string,
    telephone: string,
    localisation?: string
  ): Observable<Utilisateur> {

    const payload: RegisterPayload = {
      nom,
      prenom,
      email,
      motDePasse,
      telephone,
      localisation
    };

    return this.http
      .post<Utilisateur>(`${this.apiUrl}/register`, payload)
      .pipe(
        tap(utilisateur => {

          this.utilisateurActuel = utilisateur;

          localStorage.setItem(
            'utilisateur',
            JSON.stringify(utilisateur)
          );
        })
      );
  }

  logout(): void {

    this.utilisateurActuel = null;

    localStorage.removeItem('utilisateur');
  }

  estConnecte(): boolean {
    return this.utilisateurActuel !== null;
  }

  utilisateurCourant(): Utilisateur | null {
    return this.utilisateurActuel;
  }
}