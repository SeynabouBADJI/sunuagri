package com.projet.sunuagri.service.impl;

import com.projet.sunuagri.dto.MaladieCreateDTO;
import com.projet.sunuagri.dto.MaladieDTO;
import com.projet.sunuagri.entity.Maladie;
import com.projet.sunuagri.repository.MaladieRepository;
import com.projet.sunuagri.service.MaladieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaladieServiceImpl implements MaladieService {

    private final MaladieRepository maladieRepository;

    @Override
    public MaladieDTO creer(MaladieCreateDTO dto) {

        Maladie maladie = new Maladie();

        maladie.setNom(dto.getNom());
        maladie.setSymptomes(dto.getSymptomes());
        maladie.setTraitement(dto.getTraitement());

        return convertirEnDTO(
                maladieRepository.save(maladie)
        );
    }

    @Override
    public MaladieDTO trouverParId(Long id) {

        Maladie maladie = maladieRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Maladie introuvable"));

        return convertirEnDTO(maladie);
    }

    @Override
    public List<MaladieDTO> trouverToutes() {

        return maladieRepository.findAll()
                .stream()
                .map(this::convertirEnDTO)
                .toList();
    }

    @Override
    public MaladieDTO modifier(
            Long id,
            MaladieCreateDTO dto) {

        Maladie maladie = maladieRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Maladie introuvable"));

        maladie.setNom(dto.getNom());
        maladie.setSymptomes(dto.getSymptomes());
        maladie.setTraitement(dto.getTraitement());

        return convertirEnDTO(
                maladieRepository.save(maladie)
        );
    }

    @Override
    public void supprimer(Long id) {

        if (!maladieRepository.existsById(id)) {
            throw new RuntimeException("Maladie introuvable");
        }

        maladieRepository.deleteById(id);
    }

    private MaladieDTO convertirEnDTO(Maladie maladie) {

        return new MaladieDTO(
                maladie.getId(),
                maladie.getNom(),
                maladie.getSymptomes(),
                maladie.getTraitement()
        );
    }
}