package com.podium.service;

import com.podium.dal.entity.City;
import com.podium.dal.repository.CityRepository;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.dto.request.CityAddServiceRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CityService {

    private CityRepository cityRepository;
    private UserService userService;

    public CityService(CityRepository cityRepository, UserService userService) {
        this.cityRepository = cityRepository;
        this.userService = userService;
    }

    @Transactional
    public void addCity(CityAddServiceRequest cityAddServiceRequest)
            throws PodiumEntityAlreadyExistException {






        if(this.cityRepository.existsByCity(cityAddServiceRequest.getCity()))
            throw new PodiumEntityAlreadyExistException("City");

        this.cityRepository.save(this.convertServiceDtoToEntity(cityAddServiceRequest));
    }

    @Transactional
    public void deleteCityByName(String cityName) throws PodiumEntityNotFoundException {

        City city = this.findCityByName(cityName);

        this.cityRepository.delete(city);

    }

    @Transactional
    public void deleteCity(City city) {

        if(city.getLocalizations().size() == 1)
            this.cityRepository.delete(city);

    }

    public boolean existCityByName(String cityName){
        return this.cityRepository.existsByCity(cityName);
    }

    public City findCityByName(String cityName) throws PodiumEntityNotFoundException {

        return this.cityRepository
                .findByCity(cityName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("City"));
    }

    public Iterable<City> findAllCity(){
        return this.cityRepository.findAll();
    }

    private City convertServiceDtoToEntity(CityAddServiceRequest cityAddServiceRequest){
        return new City(cityAddServiceRequest.getCity());
    }

    public City getEntity(String cityName){
        return this.cityRepository.findByCity(cityName).orElse(new City(cityName));
    }

}
