import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { Router, RouterLink } from '@angular/router';

import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,

  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterLink
  ],

  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  nom = '';
  prenom = '';
  email = '';
  telephone = '';
  localisation = '';

  motDePasse = '';
  confirmationMotDePasse = '';

  erreur = '';
  succes = '';

  chargement = false;

  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  sInscrire(): void {

    this.erreur = '';
    this.succes = '';

    // ============================
    // VALIDATIONS
    // ============================

    if (
      !this.nom.trim() ||
      !this.prenom.trim() ||
      !this.email.trim() ||
      !this.telephone.trim() ||
      !this.localisation.trim() ||
      !this.motDePasse.trim()
    ) {

      this.erreur =
        'Veuillez remplir tous les champs obligatoires.';

      return;
    }

    if (
      this.motDePasse !==
      this.confirmationMotDePasse
    ) {

      this.erreur =
        'Les mots de passe ne correspondent pas.';

      return;
    }

    if (this.motDePasse.length < 6) {

      this.erreur =
        'Le mot de passe doit contenir au moins 6 caractères.';

      return;
    }

    this.chargement = true;

    // ============================
    // INSCRIPTION
    // ============================

    this.auth
      .register(
        this.nom.trim(),
        this.prenom.trim(),
        this.email.trim(),
        this.telephone.trim(),
        this.motDePasse,
        this.localisation.trim()
      )
      .subscribe({

        next: (utilisateur) => {

          console.log(
            'Utilisateur créé :',
            utilisateur
          );

          this.chargement = false;

          this.succes =
            'Compte créé avec succès !';

          /*
           * Le backend register retourne uniquement
           * UtilisateurDTO.
           *
           * On redirige donc vers login.
           */

          setTimeout(() => {

            this.router.navigate(['/login']);

          }, 1200);
        },

        error: (err: any) => {

          console.error(
            'Erreur inscription :',
            err
          );

          this.chargement = false;

          if (err.status === 409) {

            this.erreur =
              'Un compte existe déjà avec cet email.';

          } else if (err.status === 400) {

            this.erreur =
              'Les informations fournies sont invalides.';

          } else {

            this.erreur =
              'Impossible de créer le compte.';
          }
        }

      });
  }
}