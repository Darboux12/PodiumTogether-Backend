package com.podium.validation.validators;

import com.podium.model.dto.other.OpeningDay;
import com.podium.model.dto.other.PlaceLocalization;
import com.podium.model.dto.other.Rating;
import com.podium.model.dto.request.PlaceRequestDto;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.*;

public class PodiumValidator {

    private static PodiumValidator instance;

    private PodiumValidator() {}

    public static PodiumValidator getInstance() {
        if(instance == null) {
            instance = new PodiumValidator();
        }
        return instance;
    }

    public void validateRequestBody(Object object){

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

                // Else if field is complex object => go deeper
                else{

                    try {
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
                Date.class
        );

    }

    private void validatePermittedTypesAnnotations(Field field, Object object){

        try {

            AnnotationValidator.getInstance().podiumNotNull(object,field);
            AnnotationValidator.getInstance().podiumTextNotEmpty(object,field);
            AnnotationValidator.getInstance().podiumDatePast(object,field);
            AnnotationValidator.getInstance().podiumImageType(object,field);
            AnnotationValidator.getInstance().podiumDateFuture(object,field);
            AnnotationValidator.getInstance().podiumLength(object,field);
            AnnotationValidator.getInstance().podiumValidEmail(object,field);
            AnnotationValidator.getInstance().podiumNumberInt(object,field);
            AnnotationValidator.getInstance().podiumNumberDouble(object,field);
            AnnotationValidator.getInstance().podiumTime(object,field);

        } catch (IllegalAccessException | ParseException e) {
            e.printStackTrace();

        }

    }

    private void validatePermittedTypeCollectionAnnotations(Object object) {

        AnnotationValidator.getInstance().podiumCollectionTextNotEmpty(object);

    }

    public static void main(String[] args){

        PlaceLocalization placeLocalization = new PlaceLocalization(
                "PlaceTestCityName",
                "PlaceTestStreetName",
                123,
                "24-060",
                "Place test localization remarks"
        );

        List<OpeningDay> openingDays = new ArrayList<>();

        openingDays.add(new OpeningDay("Tuesday",true,true,
                "10:00","17:00"));





        List<Rating> ratings = List.of(new Rating("Service",5));



       PlaceRequestDto requestDto = new PlaceRequestDto(
                "Test Place Name",
                "Football",
                placeLocalization,
                openingDays,
                50,
                2.5,
                10,
                20,
                ratings,
                "This is test place review"
        );


       List<String> kol = new ArrayList<>();
       kol.add("Ha");
        kol.add("Ha");
        kol.add("Ha");








       PodiumValidator.getInstance().validateRequestBody(requestDto);






    }

}
