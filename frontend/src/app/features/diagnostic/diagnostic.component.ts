import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { Camera, CameraResultType, CameraSource } from '@capacitor/camera';
import { DiagnosticService } from '../../core/services/diagnostic.service';
import { MockDataService } from '../../core/services/mock-data.service';
import { Diagnostic } from '../../core/models/diagnostic.model';
import { Plante } from '../../core/models/plante.model';

@Component({
  selector: 'app-diagnostic',
  standalone: true,
  imports: [CommonModule, FormsModule, IonicModule],
  templateUrl: './diagnostic.component.html',
  styleUrls: ['./diagnostic.component.scss'],
})
export class DiagnosticComponent implements OnInit {
  plantes: Plante[] = [];
  planteSelectionneeId!: number;

  photoDataUrl: string | null = null;
  analyseEnCours = false;
  resultat: Diagnostic | null = null;

  historique: Diagnostic[] = [];

  constructor(private diagnosticService: DiagnosticService, private mock: MockDataService) {
    this.plantes = this.mock.plantes;
    this.planteSelectionneeId = this.plantes[0].id;
  }

  ngOnInit() {
    this.diagnosticService.getHistorique().subscribe(h => (this.historique = h.slice(0, 3)));
  }

  selectionnerPlante(id: number) {
    this.planteSelectionneeId = id;
  }

  async prendrePhoto() {
    try {
      const photo = await Camera.getPhoto({
        quality: 80,
        resultType: CameraResultType.DataUrl,
        source: CameraSource.Prompt,
      });
      this.photoDataUrl = photo.dataUrl ?? null;
      this.resultat = null;
    } catch (e) {
      console.warn('Photo non disponible, utilisation d\'une image de demonstration', e);
      this.photoDataUrl = 'assets/mock/feuille-demo.jpg';
      this.resultat = null;
    }
  }

  analyser() {
    if (!this.photoDataUrl) return;
    this.analyseEnCours = true;
    this.diagnosticService.analyserImage(this.photoDataUrl, this.planteSelectionneeId).subscribe(res => {
      this.resultat = res;
      this.analyseEnCours = false;
      this.historique = [res, ...this.historique].slice(0, 3);
    });
  }

  reinitialiser() {
    this.photoDataUrl = null;
    this.resultat = null;
  }

  niveauConfiance(): number {
    return this.resultat ? Math.round(this.resultat.confiance * 100) : 0;
  }

  planteSelectionnee(): Plante | undefined {
    return this.plantes.find(p => p.id === this.planteSelectionneeId);
  }
}