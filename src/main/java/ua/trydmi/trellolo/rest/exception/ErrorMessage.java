package ua.trydmi.trellolo.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {

    private int httpStatusCode;
    private String messages;

}
