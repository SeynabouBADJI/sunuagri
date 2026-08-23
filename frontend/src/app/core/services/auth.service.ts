import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

export interface Utilisateur {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  localisation?: string;
  role?: string;
  dateCreation?: string;
}

export interface LoginRequest {
  email: string;
  motDePasse: string;
}

export interface RegisterRequest {
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  motDePasse: string;
  localisation: string;
}

export interface AuthResponse {
  token: string;
  utilisateur: Utilisateur;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly API_URL = 'http://localhost:8080/api/auth';

  private utilisateur: Utilisateur | null = null;

  constructor(private http: HttpClient) {
    this.restaurerSession();
  }

  // ============================
  // CONNEXION
  // ============================

  login(email: string, motDePasse: string): Observable<AuthResponse> {

    const donnees: LoginRequest = {
      email,
      motDePasse
    };

    return this.http
      .post<AuthResponse>(`${this.API_URL}/login`, donnees)
      .pipe(
        tap((response) => {

          console.log('Connexion réussie :', response);

          localStorage.setItem(
            'sunuagri_token',
            response.token
          );

          localStorage.setItem(
            'sunuagri_utilisateur',
            JSON.stringify(response.utilisateur)
          );

          this.utilisateur = response.utilisateur;
        })
      );
  }

  // ============================
  // INSCRIPTION
  // ============================

  register(
    nom: string,
    prenom: string,
    email: string,
    telephone: string,
    motDePasse: string,
    localisation: string
  ): Observable<Utilisateur> {

    const donnees: RegisterRequest = {
      nom,
      prenom,
      email,
      telephone,
      motDePasse,
      localisation
    };

    return this.http.post<Utilisateur>(
      `${this.API_URL}/register`,
      donnees
    );
  }

  // ============================
  // UTILISATEUR CONNECTÉ
  // ============================

  utilisateurCourant(): Utilisateur | null {
    return this.utilisateur;
  }

  // ============================
  // TOKEN
  // ============================

  getToken(): string | null {
    return localStorage.getItem('sunuagri_token');
  }

  
  restaurerSession(): void {

  const token = localStorage.getItem('sunuagri_token');
  const utilisateur = localStorage.getItem('sunuagri_utilisateur');

  if (token && utilisateur) {

    try {
      this.utilisateur = JSON.parse(utilisateur);
    } catch {
      this.logout();
    }

  } else {

    this.utilisateur = null;
  }
}

  // ============================
  // VÉRIFIER CONNEXION
  // ============================

  estConnecte(): boolean {
    return !!this.getToken() && !!this.utilisateur;
  }

  // ============================
  // DÉCONNEXION
  // ============================

  logout(): void {

    localStorage.removeItem('sunuagri_token');
    localStorage.removeItem('sunuagri_utilisateur');

    this.utilisateur = null;

    console.log('Utilisateur déconnecté');
  }
}