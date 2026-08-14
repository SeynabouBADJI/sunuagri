import { Injectable } from '@angular/core';
import { Observable, of, delay } from 'rxjs';
import { MockDataService } from './mock-data.service';
import { CalendrierCultural } from '../models/calendrier-cultural.model';

@Injectable({ providedIn: 'root' })
export class CalendrierService {
  constructor(private mock: MockDataService) {}

  getCalendriers(): Observable<CalendrierCultural[]> {
    return of(this.mock.calendriersCulturaux).pipe(delay(300));
  }
}