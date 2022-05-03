package oasip.us1.backend.repository;

import oasip.us1.backend.entity.Eventbooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

public interface EventbookingRepository extends JpaRepository<Eventbooking, Integer> {
    @Modifying
    @Transactional
    @Query(
            value = "insert into eventbooking (eventCategoryId,bookingName,bookingEmail,eventStartTime,eventDuration,eventNotes,name) values (:eventCategoryId,:bookingName,:bookingEmail,:eventStartTime,:eventDuration,:eventNotes,:name)",
            nativeQuery = true
    )
    void insertEvent(@Param("eventCategoryId") int eventCategoryId,
                     @Param("bookingName") String bookingName,@Param("bookingEmail") String bookingEmail,
                     @Param("eventStartTime") String eventStartTime,
                     @Param("eventDuration") int eventDuration ,
                     @Param("eventNotes") String eventNotes ,
                     @Param("name") String name);
    @Modifying
    @Transactional
    @Query(
            value = "select * from eventbooking where ((:start between eventStartTime and eventEndTime) or ( :end between eventStartTime and eventEndTime ))and eventCategoryId = :catid",
            nativeQuery = true
    )
    Collection<Eventbooking> findByEventStartTimeBetween(@Param("start") String start,@Param("end") String end,@Param("catid") int catid);

    @Modifying
    @Transactional
    @Query(
            value = "select * from eventbooking where ((:start between eventStartTime and eventEndTime) or ( :end between eventStartTime and eventEndTime ))and eventCategoryId = :catid and bookingId != :bid",
            nativeQuery = true
    )
    Collection<Eventbooking> findByEventStartTimeBetweenForPut(@Param("start") String start,@Param("end") String end,@Param("catid") int catid,@Param("bid") int bid);
}