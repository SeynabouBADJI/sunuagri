import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  template: `
    <div class="admin-page">
      <h1>Tableau de bord administrateur</h1>

      <p>Bienvenue dans l'espace d'administration de SunuAgri.</p>
    </div>
  `
})
export class AdminDashboardComponent {
}