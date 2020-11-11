package com.podium.service;

import com.podium.model.dto.request.DisciplineRequestDto;
import com.podium.model.dto.response.DisciplineResponseDto;
import com.podium.model.entity.discipline.Discipline;
import com.podium.repository.DisciplineRepository;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DisciplineService {

    private DisciplineRepository disciplineRepository;

    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public boolean existByDisciplineName(String disciplineName){

        disciplineName = WordUtils.capitalize(disciplineName);

        return this.disciplineRepository.existsByDiscipline(disciplineName);
    }

    public void addDiscipline(DisciplineRequestDto requestDto){
        this.disciplineRepository.save(this.convertRequestDtoToEntity(requestDto));
    }

    public Iterable<DisciplineResponseDto> findAllDiscipline(){

        List<DisciplineResponseDto> responseDtos = new ArrayList<>();

        this.disciplineRepository
                .findAll()
                .forEach(x -> responseDtos
                        .add(this.convertEntityToResponseDto(x))
                );

        return responseDtos;

    }

    public DisciplineResponseDto findByDisciplineName(String disciplineName){

        return this.convertEntityToResponseDto(
                this.disciplineRepository.findByDiscipline(disciplineName));
    }

    public void deleteDisciplineByName(String discipline){
        this.disciplineRepository.deleteByDiscipline(discipline);
    }

    private Discipline convertRequestDtoToEntity(DisciplineRequestDto requestDto){
        return new Discipline(requestDto.getDiscipline());
    }

    private DisciplineResponseDto convertEntityToResponseDto(Discipline discipline){
        return new DisciplineResponseDto(discipline.getDiscipline());
    }

}
