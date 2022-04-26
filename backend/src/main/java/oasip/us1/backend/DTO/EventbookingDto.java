package oasip.us1.backend.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import oasip.us1.backend.entity.Eventcategory;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Data
public class EventbookingDto implements Serializable {
    private Integer id;
    private String bookingName;
    private String bookingEmail;
    private Instant eventStartTime;
    private Integer eventCategoryId;
    private Integer eventDuration;
    private String eventNotes;
    private String name;
    private String categoryName;

    @JsonIgnore
    private Eventcategory eventcategory;

    public String getCategoryName() {
        return eventcategory.getEventCategoryName();
    }


}
