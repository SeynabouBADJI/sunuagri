import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface WeatherResponse {
  current: {
    temperature_2m: number;
    relative_humidity_2m: number;
    precipitation: number;
    weather_code: number;
  };
}

@Injectable({
  providedIn: 'root'
})
export class WeatherService {

  private readonly API_URL = 'https://api.open-meteo.com/v1/forecast';

  constructor(private http: HttpClient) {}

  getWeather(latitude: number, longitude: number): Observable<WeatherResponse> {

    const url =
      `${this.API_URL}?latitude=${latitude}` +
      `&longitude=${longitude}` +
      `&current=temperature_2m,relative_humidity_2m,precipitation,weather_code` +
      `&timezone=Africa%2FDakar`;

    return this.http.get<WeatherResponse>(url);
  }
}