package org.bridgelabz.pgforyou.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomResponseDTO {

    private Long id;

    private String type;

    private BigDecimal rent;

    private int totalRooms;

    private int availableRooms;

    private String imageUrl;

    private Long pgId;
}