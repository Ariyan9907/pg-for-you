package org.bridgelabz.pgforyou.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewRequestDTO {

    @NotBlank
    private String name;

    @Min(1)
    @Max(5)
    private int rating;

    @NotBlank
    private String comment;
}