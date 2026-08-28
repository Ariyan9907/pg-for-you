package org.bridgelabz.pgforyou.repository;

import org.bridgelabz.pgforyou.model.PG;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PGRepository extends JpaRepository<PG,Long> {

    List<PG> findByLocationContainingIgnoreCase(String location);


}
