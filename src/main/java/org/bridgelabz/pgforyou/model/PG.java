package org.bridgelabz.pgforyou.model;



import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class PG {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    private BigDecimal rent;

    private String imageUrl;

    @OneToMany(mappedBy = "pg")
    private List<Room> rooms;

    @OneToMany(mappedBy = "pg")
    private List<Review> reviews;

}
