package com.projet.sunuagri.config;

import com.projet.sunuagri.entity.CalendrierCultural;
import com.projet.sunuagri.entity.Plante;
import com.projet.sunuagri.repository.CalendrierCulturalRepository;
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

    @Override
    public void run(String... args) throws Exception {

        System.out.println("========================================");
        System.out.println("🌱 INITIALISATION DES DONNÉES SUNUAGRI");
        System.out.println("========================================");

        // 1. Import des plantes
        initialiserPlantes();

        // 2. Import des calendriers culturaux depuis le CSV
        initialiserCalendriers();

        System.out.println("========================================");
        System.out.println("✅ INITIALISATION TERMINÉE");
        System.out.println("========================================");
    }

    /**
     * Import des plantes depuis plantes_sunuagri.csv
     */
    private void initialiserPlantes() {

        String fichier = "/data/plantes_sunuagri.csv";

        InputStream inputStream =
                getClass().getResourceAsStream(fichier);

        if (inputStream == null) {
            System.out.println(
                    "⚠️ Fichier " + fichier + " introuvable."
            );
            return;
        }

        int ajoutees = 0;

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                inputStream,
                                StandardCharsets.UTF_8
                        )
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
                    System.out.println(
                            "⚠️ Ligne plante ignorée : " + ligne
                    );
                    continue;
                }

                String nomCommun = nettoyer(colonnes[0]);

                // Vérifier si la plante existe déjà
                if (planteRepository
                        .existsByNomCommunIgnoreCase(nomCommun)) {

                    System.out.println(
                            "🌱 Déjà présente : " + nomCommun
                    );

                    continue;
                }

                Plante plante = new Plante();

                plante.setNomCommun(nomCommun);
                plante.setNomScientifique(nettoyer(colonnes[1]));
                plante.setFamille(nettoyer(colonnes[2]));
                plante.setCycleVegetatif(
                        Integer.parseInt(nettoyer(colonnes[3]))
                );
                plante.setDescription(
                        nettoyer(colonnes[4])
                );

                planteRepository.save(plante);

                ajoutees++;

                System.out.println(
                        "✅ Plante ajoutée : " + nomCommun
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "❌ Erreur lors de l'import des plantes : "
                            + e.getMessage()
            );
        }

        System.out.println(
                "🌱 Plantes ajoutées : " + ajoutees
        );
    }

    /**
     * Import des calendriers depuis
     * calendrier_cultural_provisoire_senegal.csv
     */
    private void initialiserCalendriers() {

        String fichier =
                "/data/calendriers_sunuagri.csv";

        InputStream inputStream =
                getClass().getResourceAsStream(fichier);

        if (inputStream == null) {
            System.out.println(
                    "⚠️ Fichier " + fichier + " introuvable."
            );
            return;
        }

        int ajoutees = 0;
        int ignorees = 0;

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                inputStream,
                                StandardCharsets.UTF_8
                        )
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

                /*
                 * Colonnes du CSV :
                 *
                 * 0 index
                 * 1 nomPlante
                 * 2 zoneAgricole
                 * 3 dureeCycle
                 * 4 periodeSemis
                 * 5 periodeRecolte
                 * 6 conditions
                 * 7 risquesClimatiques
                 * 8 mesuresAdaptation
                 * 9 statut
                 */

                if (colonnes.length < 9) {

                    System.out.println(
                            "⚠️ Ligne calendrier ignorée : "
                                    + ligne
                    );

                    continue;
                }

                String nomPlante = nettoyer(colonnes[1]);
                String zoneAgricole = nettoyer(colonnes[2]);

                /*
                 * Rechercher la plante correspondante
                 */
                Plante plante = planteRepository
                        .findAll()
                        .stream()
                        .filter(p ->
                                p.getNomCommun()
                                        .equalsIgnoreCase(nomPlante)
                        )
                        .findFirst()
                        .orElse(null);

                if (plante == null) {

                    System.out.println(
                            "⚠️ Plante introuvable : "
                                    + nomPlante
                    );

                    ignorees++;

                    continue;
                }

                /*
                 * Vérifier si le calendrier existe déjà
                 * pour cette plante et cette zone.
                 */
                boolean existe =
                        calendrierRepository
                                .findByPlanteId(plante.getId())
                                .stream()
                                .anyMatch(c ->
                                        c.getZoneAgricole()
                                                .equalsIgnoreCase(
                                                        zoneAgricole
                                                )
                                );

                if (existe) {

                    System.out.println(
                            "📅 Calendrier déjà présent : "
                                    + nomPlante
                                    + " - "
                                    + zoneAgricole
                    );

                    ignorees++;

                    continue;
                }

                /*
                 * Création du calendrier
                 */
                CalendrierCultural calendrier =
                        CalendrierCultural.builder()
                                .plante(plante)
                                .zoneAgricole(zoneAgricole)
                                .dureeCycle(
                                        nettoyer(colonnes[3])
                                )
                                .periodeSemis(
                                        nettoyer(colonnes[4])
                                )
                                .periodeRecolte(
                                        nettoyer(colonnes[5])
                                )
                                .conditions(
                                        nettoyer(colonnes[6])
                                )
                                .risquesClimatiques(
                                        nettoyer(colonnes[7])
                                )
                                .mesuresAdaptation(
                                        nettoyer(colonnes[8])
                                )
                                .build();

                calendrierRepository.save(calendrier);

                ajoutees++;

                System.out.println(
                        "✅ Calendrier ajouté : "
                                + nomPlante
                                + " - "
                                + zoneAgricole
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "❌ Erreur lors de l'import des calendriers : "
                            + e.getMessage()
            );
        }

        System.out.println(
                "📅 Calendriers ajoutés : " + ajoutees
        );

        System.out.println(
                "⏭️ Calendriers ignorés : " + ignorees
        );
    }

    /**
     * Nettoyage des valeurs CSV.
     */
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