package com.example.booknbunk.repositories;

import com.example.booknbunk.models.Booking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Modifying
    @Transactional
    @Query(value = " UPDATE Booking b SET b.startDate = ?1 WHERE b.id = ?2")
    public void changeStartDate(@Param("startDate") LocalDate startDate, @Param("roomId") long roomId);
    @Modifying
    @Transactional
    @Query(value = " UPDATE Booking b SET b.endDate = ?1 WHERE b.id = ?2")
    public void changeEndDate(@Param("endDate") LocalDate startDate, @Param("roomId") long roomId);

    @Modifying
    @Transactional
    @Query(value = " UPDATE Booking b SET b.extraBed = ?1 WHERE b.id = ?2")
    public void changeEndDate(@Param("extraBed") int extraBed, @Param("roomId") long roomId);
    @Modifying
    @Transactional
    @Query(value = " UPDATE Booking b SET b.room = ?1 WHERE b.id = ?2")
    public void changeEndDate(@Param("room_id") Long room_id, @Param("roomId") long roomId);


}
