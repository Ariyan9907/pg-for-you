package org.bridgelabz.pgforyou.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomRequestDTO {

    @NotBlank
    private String type;

    @Positive
    private BigDecimal rent;

    @Positive
    private int totalRooms;

    @PositiveOrZero
    private int availableRooms;

    private String imageUrl;
}
