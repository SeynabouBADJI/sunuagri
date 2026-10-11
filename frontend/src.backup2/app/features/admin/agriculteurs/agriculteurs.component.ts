
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  AdminAgriculteursService,
  Agriculteur
} from '../services/admin-agriculteurs.service';

@Component({
  selector: 'app-admin-agriculteurs',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './agriculteurs.component.html',
  styleUrls: ['./agriculteurs.component.scss']
})
export class AgriculteursComponent implements OnInit {

  agriculteurs: Agriculteur[] = [];
  recherche = '';
  chargement = true;
  erreur = '';

  constructor(
    private agriculteursService: AdminAgriculteursService
  ) {}

  ngOnInit(): void {
    this.chargerAgriculteurs();
  }

  chargerAgriculteurs(): void {
    this.chargement = true;
    this.erreur = '';

    this.agriculteursService.obtenirAgriculteurs().subscribe({
      next: (data) => {
        this.agriculteurs = data;
        this.chargement = false;
      },
      error: (error) => {
        console.error('Erreur de chargement :', error);
        this.erreur =
          'Impossible de charger les agriculteurs. Vérifiez la connexion au serveur.';
        this.chargement = false;
      }
    });
  }

  get agriculteursFiltres(): Agriculteur[] {
    const terme = this.recherche.trim().toLowerCase();

    if (!terme) {
      return this.agriculteurs;
    }

    return this.agriculteurs.filter(a =>
      `${a.nom} ${a.prenom}`.toLowerCase().includes(terme) ||
      a.email.toLowerCase().includes(terme) ||
      (a.localisation ?? '').toLowerCase().includes(terme) ||
      a.telephone.includes(terme)
    );
  }
}