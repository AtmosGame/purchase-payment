package id.ac.ui.cs.advprog.purchasepayment.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RequestProcessor {
}
