import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { CalendrierService } from '../../core/services/calendrier.service';
import { CalendrierCultural } from '../../core/models/calendrier-cultural.model';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-calendrier',
  standalone: true,
  imports: [CommonModule, IonicModule,RouterLink],
  templateUrl: './calendrier.component.html',
  styleUrls: ['./calendrier.component.scss'],
})
export class CalendrierComponent implements OnInit {
  calendriers: CalendrierCultural[] = [];
  chargement = true;

  constructor(private calendrierService: CalendrierService) {}

  ngOnInit() {
    this.calendrierService.getCalendriers().subscribe(c => {
      this.calendriers = c;
      this.chargement = false;
    });
  }
}