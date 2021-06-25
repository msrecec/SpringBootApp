package com.example.vaccines.model.sideEffect;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SideEffect {

    private Long id;
    private String shortDescription;
    private String longDescription;
    private String priority;

}
