package org.bridgelabz.pgforyou.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PGResponseDTO {

    private Long id;

    private String name;

    private String location;

    private BigDecimal rent;

    private String imageUrl;
}
