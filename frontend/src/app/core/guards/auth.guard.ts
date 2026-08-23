import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = () => {

  const router = inject(Router);
  const authService = inject(AuthService);

  // Vérifie que l'utilisateur possède bien
  // un token ET les informations utilisateur
  if (authService.estConnecte()) {
    return true;
  }

  // Sinon retour vers la page de connexion
  return router.createUrlTree(['/login']);
};