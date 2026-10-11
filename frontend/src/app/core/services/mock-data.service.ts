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

  // ===== UTILISATEUR CONNECTÉ (fallback) =====
  utilisateurCourant: Utilisateur = {
    id: 1,
    nom: 'Badji',
    prenom: 'Seynabou',
    email: 'seynabou@gmail.com',
    telephone: '778777777',
    role: 'AGRICULTEUR',
    localisation: 'Dakar'
  };

  // ===== DONNÉES MOCK (vides, en attendant le backend) =====
  utilisateurs: Utilisateur[] = [];
  parcelles: Parcelle[] = [];
  plantes: Plante[] = [];
  plantations: Plantation[] = [];
  entreesCarnet: EntreeCarnet[] = [];
  maladies: Maladie[] = [];
  diagnostics: Diagnostic[] = [];
  alertes: Alerte[] = [];
  calendriers: CalendrierCultural[] = [];
  conversations: ConversationIA[] = [];
  messages: Message[] = [];
}