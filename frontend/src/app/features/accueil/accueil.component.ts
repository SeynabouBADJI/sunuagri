import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { Router, RouterLink } from '@angular/router';

import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-accueil',
  standalone: true,

  imports: [
    CommonModule,
    IonicModule,
    RouterLink
  ],

  templateUrl: './accueil.component.html',
  styleUrls: ['./accueil.component.scss']
})
export class AccueilComponent {

  constructor(
    public auth: AuthService,
    private router: Router
  ) {}

  seDeconnecter(): void {

    this.auth.logout();

    this.router.navigate(['/login']);
  }

  get utilisateur() {
    return this.auth.utilisateurCourant();
  }
}