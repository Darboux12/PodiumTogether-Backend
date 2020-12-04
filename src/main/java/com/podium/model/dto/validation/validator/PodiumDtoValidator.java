package com.podium.model.dto.validation.validator;

import com.podium.model.dto.validation.exception.PodiumEmptyTextException;
import com.podium.model.dto.validation.exception.WrongAnnotationUsageException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.xml.transform.Source;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.*;

@Aspect
@Component
public class PodiumDtoValidator {

    private PodiumAnnotationValidator annotationValidator;

    public PodiumDtoValidator(PodiumAnnotationValidator annotationValidator) {
        this.annotationValidator = annotationValidator;
    }

    @Pointcut("@within(com.podium.model.dto.validation.validator.annotation.validator.PodiumValidateController)")
    public void objectAnnotatedWithPodiumValidate() {}

    @Pointcut("execution(public * *(..))")
    public void publicMethods() {}

    @Pointcut("execution(* *(@com.podium.model.dto.validation.validator.annotation.validator.PodiumValidBody (*)))")
    public void methodsParameterWithPodiumValidBodyAnnotation() {}

    @Pointcut("execution(* *(@com.podium.model.dto.validation.validator.annotation.validator.PodiumValidVariable (*)))")
    public void methodsParameterWithPodiumValidVariableAnnotation() {}

    @Pointcut("objectAnnotatedWithPodiumValidate() && publicMethods()")
    public void methodsToValidate() {}

    @Pointcut("methodsToValidate() && methodsParameterWithPodiumValidBodyAnnotation()")
    public void parametersBodyToValidate() {}

    @Pointcut("methodsToValidate() && methodsParameterWithPodiumValidVariableAnnotation()")
    public void parametersVariableToValidate() {}

    @Before("parametersBodyToValidate()")
    public void aspectRequestBodyValidation(JoinPoint joinPoint) throws Throwable {

        if(joinPoint.getSignature() instanceof MethodSignature){

            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

            Method method = methodSignature.getMethod();

            Parameter[] parameters = method.getParameters();

            if(parameters.length == 1)
                this.validateRequestBody(joinPoint.getArgs()[0]);

        }

    }

    @Before("parametersVariableToValidate()")
    public void aspectRequestVariableValidation(JoinPoint joinPoint) throws Throwable {

        // TODO Do something with variable

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
