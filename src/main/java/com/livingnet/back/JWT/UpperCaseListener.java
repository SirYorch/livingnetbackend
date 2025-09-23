package com.livingnet.back.JWT;


import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.lang.reflect.Field;

public class UpperCaseListener {

    @PrePersist
    @PreUpdate
    public void beforeSave(Object entity) {
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                try {
                    String value = (String) field.get(entity);
                    if (value != null) {
                        field.set(entity, value.toUpperCase());
                    }
                } catch (IllegalAccessException ignored) {}
            }
        }
    }
}
