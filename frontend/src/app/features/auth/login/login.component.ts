import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { Router } from '@angular/router';

import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ CommonModule, FormsModule, IonicModule ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  email = '';
  motDePasse = '';

  erreur = '';
  chargement = false;

  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  seConnecter(): void {

    this.erreur = '';

    if (!this.email.trim() || !this.motDePasse.trim()) {

      this.erreur =
        'Veuillez renseigner votre email et votre mot de passe.';

      return;
    }

    this.chargement = true;
    this.auth.login(this.email, this.motDePasse).subscribe({
      next: (response) => {

        console.log('Connexion réussie :', response);
        console.log('Utilisateur connecté :', response.utilisateur);

        this.router.navigate(['/tabs/parcelles']);
      },

      error: (err) => {
        console.error('Erreur connexion :', err);
        this.erreur = 'Email ou mot de passe incorrect.';
      }
    });
  }
}