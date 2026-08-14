import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'accueil', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent),
  },
  {
    path: 'register',
    loadComponent: () => import('./features/auth/register/register.component').then(m => m.RegisterComponent),
  },
   {
    path: 'accueil',
    loadComponent: () => import('./features/accueil/accueil.component').then(m => m.AccueilComponent),
  },
  {
    path: 'tabs',
    loadComponent: () => import('./features/tabs/tabs.component').then(m => m.TabsComponent),
    children: [
      { path: '', redirectTo: 'diagnostic', pathMatch: 'full' },
      {
        path: 'diagnostic',
        loadComponent: () =>
          import('./features/diagnostic/diagnostic.component').then(m => m.DiagnosticComponent),
      },
    ],
  },
];