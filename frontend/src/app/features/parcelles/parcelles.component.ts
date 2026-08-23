import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { RouterLink } from '@angular/router';

import { Parcelle } from '../../core/models/parcelle.model';
import { ParcelleService } from '../../core/services/parcelle.service';
import { AuthService } from '../../core/services/auth.service';
import { EntreeCarnet } from '../../core/models/entree-carnet.model';

@Component({
  selector: 'app-parcelles',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterLink
  ],
  templateUrl: './parcelles.component.html',
  styleUrls: ['./parcelles.component.scss']
})
export class ParcellesComponent implements OnInit {

  // ============================================================
  // PARCELLES
  // ============================================================

  parcelles: Parcelle[] = [];

  chargement = false;
  erreur = '';

  // Statistiques
  totalParcelles = 0;
  totalSuperficie = 0;

  // ============================================================
  // MODAL PARCELLE
  // ============================================================

  modalOuvert = false;
  modeEdition = false;

  parcelleSelectionnee: Parcelle | null = null;

  // Formulaire parcelle
  nom = '';
  superficie: number | null = null;
  localisation = '';
  notes = '';

  // ============================================================
  // CARNET
  // ============================================================

  entreesCarnet: EntreeCarnet[] = [];

  entreeEnEdition: EntreeCarnet | null = null;

  nouveauType: EntreeCarnet['type'] = 'OBSERVATION';

  nouvelleDescription = '';

  // ============================================================
  // CONSTRUCTEUR
  // ============================================================

  constructor(
    private parcelleService: ParcelleService,
    private authService: AuthService
  ) {}

  // ============================================================
  // INITIALISATION
  // ============================================================

  ngOnInit(): void {

  console.log('===== PARCELLES =====');

  console.log(
    'Token :',
    this.authService.getToken()
  );

  console.log(
    'Utilisateur courant :',
    this.authService.utilisateurCourant()
  );

  console.log(
    'Est connecté :',
    this.authService.estConnecte()
  );

  this.chargerParcelles();
}

  // ============================================================
  // CHARGER LES PARCELLES
  // ============================================================

  chargerParcelles(): void {

  console.log('--- chargement des parcelles ---');

  const utilisateur =
    this.authService.utilisateurCourant();

  console.log(
    'Utilisateur récupéré :',
    utilisateur
  );

  if (!utilisateur) {

    console.log(
      '❌ Aucun utilisateur dans AuthService'
    );

    this.erreur =
      'Vous devez être connecté pour voir vos parcelles.';

    return;
  }

  console.log(
    '✅ Utilisateur connecté, ID =',
    utilisateur.id
  );

    this.chargement = true;
    this.erreur = '';

    this.parcelleService
      .getParcellesParUtilisateur(utilisateur.id)
      .subscribe({

        next: (parcelles) => {

          this.parcelles = parcelles;

          this.calculerStatistiques();

          this.chargement = false;
        },

        error: (err) => {

          console.error(
            'Erreur chargement parcelles :',
            err
          );

          this.erreur =
            'Impossible de charger vos parcelles.';

          this.chargement = false;
        }

      });
  }

  // ============================================================
  // STATISTIQUES
  // ============================================================

  calculerStatistiques(): void {

    this.totalParcelles =
      this.parcelles.length;

    this.totalSuperficie =
      this.parcelles.reduce(
        (total, parcelle) =>
          total + Number(parcelle.superficie),
        0
      );

    this.totalSuperficie =
      Number(this.totalSuperficie.toFixed(2));
  }

  // ============================================================
  // AJOUTER UNE PARCELLE
  // ============================================================

  ouvrirAjout(): void {

    this.modeEdition = false;

    this.parcelleSelectionnee = null;

    this.nom = '';
    this.superficie = null;
    this.localisation = '';
    this.notes = '';

    this.erreur = '';

    this.modalOuvert = true;
  }

  // ============================================================
  // MODIFIER UNE PARCELLE
  // ============================================================

  ouvrirModification(parcelle: Parcelle): void {

    this.modeEdition = true;

    this.parcelleSelectionnee = parcelle;

    this.nom = parcelle.nom;
    this.superficie = parcelle.superficie;
    this.localisation = parcelle.localisation;
    this.notes = parcelle.notes ?? '';

    this.erreur = '';

    this.modalOuvert = true;
  }

  // ============================================================
  // FERMER MODAL PARCELLE
  // ============================================================

  fermerModal(): void {
    this.modalOuvert = false;
  }

  // ============================================================
  // ENREGISTRER UNE PARCELLE
  // ============================================================

  enregistrerParcelle(): void {

    const utilisateur =
      this.authService.utilisateurCourant();

    if (!utilisateur) {

      this.erreur =
        'Vous devez être connecté.';

      return;
    }

    if (
      !this.nom.trim() ||
      this.superficie === null ||
      this.superficie <= 0 ||
      !this.localisation.trim()
    ) {

      this.erreur =
        'Veuillez remplir correctement les champs obligatoires.';

      return;
    }

    const parcelle: Parcelle = {

      id:
        this.parcelleSelectionnee?.id ?? 0,

      nom:
        this.nom.trim(),

      superficie:
        this.superficie,

      localisation:
        this.localisation.trim(),

      notes:
        this.notes.trim(),

      utilisateurId:
        utilisateur.id
    };

    this.chargement = true;

    // ----------------------------------------------------------
    // MODIFICATION
    // ----------------------------------------------------------

    if (
      this.modeEdition &&
      this.parcelleSelectionnee
    ) {

      this.parcelleService
        .modifierParcelle(
          this.parcelleSelectionnee.id,
          parcelle
        )
        .subscribe({

          next: () => {

            this.fermerModal();

            this.chargerParcelles();
          },

          error: (err) => {

            console.error(
              'Erreur modification :',
              err
            );

            this.erreur =
              'Impossible de modifier la parcelle.';

            this.chargement = false;
          }

        });

    }

    // ----------------------------------------------------------
    // AJOUT
    // ----------------------------------------------------------

    else {

      this.parcelleService
        .ajouterParcelle(parcelle)
        .subscribe({

          next: () => {

            this.fermerModal();

            this.chargerParcelles();
          },

          error: (err) => {

            console.error(
              'Erreur ajout :',
              err
            );

            this.erreur =
              'Impossible d\'ajouter la parcelle.';

            this.chargement = false;
          }

        });
    }
  }

  // ============================================================
  // SUPPRIMER UNE PARCELLE
  // ============================================================

  supprimerParcelle(parcelle: Parcelle): void {

    if (!parcelle.id) {
      return;
    }

    const confirmation =
      confirm(
        `Voulez-vous vraiment supprimer "${parcelle.nom}" ?`
      );

    if (!confirmation) {
      return;
    }

    this.chargement = true;

    this.parcelleService
      .supprimerParcelle(parcelle.id)
      .subscribe({

        next: () => {

          this.chargerParcelles();
        },

        error: (err) => {

          console.error(
            'Erreur suppression :',
            err
          );

          this.erreur =
            'Impossible de supprimer la parcelle.';

          this.chargement = false;
        }

      });
  }

  // ============================================================
  // OUVRIR LE CARNET D'UNE PARCELLE
  // ============================================================

  ouvrirCarnet(parcelle: Parcelle): void {

    this.parcelleSelectionnee = parcelle;

    this.entreeEnEdition = null;

    this.nouveauType = 'OBSERVATION';

    this.nouvelleDescription = '';

    /*
     * Pour l'instant le carnet reste local.
     *
     * Plus tard :
     * EntreeCarnetService
     *       ↓
     * GET /api/entrees-carnet/parcelle/{id}
     */

    this.entreesCarnet = [];

    this.modalOuvert = true;
  }

  // ============================================================
  // NOMBRE D'ENTREES DU CARNET
  // ============================================================

  nombreEntreesCarnet(parcelleId: number): number {

    return this.entreesCarnet.filter(
      entree =>
        entree.parcelleId === parcelleId
    ).length;
  }

  // ============================================================
  // CULTURE ACTUELLE
  // ============================================================

  culturesActuelles(parcelleId: number): string {

    /*
     * Pour le moment nous n'avons pas encore connecté
     * Plantation au frontend.
     *
     * On pourra remplacer cette méthode par :
     *
     * plantationService
     *   .getPlantationsParParcelle(parcelleId)
     */

    const plantation = this.parcelles
      .find(p => p.id === parcelleId);

    if (!plantation) {
      return 'Aucune culture renseignée';
    }

    return 'Culture en cours';
  }

  // ============================================================
  // AJOUTER / MODIFIER UNE ENTREE DU CARNET
  // ============================================================

  validerEntree(): void {

    if (!this.nouvelleDescription.trim()) {
      return;
    }

    if (!this.parcelleSelectionnee) {
      return;
    }

    // ----------------------------------------------------------
    // MODIFICATION
    // ----------------------------------------------------------

    if (this.entreeEnEdition) {

      this.entreeEnEdition.type =
        this.nouveauType;

      this.entreeEnEdition.description =
        this.nouvelleDescription.trim();

      this.entreeEnEdition =
        null;
    }

    // ----------------------------------------------------------
    // AJOUT
    // ----------------------------------------------------------

    else {

      const nouvelleEntree: EntreeCarnet = {

        id: Date.now(),

        date:
          new Date()
            .toISOString()
            .split('T')[0],

        type:
          this.nouveauType,

        description:
          this.nouvelleDescription.trim(),

        parcelleId:
          this.parcelleSelectionnee.id
      };

      this.entreesCarnet.push(
        nouvelleEntree
      );
    }

    // Réinitialisation
    this.nouveauType =
      'OBSERVATION';

    this.nouvelleDescription =
      '';
  }

  // ============================================================
  // MODIFIER UNE ENTREE
  // ============================================================

  modifierEntree(
    entree: EntreeCarnet
  ): void {

    this.entreeEnEdition =
      entree;

    this.nouveauType =
      entree.type;

    this.nouvelleDescription =
      entree.description;
  }

  // ============================================================
  // ANNULER MODIFICATION
  // ============================================================

  annulerEdition(): void {

    this.entreeEnEdition =
      null;

    this.nouveauType =
      'OBSERVATION';

    this.nouvelleDescription =
      '';
  }

  // ============================================================
  // SUPPRIMER UNE ENTREE
  // ============================================================

  supprimerEntree(
    entree: EntreeCarnet
  ): void {

    const confirmation =
      confirm(
        'Voulez-vous supprimer cette entrée du carnet ?'
      );

    if (!confirmation) {
      return;
    }

    this.entreesCarnet =
      this.entreesCarnet.filter(
        e => e.id !== entree.id
      );

    if (
      this.entreeEnEdition?.id === entree.id
    ) {
      this.annulerEdition();
    }
  }
}