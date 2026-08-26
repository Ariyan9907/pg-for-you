package org.bridgelabz.pgforyou.repository;

import org.bridgelabz.pgforyou.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {

}
