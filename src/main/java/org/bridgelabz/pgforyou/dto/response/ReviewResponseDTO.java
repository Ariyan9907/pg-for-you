package org.bridgelabz.pgforyou.dto.response;

import lombok.Data;

@Data
public class ReviewResponseDTO {

    private Long id;

    private String name;

    private int rating;

    private String comment;

    private Long pgId;
}