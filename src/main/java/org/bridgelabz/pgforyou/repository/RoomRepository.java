package org.bridgelabz.pgforyou.repository;

import org.bridgelabz.pgforyou.model.PG;
import org.bridgelabz.pgforyou.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long> {
    List<Room> findByPg(PG pg);
}
