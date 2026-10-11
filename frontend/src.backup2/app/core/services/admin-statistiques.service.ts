
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AdminStatistiques {
  totalUtilisateurs: number;
  totalAgriculteurs: number;
  totalAdministrateurs: number;
  totalPlantes: number;
  totalCalendriers: number;
}

@Injectable({
  providedIn: 'root'
})
export class AdminStatistiquesService {

  private readonly API_URL =
    'http://localhost:8080/api/admin/statistiques';

  constructor(private http: HttpClient) {}

  obtenirStatistiques(): Observable<AdminStatistiques> {
    return this.http.get<AdminStatistiques>(this.API_URL);
  }
}