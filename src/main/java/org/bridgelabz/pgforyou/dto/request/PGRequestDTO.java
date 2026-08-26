package org.bridgelabz.pgforyou.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PGRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @Positive
    private BigDecimal rent;

    private String imageUrl;
}
