package com.podium.service;

import com.podium.model.dto.request.DisciplineRequestDto;
import com.podium.model.dto.request.NewsRequestDto;
import com.podium.model.dto.response.DisciplineResponseDto;
import com.podium.model.dto.response.GenderResponseDto;
import com.podium.model.dto.response.NewsResponseDto;
import com.podium.model.entity.Discipline;
import com.podium.model.entity.News;
import com.podium.model.entity.PodiumResource;
import com.podium.model.other.PodiumFile;
import com.podium.repository.DisciplineRepository;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DisciplineService {

    private DisciplineRepository disciplineRepository;

    @Autowired
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
        for(Discipline discipline : this.disciplineRepository.findAll() )
            responseDtos.add(this.convertEntityToResponseDto(discipline));

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

        Discipline discipline = new Discipline();
        discipline.setDiscipline(requestDto.getDiscipline());
        return discipline;

    }

    private DisciplineResponseDto convertEntityToResponseDto(Discipline discipline){

        DisciplineResponseDto responseDto = new DisciplineResponseDto();
        responseDto.setDiscipline(discipline.getDiscipline());
        return responseDto;
    }

}
