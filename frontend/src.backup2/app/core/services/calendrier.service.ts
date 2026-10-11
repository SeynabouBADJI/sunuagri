import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { CalendrierCultural } from '../models/calendrier-cultural.model';

@Injectable({
  providedIn: 'root'
})
export class CalendrierService {

  private readonly API_URL =
    'http://localhost:8080/api/calendrier-cultural';

  constructor(private http: HttpClient) {}

  // Récupérer tous les calendriers
  getCalendriers(): Observable<CalendrierCultural[]> {
    return this.http.get<CalendrierCultural[]>(this.API_URL);
  }

  // Récupérer un calendrier par son ID
  getCalendrierById(id: number): Observable<CalendrierCultural> {
    return this.http.get<CalendrierCultural>(
      `${this.API_URL}/${id}`
    );
  }

  // Récupérer les calendriers d'une plante
  getCalendriersByPlante(
    planteId: number
  ): Observable<CalendrierCultural[]> {
    return this.http.get<CalendrierCultural[]>(
      `${this.API_URL}/plante/${planteId}`
    );
  }

  // Récupérer les calendriers d'une zone
  getCalendriersByZone(
    zoneAgricole: string
  ): Observable<CalendrierCultural[]> {
    return this.http.get<CalendrierCultural[]>(
      `${this.API_URL}/zone/${encodeURIComponent(zoneAgricole)}`
    );
  }
}