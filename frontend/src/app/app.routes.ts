import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [

  {
    path: '',
    redirectTo: 'accueil',
    pathMatch: 'full'
  },

  // PUBLIC
  {
    path: 'accueil',
    loadComponent: () =>
      import('./features/accueil/accueil.component')
        .then(m => m.AccueilComponent)
  },

  {
    path: 'login',
    loadComponent: () =>
      import('./features/auth/login/login.component')
        .then(m => m.LoginComponent)
  },

  {
    path: 'register',
    loadComponent: () =>
      import('./features/auth/register/register.component')
        .then(m => m.RegisterComponent)
  },

  // UTILISATEUR CONNECTÉ
  {
    path: 'tabs',
    canActivate: [authGuard],

    loadComponent: () =>
      import('./features/tabs/tabs.component')
        .then(m => m.TabsComponent),

    children: [

      {
        path: '',
        redirectTo: 'diagnostic',
        pathMatch: 'full'
      },

      {
        path: 'diagnostic',
        loadComponent: () =>
          import('./features/diagnostic/diagnostic.component')
            .then(m => m.DiagnosticComponent)
      },

      {
        path: 'parcelles',
        loadComponent: () =>
          import('./features/parcelles/parcelles.component')
            .then(m => m.ParcellesComponent)
      },

      {
        path: 'assistant',
        loadComponent: () =>
          import('./features/assistant/assistant.component')
            .then(m => m.AssistantComponent)
      },

      {
        path: 'calendrier',
        loadComponent: () =>
          import('./features/calendrier/calendrier.component')
            .then(m => m.CalendrierComponent)
      },

      {
        path: 'alertes',
        loadComponent: () =>
          import('./features/alertes/alertes.component')
            .then(m => m.AlertesComponent)
      }
    ]
  },

  {
    path: '**',
    redirectTo: 'accueil'
  }
];