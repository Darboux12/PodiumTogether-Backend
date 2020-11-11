package com.podium.service;

import com.podium.model.dto.request.EventRequestDto;
import com.podium.model.dto.response.CountryResponseDto;
import com.podium.model.entity.discipline.Discipline;
import com.podium.model.entity.event.Event;
import com.podium.model.entity.gender.Gender;
import com.podium.model.entity.localization.City;
import com.podium.model.entity.localization.Country;
import com.podium.model.entity.localization.Localization;
import com.podium.model.entity.localization.Street;
import com.podium.model.entity.user.User;
import com.podium.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EventService {

    private EventRepository eventRepository;
    private CityRepository cityRepository;
    private StreetRepository streetRepository;
    private LocalizationRepository localizationRepository;
    private DisciplineRepository disciplineRepository;
    private GenderRepository genderRepository;
    private UserRepository userRepository;

    @Autowired
    public EventService(
            EventRepository eventRepository,
            CityRepository cityRepository,
            StreetRepository streetRepository,
            LocalizationRepository localizationRepository,
            DisciplineRepository disciplineRepository,
            GenderRepository genderRepository,
            UserRepository userRepository) {

        this.eventRepository = eventRepository;
        this.cityRepository = cityRepository;
        this.streetRepository = streetRepository;
        this.localizationRepository = localizationRepository;
        this.disciplineRepository = disciplineRepository;
        this.genderRepository = genderRepository;
        this.userRepository = userRepository;
    }


    public void addEvent(EventRequestDto requestDto){
        this.eventRepository.save(this.convertRequestDtoToEntity(requestDto));
    }

    public boolean existEventByTitle(String eventTitle){
        return this.eventRepository.existsByTitle(eventTitle);
    }

    public void deleteEventByTitle(String eventTitle){
        this.eventRepository.deleteByTitle(eventTitle);

    }

    private Event convertRequestDtoToEntity(EventRequestDto requestDto){

        Event event = new Event();
        event.setTitle(requestDto.getTitle());
        event.setDateFrom(requestDto.getDateFrom());
        event.setDateTo(requestDto.getDateTo());

        City city;
        Street street;
        Localization localization;

        if(this.cityRepository.existsByCity(requestDto.getCity()))
            city = this.cityRepository.findByCity(requestDto.getCity());
        else{
            city = new City(requestDto.getCity());

        }



        if(this.streetRepository.existsByStreet(requestDto.getStreet()))
            street = this.streetRepository.findByStreet(requestDto.getStreet());
        else{
            street = new Street();
            street.setStreet(requestDto.getStreet());
        }



        if(this.localizationRepository
                .existsByCityAndStreetAndBuildingNumberAndPostalCode(
                        city,street,requestDto.getNumber(),requestDto.getPostal()
                ))
        localization =
                this.localizationRepository
                        .findByCityAndStreetAndBuildingNumberAndPostalCode(
                                city,street,requestDto.getNumber(),requestDto.getPostal());
        else{
            localization = new Localization();
            localization.setCity(city);
            localization.setStreet(street);
            localization.setBuildingNumber(requestDto.getNumber());
            localization.setPostalCode(requestDto.getPostal());
        }

        this.localizationRepository.save(localization);

        event.setLocalization(localization);

        Discipline discipline = this.disciplineRepository
                .findByDiscipline(requestDto.getDiscipline());

        event.setDiscipline(discipline);

        event.setPeopleNumber(requestDto.getPeople());

        for(String genderName : requestDto.getGenders()){
            Gender gender = this.genderRepository.findByGender(genderName);

            if(gender != null)
                event.getGenders().add(gender);
            else{
                Gender gender1 = new Gender();
                gender1.setGender("Chuj");
                event.getGenders().add(gender1);
            }
        }

        event.setMinAge(requestDto.getMinAge());
        event.setMaxAge(requestDto.getMaxAge());
        event.setCost(requestDto.getCost());
        event.setDescription(requestDto.getDescription());

        User user = this.userRepository.findByUsername(requestDto.getAuthor()).orElse(null);

        if (user != null) {
            System.out.println(user.getEmail());
        }

        event.setAuthor(user);

        event.setViews(0);

        event.setCreationDate(new Date());

        return event;

    }

    private CountryResponseDto convertEntityToResponseDto(Country country){

        CountryResponseDto responseDto = new CountryResponseDto();
        responseDto.setCountryId(country.getCountryId());
        responseDto.setIso3(country.getIso3());
        responseDto.setName(country.getName());
        responseDto.setPrintable_name(country.getPrintableName());
        responseDto.setNumCode(country.getNumCode());

        return responseDto;

    }




}
