package oasip.us1.backend.repository;

import oasip.us1.backend.entity.Eventbooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
//    @Modifying
//    @Transactional
//    @Query(
//            value = "select * from eventbooking where ((:start between eventStartTime and eventEndTime) or ( :end between eventStartTime and eventEndTime )or (eventStartTime between :start and :end) or (eventEndTime between :start and :end))  and eventCategoryId = :catid",
//            nativeQuery = true
//    )
//    Collection<Eventbooking> findByEventStartTimeBetween(@Param("start") String start,@Param("end") String end,@Param("catid") int catid);

    @Modifying
    @Transactional
    @Query(
            value = "select * from eventbooking where ((:start between addtime(eventStartTime,'00:01:00') and addtime(eventStartTime,concat('00:',eventDuration-1,':00'))) or ( :end between addtime(eventStartTime,'00:01:00') and addtime(eventStartTime,concat('00:',eventDuration-1,':00')) )or (addtime(eventStartTime,'00:01:00') between :start and :end) or (addtime(eventStartTime,concat('00:',eventDuration-1,':00')) between :start and :end))  and eventCategoryId = :catid",
            nativeQuery = true
    )
    Collection<Eventbooking> findByEventStartTimeBetween(@Param("start") String start,@Param("end") String end,@Param("catid") int catid);

//    @Modifying
//    @Transactional
//    @Query(
//            value = "select * from eventbooking where ((:start between eventStartTime and eventEndTime) or ( :end between eventStartTime and eventEndTime )or (eventStartTime between :start and :end) or (eventEndTime between :start and :end)) and eventCategoryId = :catid and bookingId != :bid",
//            nativeQuery = true
//    )
//    Collection<Eventbooking> findByEventStartTimeBetweenForPut(@Param("start") String start,@Param("end") String end,@Param("catid") int catid,@Param("bid") int bid);

    @Modifying
    @Transactional
    @Query(
            value = "select * from eventbooking where ((:start between addtime(eventStartTime,'00:01:00') and addtime(eventStartTime,concat('00:',eventDuration-1,':00'))) or ( :end between addtime(eventStartTime,'00:01:00') and addtime(eventStartTime,concat('00:',eventDuration-1,':00')) )or (addtime(eventStartTime,'00:01:00') between :start and :end) or (addtime(eventStartTime,concat('00:',eventDuration-1,':00')) between :start and :end))  and eventCategoryId = :catid and bookingId != :bid",
            nativeQuery = true
    )
    Collection<Eventbooking> findByEventStartTimeBetweenForPut(@Param("start") String start,@Param("end") String end,@Param("catid") int catid,@Param("bid") int bid);

    @Transactional
    @Query(
            value = "select * from eventbooking where eventCategoryId in :id",
            countQuery = "select count(*) from eventbooking where eventCategoryId in :id",
            nativeQuery = true
    )
    Page<Eventbooking> findByCategoryId(@Param("id") Collection<String> id, @Param("page") Pageable pageable);

    @Query(
            value = "select * from eventbooking where eventStartTime like '%'||:date||'%' and eventCategoryId = :catid",
            nativeQuery = true
    )
    List<Eventbooking> findByEventStartTimeAndEventCategoryId(@Param("catid") int catid,@Param("date") String date);
}