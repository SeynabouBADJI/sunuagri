import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterLink } from '@angular/router';

import { CalendrierService } from '../../core/services/calendrier.service';
import { CalendrierCultural } from '../../core/models/calendrier-cultural.model';

@Component({
  selector: 'app-calendrier',
  standalone: true,
  imports: [
    CommonModule,
    IonicModule,
    RouterLink
  ],
  templateUrl: './calendrier.component.html',
  styleUrls: ['./calendrier.component.scss']
})
export class CalendrierComponent implements OnInit {

  // ====== DONNÉES ======
  calendriers: CalendrierCultural[] = [];
  chargement = true;
  erreur = '';

  // ====== ACCORDÉON ======
  indexOuvert: number | null = null;

  // ====== FILTRES ======
  zoneSelectionnee: string | null = null;
  cultureSelectionnee: string | null = null;

  constructor(
    private calendrierService: CalendrierService
  ) {}

  ngOnInit(): void {
    this.chargerCalendriers();
  }

  // ====== CHARGEMENT ======

  chargerCalendriers(): void {
    this.chargement = true;
    this.erreur = '';

    this.calendrierService.getCalendriers().subscribe({
      next: (data) => {
        this.calendriers = data;
        this.chargement = false;
      },

      error: (error) => {
        console.error(
          'Erreur lors du chargement des calendriers :',
          error
        );

        this.erreur =
          'Impossible de charger les calendriers culturaux.';

        this.chargement = false;
      }
    });
  }

  // ====== GETTERS — FILTRES ======

  /**
   * Liste unique des zones agricoles (extraite des calendriers chargés)
   */
  get zonesDisponibles(): string[] {
    return [...new Set(this.calendriers.map(c => c.zoneAgricole))].sort();
  }

  /**
   * Liste des cultures disponibles.
   * - Si une zone est sélectionnée → cultures de cette zone uniquement
   * - Sinon → toutes les cultures
   */
  get culturesDisponibles(): string[] {
    const source = this.zoneSelectionnee
      ? this.calendriers.filter(c => c.zoneAgricole === this.zoneSelectionnee)
      : this.calendriers;

    return [...new Set(source.map(c => c.nomPlante))].sort();
  }

  /**
   * Calendriers filtrés (ceux affichés réellement)
   */
  get calendriersFiltres(): CalendrierCultural[] {
    return this.calendriers.filter(c => {
      const matchZone =
        !this.zoneSelectionnee || c.zoneAgricole === this.zoneSelectionnee;

      const matchCulture =
        !this.cultureSelectionnee || c.nomPlante === this.cultureSelectionnee;

      return matchZone && matchCulture;
    });
  }

  /**
   * Y a-t-il au moins un filtre actif ?
   */
  get filtreActif(): boolean {
    return this.zoneSelectionnee !== null || this.cultureSelectionnee !== null;
  }

  // ====== ACTIONS — FILTRES ======

  /**
   * Sélectionne / désélectionne une zone.
   * Si on change de zone → on reset la culture sélectionnée.
   */
  selectionnerZone(zone: string): void {
    if (this.zoneSelectionnee === zone) {
      this.zoneSelectionnee = null;
    } else {
      this.zoneSelectionnee = zone;
      this.cultureSelectionnee = null;  // reset culture
    }

    this.indexOuvert = null;  // referme les cards
  }

  /**
   * Sélectionne / désélectionne une culture.
   */
  selectionnerCulture(culture: string): void {
    this.cultureSelectionnee =
      this.cultureSelectionnee === culture ? null : culture;

    this.indexOuvert = null;
  }

  /**
   * Réinitialise tous les filtres.
   */
  reinitialiserFiltres(): void {
    this.zoneSelectionnee = null;
    this.cultureSelectionnee = null;
    this.indexOuvert = null;
  }

  // ====== ACTIONS — ACCORDÉON ======

  toggleCard(index: number): void {
    this.indexOuvert = this.indexOuvert === index ? null : index;
  }

  toutBasculer(): void {
    // Si tout est fermé → ouvre la première, sinon ferme tout
    this.indexOuvert = this.indexOuvert === null ? 0 : null;
  }
}