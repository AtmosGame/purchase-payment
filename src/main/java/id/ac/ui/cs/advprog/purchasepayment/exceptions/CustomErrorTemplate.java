package id.ac.ui.cs.advprog.purchasepayment.exceptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorTemplate {
    private String message;
    private String httpStatus;
    private ZonedDateTime timestamp;
}

