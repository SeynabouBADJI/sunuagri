import { Injectable } from '@angular/core';
import { Observable, of, delay } from 'rxjs';
import { MockDataService } from './mock-data.service';
import { Parcelle } from '../models/parcelle.model';
import { EntreeCarnet } from '../models/entree-carnet.model';
import { Plantation } from '../models/plantation.model';

@Injectable({ providedIn: 'root' })
export class ParcelleService {
  constructor(private mock: MockDataService) {}

  getParcelles(): Observable<Parcelle[]> {
    return of(this.mock.parcelles).pipe(delay(300));
  }

  getParcelle(id: number): Observable<Parcelle | undefined> {
    return of(this.mock.parcelles.find(p => p.id === id)).pipe(delay(200));
  }

  getEntreesCarnet(parcelleId: number): Observable<EntreeCarnet[]> {
    return of(this.mock.entreesCarnet.filter(e => e.parcelleId === parcelleId)).pipe(delay(200));
  }

  getPlantations(parcelleId: number): Observable<Plantation[]> {
    return of(this.mock.plantations.filter(p => p.parcelleId === parcelleId)).pipe(delay(200));
  }

  ajouterEntreeCarnet(entree: Omit<EntreeCarnet, 'id'>): Observable<EntreeCarnet> {
    const nouvelle: EntreeCarnet = { ...entree, id: this.mock.entreesCarnet.length + 1 };
    this.mock.entreesCarnet.push(nouvelle);
    return of(nouvelle).pipe(delay(300));
  }
}