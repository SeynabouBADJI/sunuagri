import { Component } from '@angular/core';

// ✅ On importe les composants individuels utilisés dans le template
import { IonApp, IonRouterOutlet } from '@ionic/angular/standalone';

import { addIcons } from 'ionicons';
import * as allIcons from 'ionicons/icons';

import { AuthService } from './core/services/auth.service';

addIcons(allIcons);

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [IonApp, IonRouterOutlet],   // ← plus de IonicModule
  template: `
    <ion-app>
      <ion-router-outlet></ion-router-outlet>
    </ion-app>
  `
})
export class AppComponent {
  constructor(private authService: AuthService) {
    this.authService.restaurerSession();
  }
}