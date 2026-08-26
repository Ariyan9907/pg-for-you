package org.bridgelabz.pgforyou.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingResponseDTO {

    private Long id;

    private String name;

    private String phone;

    private LocalDate bookingDate;

    private String status;

    private Long roomId;
}