package oasip.us1.backend.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import oasip.us1.backend.entity.Eventcategory;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.time.Instant;
@Data
public class EventbookingInsertDto {

    @Column(name = "bookingName", nullable = false, length = 100)
    @NotBlank(message = "bookingName can't be null or blank")
    @Size(max = 100 , message = "bookingName must be between 1-100 characters")
    private String bookingName;

    @Email(message = "email must be valid form")
    @NotBlank(message = "email is mendatory")
    @Size(max = 100 , message = "email must be between 1-100 characters")
    private String bookingEmail;

    @Column(name = "eventStartTime", nullable = false)
    @NotNull(message = "eventStartTime can't be null")
    @Future(message = "eventStartTime must be future")
    private Instant eventStartTime;

    @NotNull(message = "eventCategoryId can not be null")
    private Integer eventCategoryId;


    @Column(name = "eventNotes", length = 500)
    private String eventNotes;
    private String name;
}
