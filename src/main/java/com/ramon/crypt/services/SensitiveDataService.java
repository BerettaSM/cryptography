package com.ramon.crypt.services;

import java.lang.reflect.Field;
import java.util.function.UnaryOperator;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramon.crypt.services.annotations.SensitiveData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SensitiveDataService {

    private final CryptographyService cryptographyService;

    public <T> T encrypt(T object) {
        return process(object, value -> cryptographyService.encrypt(value));
    }

    public <T> T decrypt(T object) {
        return process(object, value -> cryptographyService.decrypt(value));
    }

    private <T> T process(T object, UnaryOperator<String> transform) {
        var wrapped = new BeanWrapperImpl(object);
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(SensitiveData.class)) {
                if (!String.class.isAssignableFrom(field.getType())) {
                    throw new RuntimeException("Only String fields can be encrypted/decrypted.");
                }
                String fieldName = field.getName();
                String fieldValue = (String) wrapped.getPropertyValue(fieldName);
                if (fieldValue == null) {
                    continue;
                }
                String processedValue = transform.apply(fieldValue);
                wrapped.setPropertyValue(fieldName, processedValue);
            }
        }
        return object;
    }

}
