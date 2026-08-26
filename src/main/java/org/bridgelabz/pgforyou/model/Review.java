package org.bridgelabz.pgforyou.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int rating;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "pg_id")
    private PG pg;
}
