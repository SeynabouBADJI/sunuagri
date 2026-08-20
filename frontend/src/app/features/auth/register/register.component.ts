import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, IonicModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  nom = '';
  prenom = '';
  email = '';
  telephone = '';
  motDePasse = '';
  confirmationMotDePasse = '';
  chargement = false;
  erreur = '';

  constructor(private auth: AuthService, private router: Router) {}

  sInscrire() {
    this.erreur = '';

    if (this.motDePasse !== this.confirmationMotDePasse) {
      this.erreur = 'Les mots de passe ne correspondent pas.';
      return;
    }

    this.chargement = true;
    this.auth.register(this.nom, this.prenom, this.email, this.motDePasse, this.telephone).subscribe({
      next: () => {
        this.chargement = false;
        this.router.navigateByUrl('/tabs/diagnostic');
      },
      error: (err) => {
        this.chargement = false;
        this.erreur = err.status === 400
          ? 'Verifie les champs du formulaire.'
          : 'Un compte existe peut-etre deja avec cet email.';
      },
    });
  }
}