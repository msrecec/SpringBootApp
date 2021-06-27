package com.example.vaccines.service.vaccine;

import com.example.vaccines.command.vaccine.VaccineCommand;
import com.example.vaccines.dto.VaccineDTO;
import com.example.vaccines.model.vaccine.Vaccine;
import com.example.vaccines.repository.vaccine.VaccineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VaccineServiceImpl implements VaccineService{

    private final VaccineRepository vaccineRepository;

    public VaccineServiceImpl(VaccineRepository vaccineRepository) {
        this.vaccineRepository = vaccineRepository;
    }

    @Override
    public List<VaccineDTO> findAll() {
        return vaccineRepository.findAll().stream().map(s -> mapVaccineToDTO(s)).collect(Collectors.toList());
    }

    @Override
    public Optional<VaccineDTO> findVaccineByResearchName(String researchName) {
        return vaccineRepository.findVaccineByResearchName(researchName).map(this::mapVaccineToDTO);
    }

    @Override
    public List<VaccineDTO> findVaccineByType(String vaccineType) {
        return vaccineRepository.findVaccineByType(vaccineType).stream().map(v -> mapVaccineToDTO(v)).collect(Collectors.toList());
    }

    @Override
    public List<VaccineDTO> findVaccineBySuffix(String suffix) {
        return vaccineRepository.findVaccineBySuffix(suffix).stream().map(v -> mapVaccineToDTO(v)).collect(Collectors.toList());
    }

    @Override
    public Optional<VaccineDTO> save(VaccineCommand command) {
        return vaccineRepository.save(mapCommandToVaccine(command)).map(this::mapVaccineToDTO);
    }

    @Override
    public Optional<VaccineDTO> update(VaccineCommand command) {
        return vaccineRepository.update(mapCommandToVaccine(command)).map(this::mapVaccineToDTO);
    }

    @Override
    public void deleteByResearchName(String researchName) {
        vaccineRepository.deleteByResearchName(researchName);
    }

    private VaccineDTO mapVaccineToDTO(final Vaccine vaccine) {
        return new VaccineDTO(
                vaccine.getResearchName(),
                vaccine.getManufacturerName(),
                vaccine.getVaccineType(),
                vaccine.getRequiredNumberOfShots(),
                vaccine.getAvailableNumberOfShots()
        );
    }

    private Vaccine mapCommandToVaccine(final VaccineCommand command) {
        return Vaccine
                .builder()
                .researchName(command.getResearchName())
                .manufacturerName(command.getManufacturerName())
                .availableNumberOfShots(command.getNumberOfShots())
                .requiredNumberOfShots(command.getAvailableDoses())
                .build();
    }
}
