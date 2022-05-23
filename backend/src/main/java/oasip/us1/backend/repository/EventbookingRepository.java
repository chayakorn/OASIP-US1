package oasip.us1.backend.repository;

import oasip.us1.backend.entity.Eventbooking;
import oasip.us1.backend.entity.Eventcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface EventbookingRepository extends JpaRepository<Eventbooking, Integer> {

    @Query(
            value = "select * from eventbooking where ((:start between addtime(eventStartTime,'00:01:00') and addtime(eventStartTime,concat('00:',eventDuration-1,':00'))) or ( :end between addtime(eventStartTime,'00:01:00') and addtime(eventStartTime,concat('00:',eventDuration-1,':00')) )or (addtime(eventStartTime,'00:01:00') between :start and :end) or (addtime(eventStartTime,concat('00:',eventDuration-1,':00')) between :start and :end))  and eventCategoryId = :catid",
            nativeQuery = true
    )
    Collection<Eventbooking> findByEventStartTimeBetween(@Param("start") String start,@Param("end") String end,@Param("catid") int catid);


    @Query(
            value = "select * from eventbooking where ((:start between addtime(eventStartTime,'00:01:00') and addtime(eventStartTime,concat('00:',eventDuration-1,':00'))) or ( :end between addtime(eventStartTime,'00:01:00') and addtime(eventStartTime,concat('00:',eventDuration-1,':00')) )or (addtime(eventStartTime,'00:01:00') between :start and :end) or (addtime(eventStartTime,concat('00:',eventDuration-1,':00')) between :start and :end))  and eventCategoryId = :catid and bookingId != :bid",
            nativeQuery = true
    )
    Collection<Eventbooking> findByEventStartTimeBetweenForPut(@Param("start") String start,@Param("end") String end,@Param("catid") int catid,@Param("bid") int bid);


    @Query(
            value = "select * from eventbooking where eventCategoryId in :id",
            countQuery = "select count(*) from eventbooking where eventCategoryId in :id",
            nativeQuery = true
    )
    Page<Eventbooking> findByCategoryId(@Param("id") Collection<String> id, @Param("page") Pageable pageable);

    @Query(
            value = "select * from eventbooking where (addtime(eventStartTime, concat(:offSet':00')) between :date and concat(:date,' 23:59:59')) and eventCategoryId = :catid",
            nativeQuery = true
    )
    List<Eventbooking> findByEventStartTimeAndEventCategoryIdForPlus(@Param("catid") int catid,@Param("date") String date,@Param("offSet") String offset);

    @Query(
            value = "select * from eventbooking where (subtime(eventStartTime, concat(:offSet,':00') between :date and concat(:date,' 23:59:59')) and eventCategoryId = :catid",
            nativeQuery = true
    )
    List<Eventbooking> findByEventStartTimeAndEventCategoryIdForMinus(@Param("catid") int catid,@Param("date") String date,@Param("offSet") String offset);

    @Query(
            value = "select * from eventbooking where addtime(eventStartTime, concat(:offSet,':00')) between :date and concat(:date,' 23:59:59')",
            countQuery = "select count(*) from eventbooking where addtime(eventStartTime, concat(:offSet,':00')) between :date and concat(:date,' 23:59:59')",
            nativeQuery = true)
    Page<Eventbooking> findByDateForPlus(@Param("date") String date,@Param("offSet") String offSet, Pageable pageable);


    @Query(
            value = "select * from eventbooking where subtimeeventStartTime, concat(:offSet,':00')) between :date and concat(:date,' 23:59:59')",
            countQuery = "select count(*) from eventbooking where subtime(eventStartTime, concat(:offSet,':00')) between :date and concat(:date,' 23:59:59')",
            nativeQuery = true)
    Page<Eventbooking> findByDateForMinus(@Param("date") String date,@Param("offSet") String offSet,Pageable pageable);

    @Query(
            value = "select * from eventbooking where eventCategoryId in :id and eventStartTime < :now",
            countQuery = "select count(*) from eventbooking where eventCategoryId in :id and eventStartTime < :now",
            nativeQuery = true
    )
    Page<Eventbooking> findByCategoryIdPast(@Param("id") Collection<String> id, @Param("page") Pageable pageable, @Param("now") String now);

    @Query(
            value = "select * from eventbooking where eventCategoryId in :id and eventStartTime > :now",
            countQuery = "select count(*) from eventbooking where eventCategoryId in :id and eventStartTime > :now",
            nativeQuery = true
    )
    Page<Eventbooking> findByCategoryIdUpcomming(@Param("id") Collection<String> id, @Param("page") Pageable pageable, @Param("now") String now);

    List<Eventbooking> findEventbookingByEventCategoryId(Eventcategory eventcategory);

}