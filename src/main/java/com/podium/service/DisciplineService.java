package com.podium.service;

import com.podium.model.dto.request.DisciplineRequestDto;
import com.podium.model.dto.response.DisciplineResponseDto;
import com.podium.model.entity.City;
import com.podium.model.entity.Discipline;
import com.podium.repository.DisciplineRepository;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class DisciplineService {

    private DisciplineRepository disciplineRepository;

    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public boolean existByDisciplineName(String disciplineName){
        return this.disciplineRepository.existsByDiscipline(disciplineName);
    }

    @Transactional
    public void addDiscipline(DisciplineRequestDto requestDto){

        if(this.disciplineRepository.existsByDiscipline(requestDto.getDiscipline()))
            throw new PodiumEntityAlreadyExistException("Discipline");

        this.disciplineRepository.save(this.convertRequestDtoToEntity(requestDto));
    }

    public Iterable<Discipline> findAllDiscipline(){
        return this.disciplineRepository.findAll();
    }

    public Discipline findByDisciplineName(String disciplineName){

        return this.disciplineRepository
                .findByDiscipline(disciplineName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Discipline"));
    }

    @Transactional
    public void deleteDisciplineByName(String discipline){

        if(!this.disciplineRepository.existsByDiscipline(discipline))
            throw new PodiumEntityNotFoundException("Discipline");

        this.disciplineRepository.deleteByDiscipline(discipline);
    }

    private Discipline convertRequestDtoToEntity(DisciplineRequestDto requestDto){
        return new Discipline(requestDto.getDiscipline());
    }



}
