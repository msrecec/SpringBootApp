package com.example.vaccines.dto;

import com.example.vaccines.model.vaccine.VaccineType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VaccineDTO {
    private String researchName;
    private String manufacturerName;
    private VaccineType type;
    private Integer numberOfShots;
    private Integer availableDoses;
}
