package id.ac.ui.cs.advprog.purchasepayment.exceptions;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorTemplate {
    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;
}

