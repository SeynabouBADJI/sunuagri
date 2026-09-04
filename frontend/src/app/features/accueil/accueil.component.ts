import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { Router, RouterLink } from '@angular/router';

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

  temperature = 0;
  humidity = 0;
  precipitation = 0;

  weatherIcon = 'partly-sunny-outline';
  weatherMessage = 'Chargement de la météo...';

  constructor(
    public auth: AuthService,
    private router: Router,
    private weatherService: WeatherService
  ) {}

  ngOnInit(): void {
    this.chargerMeteo();
  }

  chargerMeteo(): void {

    // Coordonnées de Dakar
    const latitude = 14.7167;
    const longitude = -17.4677;

    this.weatherService
      .getWeather(latitude, longitude)
      .subscribe({
        next: (data) => {

          this.temperature = Math.round(
            data.current.temperature_2m
          );

          this.humidity = Math.round(
            data.current.relative_humidity_2m
          );

          this.precipitation =
            data.current.precipitation;

          this.weatherIcon =
            this.getWeatherIcon(
              data.current.weather_code
            );

          this.weatherMessage =
            this.getWeatherMessage(
              data.current.weather_code
            );
        },

        error: (error) => {

          console.error(
            'Erreur météo :',
            error
          );

          this.weatherMessage =
            'Météo momentanément indisponible.';
        }
      });
  }

  getWeatherIcon(code: number): string {

    if (code === 0) {
      return 'sunny-outline';
    }

    if (code >= 1 && code <= 3) {
      return 'partly-sunny-outline';
    }

    if (code >= 45 && code <= 48) {
      return 'cloud-outline';
    }

    if (code >= 51 && code <= 67) {
      return 'rainy-outline';
    }

    if (code >= 71 && code <= 77) {
      return 'snow-outline';
    }

    if (code >= 80 && code <= 82) {
      return 'rainy-outline';
    }

    if (code >= 95) {
      return 'thunderstorm-outline';
    }

    return 'partly-sunny-outline';
  }

  getWeatherMessage(code: number): string {

    if (code === 0) {
      return 'Ciel dégagé.';
    }

    if (code >= 1 && code <= 3) {
      return 'Conditions globalement favorables.';
    }

    if (code >= 45 && code <= 48) {
      return 'Visibilité réduite.';
    }

    if (code >= 51 && code <= 67) {
      return 'Précipitations en cours.';
    }

    if (code >= 80 && code <= 82) {
      return 'Averses possibles.';
    }

    if (code >= 95) {
      return 'Risque d’orage.';
    }

    return 'Conditions météorologiques à surveiller.';
  }

  seDeconnecter(): void {

    this.auth.logout();

    this.router.navigate(['/login']);
  }

  get utilisateur() {
    return this.auth.utilisateurCourant();
  }
}