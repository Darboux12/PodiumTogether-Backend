package com.podium.controller.validation.validator;

import com.podium.controller.validation.exception.WrongAnnotationUsageException;
import com.podium.controller.validation.exception.PodiumEmptyTextException;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
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

    @Pointcut("@within(com.podium.controller.validation.validator.annotation.PodiumValidateController)")
    public void objectAnnotatedWithPodiumValidate() {}

    @Pointcut("execution(public * *(..))")
    public void publicMethods() {}

    @Pointcut("execution(private * *(..))")
    public void privateMethods() {}

    @Pointcut("execution(protected * *(..))")
    public void protectedMethods() {}

    @Pointcut("publicMethods() || protectedMethods() || privateMethods()")
    public void allMethods() {}

    @Pointcut("objectAnnotatedWithPodiumValidate() && allMethods()")
    public void methodsToValidate() {}

    @Pointcut("methodsToValidate())")
    public void parametersBodyToValidate() {}

    @Pointcut("methodsToValidate())")
    public void parametersVariableToValidate() {}

    @Before("parametersBodyToValidate()")
    public void aspectRequestBodyValidation(JoinPoint joinPoint) throws Throwable {

        if(joinPoint.getSignature() instanceof MethodSignature){

            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

            Method method = methodSignature.getMethod();

            Parameter[] parameters = method.getParameters();

            int i = 0;

            for(Parameter parameter : parameters){

                if(parameter.isAnnotationPresent(PodiumValidBody.class))
                    this.validateRequestBody(joinPoint.getArgs()[i]);

                i++;

            }

        }

    }

    @Before("parametersVariableToValidate()")
    public void aspectRequestVariableValidation(JoinPoint joinPoint) throws Throwable {

        // TODO Do something with variable

    }

    private void validateRequestBody(Object object) throws PodiumEmptyTextException {

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
