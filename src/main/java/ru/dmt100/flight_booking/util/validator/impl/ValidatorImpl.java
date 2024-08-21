package ru.dmt100.flight_booking.util.validator.impl;

import ru.dmt100.flight_booking.exception.ValidationException;
import ru.dmt100.flight_booking.util.validator.Validator;

import java.lang.reflect.Field;

public abstract class ValidatorImpl<T> implements Validator<T> {
    @Override
    public T validate(T t) {
        Field[] fields = t.getClass().getDeclaredFields();

        for(Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if (value == null) {
                    throw new ValidationException("Field " + field.getName() + " cannot be null");
                }
                validateField(field.getName(), value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return t;
    }

    protected abstract void validateField(String fieldName, Object value);
}
