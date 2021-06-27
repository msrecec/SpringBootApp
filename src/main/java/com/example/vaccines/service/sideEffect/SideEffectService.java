package com.example.vaccines.service.sideEffect;

import com.example.vaccines.command.sideEffect.SideEffectCommand;
import com.example.vaccines.dto.SideEffectDTO;

import java.util.List;
import java.util.Optional;

public interface SideEffectService {
    List<SideEffectDTO> findAll();
    Optional<SideEffectDTO> findByShortDescription(String shortDescription);
    List<SideEffectDTO> findByLongDescription(String researchName);
    Optional<SideEffectDTO> save(SideEffectCommand command);
    Optional<SideEffectDTO> update(SideEffectCommand command);
//    void delete(String shortDescription);
}
