package com.example.vaccines.command.sideEffect;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;


@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SideEffectCommand {

    @NotBlank(message = "shortDescription must not be empty")
    @NotNull(message = "shortDescription must not be null")
    @Size(min = 1, max = 100)
    private String shortDescription;
    @PositiveOrZero(message = "frequency must be equal or greater than zero")
    private Double frequency;
    @NotBlank(message = "longDescription must not be empty")
    @NotNull(message = "longDescription must not be null")
    @Size(min = 1, max = 100)
    private String longDescription;

}
