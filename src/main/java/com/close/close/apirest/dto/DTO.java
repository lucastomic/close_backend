package com.close.close.apirest.dto;

import com.close.close.user.User;

import java.lang.reflect.Field;

abstract public class DTO<T> {
    T object;
    protected DTO(T object){
        this.object = object;
    }
    protected Object getPrivateField(String fieldName)  {
        try{
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        }catch (NoSuchFieldException | IllegalAccessException e){
            throw new DTOParsingException();
        }
    }
}
