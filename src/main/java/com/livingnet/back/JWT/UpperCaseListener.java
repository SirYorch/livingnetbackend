package com.livingnet.back.JWT;


import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.lang.reflect.Field;

/**
 * Listener JPA que convierte campos de tipo String a mayúsculas antes de persistir o actualizar entidades.
 */
public class UpperCaseListener {

    /**
     * Método ejecutado antes de persistir o actualizar una entidad.
     * Convierte todos los campos String a mayúsculas.
     * @param entity La entidad a procesar.
     */
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
