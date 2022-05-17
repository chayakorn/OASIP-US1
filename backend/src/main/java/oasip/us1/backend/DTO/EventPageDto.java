package oasip.us1.backend.DTO;

import lombok.Data;

import java.util.List;
@Data
public class EventPageDto {

        private List<EventbookingDto> content;
        private int number;
        private int size;
        private int totalPages;
        private int numberOfElements;
        private int totalElements;
        private boolean last;
        private boolean first;
    }

