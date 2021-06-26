package com.example.vaccines.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SideEffectDTO {
    private String shortDescription;
    private String description;
    private Double frequency;
}
