import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { Camera, CameraResultType, CameraSource } from '@capacitor/camera';
import { RouterLink } from '@angular/router';

import { DiagnosticService } from '../../core/services/diagnostic.service';
import { AuthService } from '../../core/services/auth.service';
import { DiagnosticAnalyseResponse } from '../../core/models/diagnostic-analyse.model';

@Component({
  selector: 'app-diagnostic',
  standalone: true,
  imports: [CommonModule, IonicModule, RouterLink],
  templateUrl: './diagnostic.component.html',
  styleUrls: ['./diagnostic.component.scss'],
})
export class DiagnosticComponent implements OnInit {

  selectedImage: string | null = null;
  selectedFileName = '';
  isAnalyzing = false;
  diagnosticResult: DiagnosticAnalyseResponse | null = null;
  recentDiagnostics: DiagnosticAnalyseResponse[] = [];
  erreur = '';

  constructor(
    private diagnosticService: DiagnosticService,
    private auth: AuthService
  ) {}

  ngOnInit() {
    // TODO : charger l'historique depuis le backend
    this.recentDiagnostics = [];
  }

  // ==================== CAMERA ====================

  async takePhoto() {
    await this.ouvrirCamera(CameraSource.Camera);
  }

  async selectFromGallery() {
    await this.ouvrirCamera(CameraSource.Photos);
  }

  private async ouvrirCamera(source: CameraSource) {
    try {
      const photo = await Camera.getPhoto({
        quality: 80,
        resultType: CameraResultType.DataUrl,
        source
      });
      this.selectedImage = photo.dataUrl ?? null;
      this.selectedFileName = `photo-${Date.now()}.jpg`;
      this.diagnosticResult = null;
      this.erreur = '';
    } catch (e) {
      console.warn('Camera indisponible', e);
      this.selectedImage = 'assets/mock/feuille-demo.jpg';
      this.selectedFileName = 'feuille-demo.jpg';
      this.diagnosticResult = null;
    }
  }

  removeImage() {
    this.selectedImage = null;
    this.selectedFileName = '';
    this.diagnosticResult = null;
    this.erreur = '';
  }

  // ==================== ANALYSE ====================

  analyzeImage() {
    if (!this.selectedImage) return;

    const utilisateur = this.auth.utilisateurCourant();
    const utilisateurId = utilisateur?.id ?? 1;

    this.isAnalyzing = true;
    this.erreur = '';
    this.diagnosticResult = null;

    this.diagnosticService.analyserImage(this.selectedImage, utilisateurId).subscribe({
      next: (res) => {
        this.diagnosticResult = res;
        this.isAnalyzing = false;
        this.recentDiagnostics = [res, ...this.recentDiagnostics].slice(0, 3);
      },
      error: (err) => {
        console.error('Erreur analyse IA', err);
        this.isAnalyzing = false;
        this.erreur = 'Impossible d\'analyser l\'image. Vérifiez votre connexion au serveur.';
      }
    });
  }

  newDiagnostic() {
    this.selectedImage = null;
    this.selectedFileName = '';
    this.diagnosticResult = null;
    this.erreur = '';
  }
}