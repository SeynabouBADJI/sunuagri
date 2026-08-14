import { Injectable } from '@angular/core';
import { Utilisateur } from '../models/utilisateur.model';
import { Parcelle } from '../models/parcelle.model';
import { Plante } from '../models/plante.model';
import { Plantation } from '../models/plantation.model';
import { EntreeCarnet } from '../models/entree-carnet.model';
import { Maladie } from '../models/maladie.model';
import { Diagnostic } from '../models/diagnostic.model';
import { Alerte } from '../models/alerte.model';
import { CalendrierCultural } from '../models/calendrier-cultural.model';
import { ConversationIA, Message } from '../models/conversation.model';


@Injectable({ providedIn: 'root' })
export class MockDataService {

  utilisateurCourant: Utilisateur = {
    id: 1,
    nom: 'Badji',
    prenom: 'Seynabou',
    email: 'seynabou.badji@example.sn',
    telephone: '+221 77 000 00 00',
    role: 'AGRICULTEUR',
    localisation: 'Bambey, Diourbel',
  };

  plantes: Plante[] = [
    { id: 1, nomCommun: 'Mil', nomScientifique: 'Pennisetum glaucum', famille: 'Poaceae', cycleVegetatif: 90, description: 'Cereale resistante a la secheresse.' },
    { id: 2, nomCommun: 'Arachide', nomScientifique: 'Arachis hypogaea', famille: 'Fabaceae', cycleVegetatif: 100, description: 'Principale culture de rente au Senegal.' },
  ];

  parcelles: Parcelle[] = [
    { id: 1, nom: 'Parcelle Nord', superficie: 2.5, localisation: 'Bambey', notes: 'Sol sablonneux', utilisateurId: 1 },
    { id: 2, nom: 'Parcelle Sud', superficie: 1.2, localisation: 'Bambey', utilisateurId: 1 },
  ];

  plantations: Plantation[] = [
    { id: 1, dateDebut: '2026-06-01', saison: 'Hivernage', planteId: 1, parcelleId: 1 },
    { id: 2, dateDebut: '2026-06-15', saison: 'Hivernage', planteId: 2, parcelleId: 2 },
  ];

  entreesCarnet: EntreeCarnet[] = [
    { id: 1, date: '2026-06-05', type: 'ARROSAGE', description: 'Arrosage matinal, 40mm', parcelleId: 1 },
    { id: 2, date: '2026-06-20', type: 'OBSERVATION', description: 'Taches jaunes observees sur quelques feuilles', parcelleId: 2 },
  ];

  maladies: Maladie[] = [
    { id: 1, nom: 'Mildiou', symptomes: 'Taches brunes huileuses sur les feuilles, duvet blanchatre au revers', traitement: 'Fongicide a base de cuivre, ameliorer l\'aeration des plants' },
    { id: 2, nom: 'Rouille du mil', symptomes: 'Pustules orangees sur les feuilles', traitement: 'Varietes resistantes, rotation des cultures' },
  ];

  diagnostics: Diagnostic[] = [
    { id: 1, dateDiagnostic: '2026-07-02', image: 'assets/mock/diagnostic1.jpg', confiance: 0.91, utilisateurId: 1, planteId: 2, maladieId: 1, maladieNom: 'Mildiou', planteNom: 'Arachide' },
  ];

  alertes: Alerte[] = [
    { id: 1, titre: 'Mildiou signale a Bambey', message: 'Plusieurs cas de mildiou ont ete detectes dans un rayon de 5 km. Inspectez vos plants.', dateCreation: '2026-07-03', type: 'MALADIE', region: 'Bambey, Diourbel', maladieId: 1 },
  ];

  calendriersCulturaux: CalendrierCultural[] = [
    { id: 1, region: 'Bassin arachidier', saison: 'Hivernage', periodeSemis: 'Juin - Juillet', periodeFloraison: 'Aout', periodeRecolte: 'Octobre - Novembre', planteId: 1, planteNom: 'Mil' },
    { id: 2, region: 'Bassin arachidier', saison: 'Hivernage', periodeSemis: 'Juin', periodeFloraison: 'Aout - Septembre', periodeRecolte: 'Novembre', planteId: 2, planteNom: 'Arachide' },
  ];

  conversation: ConversationIA = { id: 1, dateCreation: '2026-07-01', utilisateurId: 1 };

  messages: Message[] = [
    { id: 1, contenu: 'Bonjour ! Je suis ton assistant agricole. Comment puis-je t\'aider aujourd\'hui ?', dateEnvoi: '2026-07-01T08:00:00', type: 'ASSISTANT', conversationId: 1 },
    { id: 2, contenu: 'Mes plants d\'arachide ont des taches sur les feuilles', dateEnvoi: '2026-07-01T08:01:00', type: 'UTILISATEUR', conversationId: 1 },
    { id: 3, contenu: 'Cela ressemble a des symptomes de mildiou. Utilise le diagnostic photo pour confirmer.', dateEnvoi: '2026-07-01T08:01:20', type: 'ASSISTANT', conversationId: 1 },
  ];

}