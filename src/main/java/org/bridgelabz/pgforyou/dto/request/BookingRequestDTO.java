package org.bridgelabz.pgforyou.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookingRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    private Long roomId;
}