package com.example.vaccines.service.vaccine;

import com.example.vaccines.command.vaccine.VaccineCommand;
import com.example.vaccines.dto.VaccineDTO;

import java.util.List;
import java.util.Optional;

public interface VaccineService {

    List<VaccineDTO> findAll();

    Optional<VaccineDTO> findVaccineByResearchName(String researchName);

    List<VaccineDTO> findVaccineByType(String vaccineType);

    List<VaccineDTO> findVaccineBySuffix(String suffix);

    Optional<VaccineDTO> save(final VaccineCommand command);

    Optional<VaccineDTO> update(VaccineCommand command);

    void deleteByResearchName(String researchName);

}
