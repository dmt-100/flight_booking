package ru.dmt100.flight_booking.entity.booking.util.validator;

import org.springframework.stereotype.Component;
import ru.dmt100.flight_booking.exception.ValidationException;
import ru.dmt100.flight_booking.util.validator.impl.ValidatorImpl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Component("bookingValidator")
public class BookingValidator extends ValidatorImpl {

    @Override
    protected void validateField(String fieldName, Object value) {
        switch (fieldName) {
            case "bookRef":
                if(!(value instanceof String) || ((String) value).length() != 6) {
                    throw new ValidationException("Invalid bookRef");
                }
                break;
            case "bookDate":
                if (!(value instanceof OffsetDateTime)) {
                    throw new ValidationException("Invalid bookDate");
                }
                break;
            case "totalAmount":
                if (!(value instanceof BigDecimal)) {
                    throw new ValidationException("Invalid totalAmount: must be a BigDecimal");
                }
                BigDecimal totalAmount = (BigDecimal) value;
                if (totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new ValidationException("Invalid totalAmount: must be greater than zero");
                }
                if (totalAmount.compareTo(new BigDecimal("99999999.99")) > 0) {
                    throw new ValidationException("Invalid totalAmount: must be less than 100,000,000");
                }
                if (totalAmount.scale() > 2) {
                    throw new ValidationException("Invalid totalAmount: must have at most 2 decimal places");
                }
                break;
            default:
                throw new ValidationException("Unknown field: " + fieldName);
        }
    }
}
