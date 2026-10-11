import { HttpInterceptorFn } from '@angular/common/http';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {

  const token = localStorage.getItem('sunuagri_token');

  if (token) {

    const requete = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

    return next(requete);
  }

  return next(req);
};