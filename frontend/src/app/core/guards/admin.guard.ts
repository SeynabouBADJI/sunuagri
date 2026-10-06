import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const adminGuard: CanActivateFn = () => {

  const authService = inject(AuthService);
  const router = inject(Router);

  // Pas connecté
  if (!authService.estConnecte()) {
    return router.createUrlTree(['/login']);
  }

  const utilisateur = authService.utilisateurCourant();

  // Administrateur
  if (utilisateur?.role === 'ADMINISTRATEUR') {
    return true;
  }

  // Agriculteur → retour vers son espace
  return router.createUrlTree(['/tabs/diagnostic']);
};