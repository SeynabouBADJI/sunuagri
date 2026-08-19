import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { RouterLink } from '@angular/router';
import { ParcelleService } from '../../core/services/parcelle.service';
import { MockDataService } from '../../core/services/mock-data.service';
import { Parcelle } from '../../core/models/parcelle.model';
import { EntreeCarnet, TypeEntree } from '../../core/models/entree-carnet.model';

@Component({
  selector: 'app-parcelles',
  standalone: true,
  imports: [CommonModule, FormsModule, IonicModule, RouterLink],
  templateUrl: './parcelles.component.html',
  styleUrls: ['./parcelles.component.scss'],
})
export class ParcellesComponent implements OnInit {
  parcelles: Parcelle[] = [];
  chargement = true;

  totalSuperficie = 0;
  totalParcelles = 0;

  // --- Modale carnet ---
  modalOuvert = false;
  parcelleSelectionnee: Parcelle | null = null;
  entreesCarnet: EntreeCarnet[] = [];

  nouveauType: TypeEntree = 'OBSERVATION';
  nouvelleDescription = '';

  constructor(private parcelleService: ParcelleService, private mock: MockDataService) {}

  ngOnInit() {
    this.parcelleService.getParcelles().subscribe(parcelles => {
      this.parcelles = parcelles;
      this.totalParcelles = parcelles.length;
      this.totalSuperficie = parcelles.reduce((somme, p) => somme + p.superficie, 0);
      this.chargement = false;
    });
  }

  culturesActuelles(parcelleId: number): string {
    const plantations = this.mock.plantations.filter(p => p.parcelleId === parcelleId && !p.dateFin);
    if (plantations.length === 0) return 'Aucune culture en cours';
    const noms = plantations.map(p => this.mock.plantes.find(pl => pl.id === p.planteId)?.nomCommun ?? '?');
    return noms.join(', ');
  }

  nombreEntreesCarnet(parcelleId: number): number {
    return this.mock.entreesCarnet.filter(e => e.parcelleId === parcelleId).length;
  }

  ouvrirCarnet(parcelle: Parcelle) {
    this.parcelleSelectionnee = parcelle;
    this.parcelleService.getEntreesCarnet(parcelle.id).subscribe(entrees => {
      this.entreesCarnet = entrees;
    });
    this.modalOuvert = true;
  }

  fermerModal() {
    this.modalOuvert = false;
    this.parcelleSelectionnee = null;
    this.annulerEdition();
  }

  validerEntree() {
    if (!this.parcelleSelectionnee || !this.nouvelleDescription.trim()) return;

    if (this.entreeEnEdition) {
      this.parcelleService.modifierEntreeCarnet(this.entreeEnEdition.id, this.nouveauType, this.nouvelleDescription.trim())
        .subscribe(() => {
          const index = this.entreesCarnet.findIndex(e => e.id === this.entreeEnEdition!.id);
          if (index !== -1) {
            this.entreesCarnet[index] = { ...this.entreesCarnet[index], type: this.nouveauType, description: this.nouvelleDescription.trim() };
          }
          this.annulerEdition();
        });
    } else {
      this.parcelleService.ajouterEntreeCarnet({
        date: new Date().toISOString().slice(0, 10),
        type: this.nouveauType,
        description: this.nouvelleDescription.trim(),
        parcelleId: this.parcelleSelectionnee.id,
      }).subscribe(nouvelle => {
        this.entreesCarnet = [nouvelle, ...this.entreesCarnet];
        this.annulerEdition();
      });
    }
  }

  modifierEntree(entree: EntreeCarnet) {
    this.entreeEnEdition = entree;
    this.nouveauType = entree.type;
    this.nouvelleDescription = entree.description;
  }

  annulerEdition() {
    this.entreeEnEdition = null;
    this.nouvelleDescription = '';
    this.nouveauType = 'OBSERVATION';
  }

  supprimerEntree(entree: EntreeCarnet) {
    this.parcelleService.supprimerEntreeCarnet(entree.id).subscribe(() => {
      this.entreesCarnet = this.entreesCarnet.filter(e => e.id !== entree.id);
      if (this.entreeEnEdition?.id === entree.id) this.annulerEdition();
    });
  }

  entreeEnEdition: EntreeCarnet | null = null;
}