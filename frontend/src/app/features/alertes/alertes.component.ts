import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { AlerteService } from '../../core/services/alerte.service';
import { Alerte } from '../../core/models/alerte.model';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-alertes',
  standalone: true,
  imports: [CommonModule, IonicModule,RouterLink],
  templateUrl: './alertes.component.html',
  styleUrls: ['./alertes.component.scss'],
})
export class AlertesComponent implements OnInit {
  alertes: Alerte[] = [];
  chargement = true;

  constructor(private alerteService: AlerteService) {}

  ngOnInit() {
    this.alerteService.getAlertes().subscribe(a => {
      this.alertes = a;
      this.chargement = false;
    });
  }
}