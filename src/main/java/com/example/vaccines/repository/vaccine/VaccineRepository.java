package com.example.vaccines.repository.vaccine;

import com.example.vaccines.model.vaccine.Vaccine;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface VaccineRepository {
    List<Vaccine> findAll();
    Optional<Vaccine> findVaccineByResearchName(String researchName);
    List<Vaccine> findVaccineByType(String vaccineType);
    List<Vaccine> findVaccineBySuffix(String suffix);
    Optional<Vaccine> save(Vaccine vaccine);
    Optional<Vaccine> update(Vaccine vaccine);
    void deleteByResearchName(String researchName);
}
