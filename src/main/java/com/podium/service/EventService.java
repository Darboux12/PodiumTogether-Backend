package com.podium.service;

import com.podium.dal.entity.*;
import com.podium.dal.repository.EventRepository;
import com.podium.service.dto.converter.ServiceConverter;
import com.podium.service.dto.request.EventAddServiceRequest;
import com.podium.service.dto.response.EventServiceResponse;
import com.podium.service.dto.response.PlaceServiceResponse;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.exception.PodiumEntityTimeConsistencyError;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class EventService {

    private EventRepository eventRepository;

    private UserService userService;
    private SecurityService securityService;
    private PlaceService placeService;
    private ResourceService resourceService;
    private GenderService genderService;

    public EventService(EventRepository eventRepository, UserService userService, SecurityService securityService, PlaceService placeService, ResourceService resourceService, GenderService genderService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.securityService = securityService;
        this.placeService = placeService;
        this.resourceService = resourceService;
        this.genderService = genderService;
    }

    @Transactional
    public void addEvent(EventAddServiceRequest addServiceDto, String username) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException, PodiumEntityTimeConsistencyError, PodiumAuthorityException {

        User user = userService.getEntity(username);
        this.securityService.validateUserSubscriberAuthority(user);

        if(this.eventRepository.existsByTitle(addServiceDto.getTitle()))
            throw new PodiumEntityAlreadyExistException("Event with given title");

        this.eventRepository.save(this.convertServiceAddDtoToEntity(addServiceDto));

    }

    public EventServiceResponse findEventByTitle(String eventTitle, String username) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User user = userService.getEntity(username);
        this.securityService.validateUserSubscriberAuthority(user);

        return ServiceConverter
                .getInstance()
                .convertEventToResponseDto( this.eventRepository
                        .findByTitle(eventTitle)
                        .orElseThrow(() -> new PodiumEntityNotFoundException("Event with given name")));

    }

    public Iterable<EventServiceResponse> findAllEvents(String username) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User user = userService.getEntity(username);
        this.securityService.validateUserSubscriberAuthority(user);

        return ServiceConverter
                .getInstance()
                .convertEventIterableToResponseDto(this.eventRepository.findAll());

    }


    @Transactional
    public void deleteEventById(int id, String username) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User user = userService.getEntity(username);
        this.securityService.validateUserAdminAuthority(user);

        Event event = this.eventRepository.findById(id)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Event with given id"));

        this.eventRepository.deleteById(id);

        this.resourceService.deleteResources(event.getEventResources());
    }

    private Event convertServiceAddDtoToEntity(EventAddServiceRequest requestDto) throws PodiumEntityNotFoundException, PodiumEntityTimeConsistencyError {

        User author = this.userService.getEntity(requestDto.getAuthor());

        Place place = this.placeService.getEntity(requestDto.getPlaceName());

        Set<PodiumResource> imageResources = this.resourceService
                .createPodiumImageResources(requestDto.getImages());

        Set<PodiumResource> documentResources = this.resourceService
                .createPodiumDocumentResources(requestDto.getDocuments());

        Set<PodiumResource> resources = new HashSet<>();

        resources.addAll(imageResources);
        resources.addAll(documentResources);

        Set<Gender> genders = new HashSet<>();

        for(String gender : requestDto.getGenders())
            genders.add(this.genderService.findByGenderName(gender));

        return new Event(
                requestDto.getTitle(),
                requestDto.getDateFrom(),
                requestDto.getDateTo(),
                requestDto.getPeople(),
                requestDto.getMinAge(),
                requestDto.getMaxAge(),
                requestDto.getDescription(),
                author,
                place.getDiscipline(),
                0,
                resources,
                genders,
                place
        );
    }

}
