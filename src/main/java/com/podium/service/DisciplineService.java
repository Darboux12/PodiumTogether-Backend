package com.podium.service;

import com.podium.dal.entity.Discipline;
import com.podium.dal.repository.DisciplineRepository;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.dto.DisciplineAddServiceDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DisciplineService {

    private DisciplineRepository disciplineRepository;

    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    @Transactional
    public void addDiscipline(DisciplineAddServiceDto addServiceDto) throws PodiumEntityAlreadyExistException {

        if(this.disciplineRepository.existsByDiscipline(addServiceDto.getDiscipline()))
            throw new PodiumEntityAlreadyExistException("Discipline");

        this.disciplineRepository.save(this.convertServiceAddDtoToEntity(addServiceDto));
    }

    @Transactional
    public void deleteDisciplineByName(String discipline) throws PodiumEntityNotFoundException {

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

    private Discipline convertServiceAddDtoToEntity(DisciplineAddServiceDto addServiceDto){
        return new Discipline(addServiceDto.getDiscipline());
    }

    public Discipline getEntity(String disciplineName) throws PodiumEntityNotFoundException {

        return this.disciplineRepository
                .findByDiscipline(disciplineName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Discipline"));

    }
}
