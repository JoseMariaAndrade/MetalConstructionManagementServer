package exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

public class MyConstraintViolationException extends Exception {

    public MyConstraintViolationException(ConstraintViolationException constraintViolationException) {
        super(getConstraintViolationMessages(constraintViolationException));
    }

    private static String getConstraintViolationMessages(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> constraintViolationSet = constraintViolationException.getConstraintViolations();
        StringBuilder errorMessages = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : constraintViolationSet){
            errorMessages.append(constraintViolation.getMessage());
            errorMessages.append("; ");
        }
        return errorMessages.toString();
    }
}
