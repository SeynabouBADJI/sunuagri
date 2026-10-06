package com.projet.sunuagri.config;

import com.projet.sunuagri.entity.CalendrierCultural;
import com.projet.sunuagri.entity.Maladie;
import com.projet.sunuagri.entity.Plante;
import com.projet.sunuagri.repository.CalendrierCulturalRepository;
import com.projet.sunuagri.repository.MaladieRepository;
import com.projet.sunuagri.repository.PlanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PlanteRepository planteRepository;
    private final CalendrierCulturalRepository calendrierRepository;
    private final MaladieRepository maladieRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("========================================");
        System.out.println("🌱 INITIALISATION DES DONNÉES SUNUAGRI");
        System.out.println("========================================");

        // 1. Import des plantes
        initialiserPlantes();

        // 2. Import des calendriers culturaux depuis le CSV
        initialiserCalendriers();

        // 3. Import des 12 maladies (pour le diagnostic IA)
        initialiserMaladies();

        System.out.println("========================================");
        System.out.println("✅ INITIALISATION TERMINÉE");
        System.out.println("========================================");
    }

    // ============================================================
    // 1. PLANTES
    // ============================================================

    private void initialiserPlantes() {

        String fichier = "/data/plantes_sunuagri.csv";

        InputStream inputStream = getClass().getResourceAsStream(fichier);

        if (inputStream == null) {
            System.out.println("⚠️ Fichier " + fichier + " introuvable.");
            return;
        }

        int ajoutees = 0;

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8)
                )
        ) {

            // Ignorer l'en-tête
            reader.readLine();

            String ligne;

            while ((ligne = reader.readLine()) != null) {

                if (ligne.trim().isEmpty()) {
                    continue;
                }

                String[] colonnes = ligne.split(
                        ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"
                );

                if (colonnes.length < 5) {
                    System.out.println("⚠️ Ligne plante ignorée : " + ligne);
                    continue;
                }

                String nomCommun = nettoyer(colonnes[0]);

                if (planteRepository.existsByNomCommunIgnoreCase(nomCommun)) {
                    System.out.println("🌱 Déjà présente : " + nomCommun);
                    continue;
                }

                Plante plante = new Plante();

                plante.setNomCommun(nomCommun);
                plante.setNomScientifique(nettoyer(colonnes[1]));
                plante.setFamille(nettoyer(colonnes[2]));
                plante.setCycleVegetatif(Integer.parseInt(nettoyer(colonnes[3])));
                plante.setDescription(nettoyer(colonnes[4]));

                planteRepository.save(plante);

                ajoutees++;

                System.out.println("✅ Plante ajoutée : " + nomCommun);
            }

        } catch (Exception e) {

            System.out.println("❌ Erreur lors de l'import des plantes : " + e.getMessage());
        }

        System.out.println("🌱 Plantes ajoutées : " + ajoutees);
    }

    // ============================================================
    // 2. CALENDRIERS
    // ============================================================

    private void initialiserCalendriers() {

        String fichier = "/data/calendriers_sunuagri.csv";

        InputStream inputStream = getClass().getResourceAsStream(fichier);

        if (inputStream == null) {
            System.out.println("⚠️ Fichier " + fichier + " introuvable.");
            return;
        }

        int ajoutees = 0;
        int ignorees = 0;

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8)
                )
        ) {

            // Ignorer l'en-tête
            reader.readLine();

            String ligne;

            while ((ligne = reader.readLine()) != null) {

                if (ligne.trim().isEmpty()) {
                    continue;
                }

                String[] colonnes = ligne.split(
                        ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"
                );

                if (colonnes.length < 9) {
                    System.out.println("⚠️ Ligne calendrier ignorée : " + ligne);
                    continue;
                }

                String nomPlante = nettoyer(colonnes[1]);
                String zoneAgricole = nettoyer(colonnes[2]);

                Plante plante = planteRepository
                        .findAll()
                        .stream()
                        .filter(p -> p.getNomCommun().equalsIgnoreCase(nomPlante))
                        .findFirst()
                        .orElse(null);

                if (plante == null) {
                    System.out.println("⚠️ Plante introuvable : " + nomPlante);
                    ignorees++;
                    continue;
                }

                boolean existe = calendrierRepository
                        .findByPlanteId(plante.getId())
                        .stream()
                        .anyMatch(c -> c.getZoneAgricole()
                                .equalsIgnoreCase(zoneAgricole));

                if (existe) {
                    System.out.println("📅 Calendrier déjà présent : "
                            + nomPlante + " - " + zoneAgricole);
                    ignorees++;
                    continue;
                }

                CalendrierCultural calendrier = CalendrierCultural.builder()
                        .plante(plante)
                        .zoneAgricole(zoneAgricole)
                        .dureeCycle(nettoyer(colonnes[3]))
                        .periodeSemis(nettoyer(colonnes[4]))
                        .periodeRecolte(nettoyer(colonnes[5]))
                        .conditions(nettoyer(colonnes[6]))
                        .risquesClimatiques(nettoyer(colonnes[7]))
                        .mesuresAdaptation(nettoyer(colonnes[8]))
                        .build();

                calendrierRepository.save(calendrier);

                ajoutees++;

                System.out.println("✅ Calendrier ajouté : "
                        + nomPlante + " - " + zoneAgricole);
            }

        } catch (Exception e) {

            System.out.println("❌ Erreur lors de l'import des calendriers : " + e.getMessage());
        }

        System.out.println("📅 Calendriers ajoutés : " + ajoutees);
        System.out.println("⏭️ Calendriers ignorés : " + ignorees);
    }

    // ============================================================
    // 3. MALADIES (pour le diagnostic IA)
    // ============================================================

    private void initialiserMaladies() {

        if (maladieRepository.count() > 0) {
            System.out.println("🦠 Maladies déjà présentes : "
                    + maladieRepository.count());
            return;
        }

        System.out.println("🦠 Initialisation des 12 maladies...");

        // ---------- POMME DE TERRE ----------

        creerMaladie(
                "Alternariose (pomme de terre)",
                "Taches brunes circulaires avec des anneaux concentriques sur les feuilles âgées. "
                        + "Les feuilles jaunissent puis se dessèchent.",
                "Appliquer un fongicide à base de mancozèbe ou chlorothalonil. "
                        + "Éliminer les feuilles atteintes. Rotation des cultures."
        );

        creerMaladie(
                "Mildiou (pomme de terre)",
                "Taches vert-grisâtres à brunes sur les feuilles, avec un halo jaune. "
                        + "Un duvet blanc apparaît sur la face inférieure par temps humide. "
                        + "Progression très rapide.",
                "Traitement préventif avec fongicides à base de cuivre ou mancozèbe. "
                        + "Éliminer les plants atteints. Éviter l'excès d'humidité."
        );

        creerMaladie(
                "Pomme de terre saine",
                "Aucun symptôme détecté. La plante est en bonne santé.",
                "Aucun traitement nécessaire. Continuer l'entretien normal."
        );

        // ---------- TOMATE ----------

        creerMaladie(
                "Tache bactérienne (tomate)",
                "Petites taches noires huileuses sur les feuilles, tiges et fruits. "
                        + "Les taches ont un halo jaune.",
                "Utiliser des semences certifiées. Éviter l'arrosage par aspersion. "
                        + "Appliquer du cuivre. Rotation des cultures."
        );

        creerMaladie(
                "Alternariose (tomate)",
                "Taches brunes à noires avec des anneaux concentriques sur les feuilles basses. "
                        + "Peut aussi toucher les tiges et les fruits.",
                "Fongicide à base de chlorothalonil ou mancozèbe. "
                        + "Éliminer les feuilles atteintes. Paillage pour limiter les éclaboussures."
        );

        creerMaladie(
                "Mildiou (tomate)",
                "Taches huileuses vert-brun sur les feuilles, avec un duvet blanc-gris sur la face inférieure. "
                        + "Progression rapide en conditions humides et fraîches.",
                "Fongicides à base de cuivre, mancozèbe ou métalaxyl. "
                        + "Éliminer rapidement les plants atteints. Aérer les serres."
        );

        creerMaladie(
                "Cladosporiose (tomate)",
                "Taches jaunes sur la face supérieure, puis duvet vert olive à brun "
                        + "sur la face inférieure. Les feuilles se dessèchent et tombent.",
                "Réduire l'humidité. Fongicide à base de cuivre ou chlorothalonil. "
                        + "Ventiler les serres. Éliminer les débris végétaux."
        );

        creerMaladie(
                "Septoriose (tomate)",
                "Petites taches circulaires avec un centre gris et un bord foncé. "
                        + "Les feuilles inférieures sont touchées en premier.",
                "Fongicide préventif (chlorothalonil, mancozèbe). "
                        + "Retirer les feuilles atteintes. Paillage."
        );

        creerMaladie(
                "Tache ciblée (tomate)",
                "Taches brunes avec des anneaux concentriques bien marqués, "
                        + "souvent sur les feuilles âgées. Les fruits peuvent aussi être atteints.",
                "Rotation des cultures. Fongicide à base de chlorothalonil. "
                        + "Éviter les excès d'azote."
        );

        creerMaladie(
                "Virus YLCV (tomate)",
                "Feuilles recroquevillées vers le haut, jaunissement des nervures, "
                        + "plants rabougris. Transmis par les aleurodes (mouches blanches).",
                "Aucun traitement curatif. Lutte contre les aleurodes (insecticides). "
                        + "Arracher les plants infectés. Utiliser des variétés résistantes."
        );

        creerMaladie(
                "Virus mosaïque (tomate)",
                "Mosaïque vert clair/vert foncé sur les feuilles, déformation, "
                        + "réduction de la croissance. Transmis par contact et par pucerons.",
                "Aucun traitement curatif. Éliminer les plants atteints. "
                        + "Désinfecter les outils. Lutter contre les pucerons."
        );

        creerMaladie(
                "Tomate saine",
                "Aucun symptôme détecté. La plante est en bonne santé.",
                "Aucun traitement nécessaire. Continuer l'entretien normal."
        );

        System.out.println("✅ Maladies ajoutées : " + maladieRepository.count());
    }

    /**
     * Utilitaire pour créer une maladie.
     */
    private void creerMaladie(String nom, String symptomes, String traitement) {
        Maladie m = new Maladie();
        m.setNom(nom);
        m.setSymptomes(symptomes);
        m.setTraitement(traitement);
        maladieRepository.save(m);
    }

    // ============================================================
    // UTILITAIRE
    // ============================================================

    private String nettoyer(String valeur) {

        if (valeur == null) {
            return "";
        }

        return valeur
                .trim()
                .replaceAll("^\"|\"$", "")
                .trim();
    }
}