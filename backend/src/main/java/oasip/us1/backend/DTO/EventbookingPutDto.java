package oasip.us1.backend.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import oasip.us1.backend.entity.Eventcategory;

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

    private Instant eventStartTime;
    private String eventNotes;



}
