import { bootstrapApplication } from '@angular/platform-browser';
import { addIcons } from 'ionicons';
import {
  cameraOutline,
  leafOutline,
  chatbubbleEllipsesOutline,
  calendarOutline,
  notificationsOutline,
  addCircleOutline,
  chevronForwardOutline,
  alertCircleOutline,
  locationOutline,
  warningOutline,
  sendOutline,
  mailOutline,
  lockClosedOutline,
} from 'ionicons/icons';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config';
import { checkmarkCircleOutline, timeOutline, imagesOutline } from 'ionicons/icons';

// Enregistrement explicite des icones utilisees dans l'app : evite tout appel
// reseau vers un CDN pour les charger, indispensable pour le mode hors connexion.
addIcons({
  'camera-outline': cameraOutline,
  'leaf-outline': leafOutline,
  'chatbubble-ellipses-outline': chatbubbleEllipsesOutline,
  'calendar-outline': calendarOutline,
  'notifications-outline': notificationsOutline,
  'add-circle-outline': addCircleOutline,
  'chevron-forward-outline': chevronForwardOutline,
  'alert-circle-outline': alertCircleOutline,
  'location-outline': locationOutline,
  'warning-outline': warningOutline,
  'send-outline': sendOutline,
  'mail-outline': mailOutline,
  'lock-closed-outline': lockClosedOutline,
  'checkmark-circle-outline': checkmarkCircleOutline,
  'time-outline': timeOutline,
  'images-outline': imagesOutline,
});

bootstrapApplication(AppComponent, appConfig).catch(err => console.error(err));