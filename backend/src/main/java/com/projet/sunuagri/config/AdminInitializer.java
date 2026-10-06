package com.projet.sunuagri.config;

import com.projet.sunuagri.entity.Utilisateur;
import com.projet.sunuagri.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        String emailAdmin = "admin@sunuagri.sn";
        String motDePasseAdmin = "Admin@SunuAgri2026";

        // Vérifier si l'administrateur existe déjà
        if (utilisateurRepository.existsByEmail(emailAdmin)) {

            System.out.println("========================================");
            System.out.println("👤 Administrateur déjà présent");
            System.out.println("========================================");

            return;
        }

        // Création du compte administrateur
        Utilisateur admin = new Utilisateur();

        admin.setNom("Admin");
        admin.setPrenom("SunuAgri");
        admin.setEmail(emailAdmin);
        admin.setTelephone("770000000");
        admin.setMotDePasse(
                passwordEncoder.encode(motDePasseAdmin)
        );
        admin.setLocalisation("Dakar");
        admin.setRole(Utilisateur.Role.ADMINISTRATEUR);

        utilisateurRepository.save(admin);

        System.out.println("========================================");
        System.out.println("✅ ADMINISTRATEUR CRÉÉ");
        System.out.println("📧 Email : " + emailAdmin);
        System.out.println("🔐 Mot de passe : " + motDePasseAdmin);
        System.out.println("========================================");
    }
}