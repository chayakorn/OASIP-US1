package oasip.us1.backend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Getter
@Setter
@ToString
@Table(name = "eventbooking")
public class Eventbooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId",nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "eventCategoryId", nullable = false)
    private Eventcategory eventCategoryId;

    @Column(name = "bookingName", nullable = false, length = 100)
    private String bookingName;

    @Column(name = "bookingEmail", nullable = false, length = 100)
    private String bookingEmail;

    @Column(name = "eventStartTime", nullable = false)
    private Instant eventStartTime;

    @Column(name = "eventDuration", nullable = false)
    private Integer eventDuration;

    @Column(name = "eventNotes", length = 500)
    private String eventNotes;

    @Column(name = "name", length = 200)
    private String name;

}