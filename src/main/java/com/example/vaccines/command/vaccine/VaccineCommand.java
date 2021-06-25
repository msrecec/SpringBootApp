package com.example.vaccines.command.vaccine;

import com.example.vaccines.model.vaccine.VaccineType;
import lombok.*;

import javax.validation.constraints.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VaccineCommand {

    @NotBlank(message="Research name must not be empty")
    @NotNull(message="Research name must not be null")
    @Size(min = 1, max = 100)
    private String researchName;
    @NotBlank(message="Manufacturer name must not be empty")
    @NotNull(message="Manufacturer name must not be null")
    @Size(min = 1, max = 100)
    private String manufacturerName;
    @NotNull(message = "Vaccine type must not be null")
    @Pattern(regexp = "(?=.*MRNA)(?=.*VIRAL_VECTOR)")
    private VaccineType type;
    @Positive(message = "Required number of shots must be greater than zero")
    @NotNull(message = "Required number of shots must not be null")
    private Integer numberOfShots;
    @PositiveOrZero(message = "Available number of shots must be equal or greater than zero")
    @NotNull(message = "Available number of shots must not be null")
    private Integer availableDoses;

}
