package com.assignment.weekTwo.MyExceptionController;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;
}
