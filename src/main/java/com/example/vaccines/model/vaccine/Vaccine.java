package com.example.vaccines.model.vaccine;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Vaccine {

    private Long id;
    private String researchName;
    private String manufacturerName;
    private VaccineType vaccineType;
    private Integer requiredNumberOfShots;
    private Integer availableNumberOfShots;

}
