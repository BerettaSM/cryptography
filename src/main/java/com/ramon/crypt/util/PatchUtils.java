package com.ramon.crypt.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.beans.BeanWrapperImpl;

import jakarta.persistence.Id;

public class PatchUtils {

     public static <T> void applyPartialUpdate(T source, T target) {
        BeanWrapperImpl src = new BeanWrapperImpl(source);
        BeanWrapperImpl dst = new BeanWrapperImpl(target);
        for (Field field : source.getClass().getDeclaredFields()) {
            Object value = src.getPropertyValue(field.getName());
            int modifiers = field.getModifiers();
            if (value == null ||
                    Modifier.isFinal(modifiers) ||
                    Modifier.isStatic(modifiers) ||
                    field.isAnnotationPresent(Id.class)) {
                continue;
            }
            dst.setPropertyValue(field.getName(), value);
        }
    }

}
