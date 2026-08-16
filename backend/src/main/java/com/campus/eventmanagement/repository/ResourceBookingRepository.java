package com.campus.eventmanagement.repository;

import com.campus.eventmanagement.entity.ResourceBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ResourceBookingRepository extends JpaRepository<ResourceBooking, Long> {

    // Finds any existing booking for the same resource that overlaps the requested time window.
    // Two intervals [s1,e1) and [s2,e2) overlap iff s1 < e2 AND s2 < e1.
    @Query("""
           SELECT b FROM ResourceBooking b
           WHERE b.resource.id = :resourceId
             AND b.startTime < :endTime
             AND :startTime < b.endTime
           """)
    List<ResourceBooking> findOverlapping(
            @Param("resourceId") Long resourceId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    List<ResourceBooking> findByEventId(Long eventId);
}
