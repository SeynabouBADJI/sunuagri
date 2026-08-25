package com.projet.sunuagri.security;

import com.projet.sunuagri.entity.Utilisateur;
import com.projet.sunuagri.repository.UtilisateurRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UtilisateurRepository utilisateurRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UtilisateurRepository utilisateurRepository) {

        this.jwtService = jwtService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Pas de token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        // try {

        //     if (jwtService.estValide(token)) {

        //         String email = jwtService.extraireEmail(token);

        //         Utilisateur utilisateur =
        //                 utilisateurRepository.findByEmail(email)
        //                         .orElse(null);

        //         if (utilisateur != null) {

        //             SimpleGrantedAuthority authority =
        //                     new SimpleGrantedAuthority(
        //                             "ROLE_" + utilisateur.getRole().name()
        //                     );

        //             UsernamePasswordAuthenticationToken authentication =
        //                     new UsernamePasswordAuthenticationToken(
        //                             utilisateur,
        //                             null,
        //                             Collections.singletonList(authority)
        //                     );

        //             SecurityContextHolder
        //                     .getContext()
        //                     .setAuthentication(authentication);
        //         }
        //     }

        // } catch (Exception e) {
        //     // Token invalide : on laisse Spring Security refuser
        //     // l'accès aux routes protégées.
        //     SecurityContextHolder.clearContext();
        // }

try {

    System.out.println("===== JWT FILTER =====");
    System.out.println("Token reçu : " + token);

    boolean valide = jwtService.estValide(token);

    System.out.println("Token valide : " + valide);

    if (valide) {

        String email = jwtService.extraireEmail(token);

        System.out.println("Email extrait : " + email);

        Utilisateur utilisateur =
                utilisateurRepository.findByEmail(email)
                        .orElse(null);

        System.out.println("Utilisateur trouvé : " + (utilisateur != null));

        if (utilisateur != null) {

            System.out.println("Utilisateur ID : " + utilisateur.getId());
            System.out.println("Utilisateur rôle : " + utilisateur.getRole());

            SimpleGrantedAuthority authority =
                    new SimpleGrantedAuthority(
                            "ROLE_" + utilisateur.getRole().name()
                    );

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            utilisateur,
                            null,
                            Collections.singletonList(authority)
                    );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            System.out.println("AUTHENTIFICATION OK");

        } else {

            System.out.println("ERREUR : utilisateur introuvable");

        }
    }

} catch (Exception e) {

    System.out.println("ERREUR JWT : " + e.getMessage());
    e.printStackTrace();

    SecurityContextHolder.clearContext();
}

        filterChain.doFilter(request, response);
    }
}