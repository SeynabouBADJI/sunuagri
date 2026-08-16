import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { Camera, CameraResultType, CameraSource } from '@capacitor/camera';
import { DiagnosticService } from '../../core/services/diagnostic.service';
import { MockDataService } from '../../core/services/mock-data.service';
import { Diagnostic } from '../../core/models/diagnostic.model';
import { Maladie } from '../../core/models/maladie.model';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-diagnostic',
  standalone: true,
  imports: [CommonModule, IonicModule,RouterLink],
  templateUrl: './diagnostic.component.html',
  styleUrls: ['./diagnostic.component.scss'],
})
export class DiagnosticComponent implements OnInit {
  selectedImage: string | null = null;
  selectedFileName = '';
  isAnalyzing = false;
  diagnosticResult: Diagnostic | null = null;
  recentDiagnostics: Diagnostic[] = [];

  private planteParDefautId: number;

  constructor(private diagnosticService: DiagnosticService, private mock: MockDataService) {
    this.planteParDefautId = this.mock.plantes[0].id;
  }

  ngOnInit() {
    this.diagnosticService.getHistorique().subscribe(h => (this.recentDiagnostics = h.slice(0, 3)));
  }

  async takePhoto() {
    await this.ouvrirCamera(CameraSource.Camera);
  }

  async selectFromGallery() {
    await this.ouvrirCamera(CameraSource.Photos);
  }

  private async ouvrirCamera(source: CameraSource) {
    try {
      const photo = await Camera.getPhoto({ quality: 80, resultType: CameraResultType.DataUrl, source });
      this.selectedImage = photo.dataUrl ?? null;
      this.selectedFileName = `photo-${Date.now()}.jpg`;
      this.diagnosticResult = null;
    } catch (e) {
      console.warn('Camera indisponible, utilisation d\'une image de demonstration', e);
      this.selectedImage = 'assets/mock/feuille-demo.jpg';
      this.selectedFileName = 'feuille-demo.jpg';
      this.diagnosticResult = null;
    }
  }

  removeImage() {
    this.selectedImage = null;
    this.selectedFileName = '';
    this.diagnosticResult = null;
  }

  analyzeImage() {
    if (!this.selectedImage) return;
    this.isAnalyzing = true;
    this.diagnosticService.analyserImage(this.selectedImage, this.planteParDefautId).subscribe(res => {
      this.diagnosticResult = res;
      this.isAnalyzing = false;
      this.recentDiagnostics = [res, ...this.recentDiagnostics].slice(0, 3);
    });
  }

  newDiagnostic() {
    this.selectedImage = null;
    this.selectedFileName = '';
    this.diagnosticResult = null;
  }

  getMaladie(maladieId: number | null): Maladie | undefined {
    if (maladieId === null) return undefined;
    return this.mock.maladies.find(m => m.id === maladieId);
  }
}