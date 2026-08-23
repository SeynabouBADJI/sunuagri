import { Component } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { AuthService } from './core/services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    IonicModule
  ],
  template: `
    <ion-app>
      <ion-router-outlet></ion-router-outlet>
    </ion-app>
  `
})
export class AppComponent {

  constructor(
    private authService: AuthService
  ) {
    this.authService.restaurerSession();
  }
}