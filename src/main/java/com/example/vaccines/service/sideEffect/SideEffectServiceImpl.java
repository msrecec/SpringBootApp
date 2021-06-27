package com.example.vaccines.service.sideEffect;

import com.example.vaccines.command.sideEffect.SideEffectCommand;
import com.example.vaccines.command.vaccine.VaccineCommand;
import com.example.vaccines.dto.SideEffectDTO;
import com.example.vaccines.dto.VaccineDTO;
import com.example.vaccines.model.sideEffect.SideEffect;
import com.example.vaccines.model.vaccine.Vaccine;
import com.example.vaccines.repository.sideEffect.SideEffectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SideEffectServiceImpl implements SideEffectService{

    private final SideEffectRepository sideEffectRepository;

    public SideEffectServiceImpl(SideEffectRepository sideEffectRepository) {
        this.sideEffectRepository = sideEffectRepository;
    }

    @Override
    public List<SideEffectDTO> findAll() {
        return sideEffectRepository.findAll().stream().map(s->mapSideEffectToDTO(s)).collect(Collectors.toList());
    }

    @Override
    public Optional<SideEffectDTO> findByShortDescription(String shortDescription) {
        return sideEffectRepository.findByShortDescription(shortDescription).map(this::mapSideEffectToDTO);
    }

    @Override
    public List<SideEffectDTO> findByLongDescription(String longDescription) {
        return sideEffectRepository.findByLongDescription(longDescription).stream().map(s -> mapSideEffectToDTO(s)).collect(Collectors.toList());
    }

    @Override
    public Optional<SideEffectDTO> save(SideEffectCommand command) {
        return sideEffectRepository.save(mapCommandToSideEffect(command)).map(this::mapSideEffectToDTO);
    }

    @Override
    public Optional<SideEffectDTO> update(SideEffectCommand command) {
        return sideEffectRepository.update(mapCommandToSideEffect(command)).map(this::mapSideEffectToDTO);
    }

//    @Override
//    public void delete(String shortDescription) {
//        return sideEffectRepository.delete()
//    }

    private SideEffectDTO mapSideEffectToDTO(final SideEffect sideEffect) {
        return new SideEffectDTO(
                sideEffect.getShortDescription(),
                sideEffect.getLongDescription(),
                sideEffect.getFrequency()
        );
    }

    private SideEffect mapCommandToSideEffect(final SideEffectCommand command) {
        return SideEffect
                .builder()
                .frequency(command.getFrequency())
                .longDescription(command.getLongDescription())
                .shortDescription(command.getShortDescription())
                .build();
    }
}
