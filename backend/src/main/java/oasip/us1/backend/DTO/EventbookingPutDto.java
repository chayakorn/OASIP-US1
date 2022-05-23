package oasip.us1.backend.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import oasip.us1.backend.entity.Eventcategory;

import javax.persistence.Column;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Data
public class EventbookingPutDto implements Serializable {

    @NotNull(message = "eventStartTime can't be null")
    @Future(message = "eventStartTime must be future")
    private Instant eventStartTime;

    @Size(max = 500,message = "eventNotes must be between 0-500 characters")
    private String eventNotes;



}
