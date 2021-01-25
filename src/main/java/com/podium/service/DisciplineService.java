package com.podium.service;

import com.podium.dal.entity.Discipline;
import com.podium.dal.entity.User;
import com.podium.dal.repository.DisciplineRepository;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.dto.request.DisciplineAddServiceRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DisciplineService {

    private DisciplineRepository disciplineRepository;

    private UserService userService;
    private SecurityService securityService;

    public DisciplineService(DisciplineRepository disciplineRepository, UserService userService, SecurityService securityService) {
        this.disciplineRepository = disciplineRepository;
        this.userService = userService;
        this.securityService = securityService;
    }

    @Transactional
    public void addDiscipline(DisciplineAddServiceRequest addServiceDto, String adminUsername) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException, PodiumAuthorityException {

        User admin = userService.getEntity(adminUsername);
        this.securityService.validateUserAdminAuthority(admin);

        if(this.disciplineRepository.existsByDiscipline(addServiceDto.getDiscipline()))
            throw new PodiumEntityAlreadyExistException("Discipline");

        this.disciplineRepository.save(this.convertServiceAddDtoToEntity(addServiceDto));
    }

    @Transactional
    public void deleteDisciplineByName(String discipline, String adminUsername) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User admin = userService.getEntity(adminUsername);
        this.securityService.validateUserAdminAuthority(admin);

        if(!this.disciplineRepository.existsByDiscipline(discipline))
            throw new PodiumEntityNotFoundException("Discipline");

        this.disciplineRepository.deleteByDiscipline(discipline);
    }

    public boolean existDisciplineByName(String disciplineName){
        return this.disciplineRepository.existsByDiscipline(disciplineName);
    }

    public Iterable<Discipline> findAllDiscipline(){
        return this.disciplineRepository.findAll();
    }

    public Discipline findDisciplineByName(String disciplineName) throws PodiumEntityNotFoundException {

        return this.disciplineRepository
                .findByDiscipline(disciplineName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Discipline"));
    }

    private Discipline convertServiceAddDtoToEntity(DisciplineAddServiceRequest addServiceDto){
        return new Discipline(addServiceDto.getDiscipline());
    }

    public Discipline getEntity(String disciplineName) throws PodiumEntityNotFoundException {

        return this.disciplineRepository
                .findByDiscipline(disciplineName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Discipline"));

    }
}
