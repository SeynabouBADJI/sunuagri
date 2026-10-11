
// import { ApplicationConfig } from '@angular/core';
// import { provideRouter } from '@angular/router';
// import { provideHttpClient, withInterceptors } from '@angular/common/http';

// import { routes } from './app.routes';
// import { provideIonicAngular } from '@ionic/angular/standalone';

// import { jwtInterceptor } from './core/interceptors/auth.interceptor';

// export const appConfig: ApplicationConfig = {
//   providers: [
//     provideIonicAngular(),

//     provideRouter(routes),

//     provideHttpClient(
//       withInterceptors([
//         jwtInterceptor
//       ])
//     )
//   ]
// };
import { ApplicationConfig } from '@angular/core';
import {
  provideRouter,
  RouteReuseStrategy
} from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

import {
  IonicRouteStrategy,
  provideIonicAngular
} from '@ionic/angular/standalone';

import { routes } from './app.routes';
import { jwtInterceptor } from './core/interceptors/auth.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy },

    provideIonicAngular({
      mode: 'md',   // ← AJOUTER ÇA
    }),

    provideRouter(routes),

    provideHttpClient(
      withInterceptors([
        jwtInterceptor
      ])
    )
  ]
};