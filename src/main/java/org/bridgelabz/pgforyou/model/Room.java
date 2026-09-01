package org.bridgelabz.pgforyou.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private BigDecimal rent;

    private int totalRooms;

    private int availableRooms;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "pg_id")
    private PG pg;

}


