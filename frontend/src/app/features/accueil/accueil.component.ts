import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { Router, RouterLink } from '@angular/router';
import { Geolocation } from '@capacitor/geolocation';

import { AuthService } from '../../core/services/auth.service';
import { WeatherService } from '../../core/services/weather.service';

@Component({
  selector: 'app-accueil',
  standalone: true,
  imports: [
    CommonModule,
    IonicModule,
    RouterLink
  ],
  templateUrl: './accueil.component.html',
  styleUrls: ['./accueil.component.scss']
})
export class AccueilComponent implements OnInit {

  // ====== MÉTÉO ======
  temperature = 0;
  humidity = 0;
  precipitation = 0;

  weatherIcon = 'partly-sunny-outline';
  weatherMessage = 'Chargement de la météo...';
  weatherLoading = true;
  weatherError = false;

  // Position courante (utilisée pour la météo)
  latitude: number | null = null;
  longitude: number | null = null;
  positionLabel = 'Localisation...';

  // Coordonnées de Dakar (fallback si la géoloc échoue)
  private readonly DAKAR_LAT = 14.7167;
  private readonly DAKAR_LON = -17.4677;

  constructor(
    public auth: AuthService,
    private router: Router,
    private weatherService: WeatherService
  ) {}

  ngOnInit(): void {
    this.initMeteo();
  }

  // =========================================================
  // CHARGEMENT DE LA MÉTÉO AVEC GÉOLOCALISATION
  // =========================================================

  private async initMeteo(): Promise<void> {
    try {
      // 1. Demander la permission
      const perm = await Geolocation.checkPermissions();

      let status = perm.location;
      if (status !== 'granted') {
        const req = await Geolocation.requestPermissions();
        status = req.location;
      }

      // 2. Récupérer la position si autorisée
      if (status === 'granted') {
        const pos = await Geolocation.getCurrentPosition({
          enableHighAccuracy: false,
          timeout: 8000,
          maximumAge: 60000
        });

        this.latitude = pos.coords.latitude;
        this.longitude = pos.coords.longitude;
        this.positionLabel = 'Autour de vous';
      } else {
        // Refus → fallback Dakar
        this.latitude = this.DAKAR_LAT;
        this.longitude = this.DAKAR_LON;
        this.positionLabel = 'Dakar (position par défaut)';
      }
    } catch (err) {
      // Erreur de géoloc (timeout, GPS off, etc.) → fallback Dakar
      console.warn('Géolocalisation indisponible, fallback Dakar', err);
      this.latitude = this.DAKAR_LAT;
      this.longitude = this.DAKAR_LON;
      this.positionLabel = 'Dakar (position par défaut)';
    }

    // 3. Charger la météo avec la position obtenue
    this.chargerMeteo();
  }

  chargerMeteo(): void {
    if (this.latitude === null || this.longitude === null) {
      return;
    }

    this.weatherLoading = true;
    this.weatherError = false;

    this.weatherService
      .getWeather(this.latitude, this.longitude)
      .subscribe({
        next: (data) => {
          // ⚠️ Double vérification : la réponse peut être malformée
          const current = data?.current;

          if (!current) {
            console.warn('Réponse météo vide ou malformée', data);
            this.weatherError = true;
            this.weatherMessage = 'Météo momentanément indisponible.';
            this.weatherLoading = false;
            return;
          }

          this.temperature = Math.round(current.temperature_2m ?? 0);
          this.humidity = Math.round(current.relative_humidity_2m ?? 0);
          this.precipitation = current.precipitation ?? 0;

          this.weatherIcon = this.getWeatherIcon(current.weather_code);
          this.weatherMessage = this.getWeatherMessage(current.weather_code);

          this.weatherLoading = false;
        },

        error: (error) => {
          console.error('Erreur météo :', error);
          this.weatherError = true;
          this.weatherMessage = 'Météo momentanément indisponible.';
          this.weatherLoading = false;
        }
      });
  }

  // =========================================================
  // HELPERS MÉTÉO
  // =========================================================

  getWeatherIcon(code: number): string {
    if (code === 0) return 'sunny-outline';
    if (code >= 1 && code <= 3) return 'partly-sunny-outline';
    if (code >= 45 && code <= 48) return 'cloud-outline';
    if (code >= 51 && code <= 67) return 'rainy-outline';
    if (code >= 71 && code <= 77) return 'snow-outline';
    if (code >= 80 && code <= 82) return 'rainy-outline';
    if (code >= 95) return 'thunderstorm-outline';
    return 'partly-sunny-outline';
  }

  getWeatherMessage(code: number): string {
    if (code === 0) return 'Ciel dégagé.';
    if (code >= 1 && code <= 3) return 'Conditions globalement favorables.';
    if (code >= 45 && code <= 48) return 'Visibilité réduite.';
    if (code >= 51 && code <= 67) return 'Précipitations en cours.';
    if (code >= 80 && code <= 82) return 'Averses possibles.';
    if (code >= 95) return 'Risque d’orage.';
    return 'Conditions météorologiques à surveiller.';
  }

  // =========================================================
  // ACTIONS
  // =========================================================

  seDeconnecter(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

  get utilisateur() {
    return this.auth.utilisateurCourant();
  }
}