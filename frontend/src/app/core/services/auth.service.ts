import { Injectable } from '@angular/core';
import { Observable, of, delay } from 'rxjs';
import { MockDataService } from './mock-data.service';
import { Utilisateur } from '../models/utilisateur.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private connecte = false;

  constructor(private mock: MockDataService) {}

  login(email: string, motDePasse: string): Observable<Utilisateur> {
    this.connecte = true;
    return of(this.mock.utilisateurCourant).pipe(delay(600));
  }

  register(nom: string, prenom: string, email: string, motDePasse: string, telephone: string): Observable<Utilisateur> {
    const nouvelUtilisateur: Utilisateur = {
      id: Date.now(),
      nom,
      prenom,
      email,
      telephone,
      role: 'AGRICULTEUR',
    };
    this.mock.utilisateurCourant = nouvelUtilisateur;
    this.connecte = true;
    return of(nouvelUtilisateur).pipe(delay(700));
  }

  logout(): void {
    this.connecte = false;
  }

  estConnecte(): boolean {
    return this.connecte;
  }

  utilisateurCourant(): Utilisateur {
    return this.mock.utilisateurCourant;
  }
}