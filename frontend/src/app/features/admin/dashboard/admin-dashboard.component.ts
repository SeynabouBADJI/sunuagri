
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import {
  AdminStatistiques,
  AdminStatistiquesService
} from '../../../core/services/admin-statistiques.service';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss']
})
export class AdminDashboardComponent implements OnInit {

  statistiques: AdminStatistiques | null = null;
  chargement = true;
  erreur = '';

  constructor(
    private router: Router,
    private statistiquesService: AdminStatistiquesService
  ) {}

  ngOnInit(): void {
    this.chargerStatistiques();
  }

  chargerStatistiques(): void {
    this.chargement = true;
    this.erreur = '';

    this.statistiquesService.obtenirStatistiques().subscribe({
      next: (data) => {
        this.statistiques = data;
        this.chargement = false;
      },
      error: (error) => {
        console.error('Erreur statistiques :', error);
        this.erreur =
          'Impossible de charger les statistiques. Vérifiez votre connexion.';
        this.chargement = false;
      }
    });
  }

  deconnexion(): void {
    localStorage.removeItem('sunuagri_token');
    localStorage.removeItem('sunuagri_utilisateur');
    this.router.navigate(['/login']);
  }
}