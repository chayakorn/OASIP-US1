package oasip.us1.backend.DTO;

import javax.persistence.Column;
import javax.validation.constraints.*;

public class EventCategoryDto {

    private Integer id;
    @NotNull(message = "eventCategoryName can not be null")
    @NotBlank(message = "eventCategoryName can not be blank")
    @Size(max = 100 , message = "eventCategoryName must be between 1-100 characters")
    private String eventCategoryName;

    @Size(max = 500 , message = "evetnCategoryDescription must be between 0-500 chracters")
    private String eventCategoryDescription;

    @NotNull(message = "eventDuraion can not be null")
    @Min(value = 1 ,message = "minimum eventDuration value is 1")
    @Max(value = 480 ,message = "max eventDuration value is 480")
    private Integer eventDuration;
}
