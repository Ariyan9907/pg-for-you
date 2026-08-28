package org.bridgelabz.pgforyou.repository;

import org.bridgelabz.pgforyou.model.PG;
import org.bridgelabz.pgforyou.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByPg(PG pg);
}
