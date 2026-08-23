
import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

import { routes } from './app.routes';
import { provideIonicAngular } from '@ionic/angular/standalone';

import { jwtInterceptor } from './core/interceptors/auth.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideIonicAngular(),

    provideRouter(routes),

    provideHttpClient(
      withInterceptors([
        jwtInterceptor
      ])
    )
  ]
};