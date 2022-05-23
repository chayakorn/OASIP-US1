package oasip.us1.backend.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class ErrorDTO{
    private final String timestamp;
    private final int status;
    private final String path;
    private final String message;
    private final Map<String,String> fieldErrors;
}