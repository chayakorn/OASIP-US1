package oasip.us1.backend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    @NotNull(message = "eventCategoryId can not be null")
    private Eventcategory eventCategoryId;

    @Column(name = "bookingName", nullable = false, length = 100)
    @NotBlank(message = "bookingName can't be null or blank")
    @Size(max = 100 , message = "bookingName must be between 1-100 characters")
    private String bookingName;

    @Email(message = "email must be valid form")
    @NotBlank(message = "email is mendatory")
    @Size(max = 100 , message = "email must be between 1-100 characters")
    @Column(name = "bookingEmail", nullable = false, length = 100)
    private String bookingEmail;

    @Column(name = "eventStartTime", nullable = false)
    @NotNull(message = "eventStartTime can't be null")
    @Future(message = "eventStartTime must be future")
    private Instant eventStartTime;

    @Column(name = "eventDuration", nullable = false)
    @NotNull(message = "eventDuration can be null")
    private Integer eventDuration;

    @Column(name = "eventNotes", length = 500)
    @Size(max = 500, message = "eventNotes must be between 0-500 characters")
    private String eventNotes;


}