package com.example.vaccines.repository.sideEffect;

import com.example.vaccines.model.sideEffect.SideEffect;

import java.util.List;
import java.util.Optional;

public interface SideEffectRepository {
    List<SideEffect> findAll();
    Optional<SideEffect> save(SideEffect sideEffect);
    Optional<SideEffect> update(SideEffect sideEffect);
    Optional<SideEffect> findByShortDescription(String shortDescription);
    List<SideEffect> findByLongDescription(String longDescription);
}
