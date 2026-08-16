import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { ParcelleService } from '../../core/services/parcelle.service';
import { MockDataService } from '../../core/services/mock-data.service';
import { Parcelle } from '../../core/models/parcelle.model';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-parcelles',
  standalone: true,
  imports: [CommonModule, IonicModule,RouterLink],
  templateUrl: './parcelles.component.html',
  styleUrls: ['./parcelles.component.scss'],
})
export class ParcellesComponent implements OnInit {
  parcelles: Parcelle[] = [];
  chargement = true;

  totalSuperficie = 0;
  totalParcelles = 0;

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
}