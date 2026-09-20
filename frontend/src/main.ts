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
  personOutline,
  callOutline,
  arrowBackOutline,
  arrowForwardOutline,
  scanOutline,
  closeCircleOutline,
  analyticsOutline,
  checkmarkOutline,
  checkmarkCircleOutline,
  bugOutline,
  eyeOutline,
  bulbOutline,
  informationCircleOutline,
  refreshOutline,
  personAddOutline,
  helpCircleOutline,
  shieldCheckmarkOutline,
  bookOutline,
  timeOutline,
  imagesOutline,

  // Icônes météo
  sunnyOutline,
  partlySunnyOutline,
  cloudOutline,
  rainyOutline,
  snowOutline,
  thunderstormOutline,

  // Icône intelligence artificielle
  sparklesOutline,

  gridOutline,
  resizeOutline,
  homeOutline,
  navigateOutline,
  medkitOutline,


} from 'ionicons/icons';

import { AppComponent } from './app/app.component';

import { appConfig } from './app/app.config';

import {
  createOutline,
  trashOutline
} from 'ionicons/icons';


addIcons({

  // ------------------------------
  // Icônes générales
  // ------------------------------

  'camera-outline': cameraOutline,

  'leaf-outline': leafOutline,

  'chatbubble-ellipses-outline':
    chatbubbleEllipsesOutline,

  'calendar-outline':
    calendarOutline,

  'notifications-outline':
    notificationsOutline,

  'add-circle-outline':
    addCircleOutline,

  'chevron-forward-outline':
    chevronForwardOutline,

  'alert-circle-outline':
    alertCircleOutline,

  'location-outline':
    locationOutline,

  'warning-outline':
    warningOutline,

  'send-outline':
    sendOutline,

  'mail-outline':
    mailOutline,

  'lock-closed-outline':
    lockClosedOutline,

  'person-outline':
    personOutline,

  'call-outline':
    callOutline,

  'arrow-back-outline':
    arrowBackOutline,

  'arrow-forward-outline':
    arrowForwardOutline,

  'scan-outline':
    scanOutline,

  'close-circle-outline':
    closeCircleOutline,

  'analytics-outline':
    analyticsOutline,

  'checkmark-outline':
    checkmarkOutline,

  'checkmark-circle-outline':
    checkmarkCircleOutline,

  'bug-outline':
    bugOutline,

  'eye-outline':
    eyeOutline,

  'bulb-outline':
    bulbOutline,

  'information-circle-outline':
    informationCircleOutline,

  'refresh-outline':
    refreshOutline,

  'person-add-outline':
    personAddOutline,

  'help-circle-outline':
    helpCircleOutline,

  'shield-checkmark-outline':
    shieldCheckmarkOutline,

  'book-outline':
    bookOutline,

  'time-outline':
    timeOutline,

  'images-outline':
    imagesOutline,


  // ------------------------------
  // Icônes météo
  // ------------------------------

  'sunny-outline':
    sunnyOutline,

  'partly-sunny-outline':
    partlySunnyOutline,

  'cloud-outline':
    cloudOutline,

  'rainy-outline':
    rainyOutline,

  'snow-outline':
    snowOutline,

  'thunderstorm-outline':thunderstormOutline,
  'sparkles-outline':sparklesOutline,

  'create-outline': createOutline,
  'trash-outline':trashOutline,
  'grid-outline': gridOutline,
  'resize-outline': resizeOutline,
  'home-outline': homeOutline,
  'navigate-outline': navigateOutline,
  'medkit-outline': medkitOutline,
  
});


// ======================================================
// DÉMARRAGE DE L'APPLICATION
// ======================================================

bootstrapApplication(
  AppComponent,
  appConfig
).catch(
  err => console.error(err)
);