import { Injectable } from '@angular/core';
import { Observable, of, delay } from 'rxjs';
import { MockDataService } from './mock-data.service';
import { Alerte } from '../models/alerte.model';

@Injectable({ providedIn: 'root' })
export class AlerteService {
  constructor(private mock: MockDataService) {}

  getAlertes(): Observable<Alerte[]> {
    return of(this.mock.alertes).pipe(delay(300));
  }
}