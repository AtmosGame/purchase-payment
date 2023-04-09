package id.ac.ui.cs.advprog.purchasepayment.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Validator<T> {
    protected Validator<T> nextValidator;
    public abstract boolean isValid(T request);
}
