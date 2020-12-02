package com.podium.model.dto.validation.validator;

import com.podium.model.dto.validation.exception.PodiumEmptyTextException;
import com.podium.model.dto.validation.exception.WrongAnnotationUsageException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.*;

@Component
public class PodiumDtoValidator {

    private PodiumAnnotationValidator annotationValidator;

    public PodiumDtoValidator(PodiumAnnotationValidator annotationValidator) {
        this.annotationValidator = annotationValidator;
    }


    public void validateRequestBody(Object object) throws PodiumEmptyTextException {

        // Check if object is collection
        if(object instanceof Collection){

            // If object is collection => check its class type
            Class elementClass = ((Collection) object).toArray()[0].getClass();

            // Check if collection elements are one of permitted fields (aka primitive)
            if(this.isPermittedType(elementClass)){
                // Validate annotations of collection => can have annotation
                this.validatePermittedTypeCollectionAnnotations(object);
            }
            // Else go deeper
            else {

                for(Object o : ((Collection) object).toArray())
                    this.validateRequestBody(o);

            }

        }

        // Else if object is not collection
        else{

            // Get object class
            Class requestClass = object.getClass();

            // Iterate through object fields
            for(Field field : requestClass.getDeclaredFields()){

                // Get access to private fields
                field.setAccessible(true);

                // Check if fields is one of permitted fields (aka primitive)
                if(this.isPermittedType(field.getType()))
                    // Validate permitted types annotations
                    this.validatePermittedTypesAnnotations(field,object);

                // Else if field is complex object => check annotation => go deeper
                else{

                    // check annotation
                    this.validateCollectionAnnotations(field,object);

                    try {
                        // go deeper
                        validateRequestBody(field.get(object));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }



            }

        }

    }

    private boolean isPermittedType(Class clazz){
        return this.getPermittedTypes().contains(clazz);
    }

    private List<Class> getPermittedTypes(){

        return List.of(
                int.class,
                Integer.class,
                String.class,
                double.class,
                Double.class,
                boolean.class,
                Boolean.class,
                char.class,
                Character.class,
                Date.class,
                LocalTime.class
        );

    }

    private void validatePermittedTypesAnnotations(Field field, Object object) throws PodiumEmptyTextException {

        try {

            annotationValidator.podiumNotNull(object,field);
            annotationValidator.podiumTextNotEmpty(object,field);
            annotationValidator.podiumDatePast(object,field);
            annotationValidator.podiumImageType(object,field);
            annotationValidator.podiumDateFuture(object,field);
            annotationValidator.podiumLength(object,field);
            annotationValidator.podiumValidEmail(object,field);
            annotationValidator.podiumNumberInt(object,field);
            annotationValidator.podiumNumberDouble(object,field);
            annotationValidator.podiumTime(object,field);
            annotationValidator.podiumWeekDay(object,field);

        } catch (IllegalAccessException | ParseException | WrongAnnotationUsageException e) {
            e.printStackTrace();
            System.exit(1);
        }



    }

    private void validatePermittedTypeCollectionAnnotations(Object object) {

        System.out.println("Hejo");

    }

    private void validateCollectionAnnotations(Field field, Object object){

        try {
            annotationValidator.podiumCollectionLength(object,field);
        } catch (IllegalAccessException | WrongAnnotationUsageException e) {
            e.printStackTrace();
        }

    }

}
