package com.podium.service;

import com.podium.model.entity.Discipline;
import com.podium.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisciplineService {

    private DisciplineRepository disciplineRepository;

    @Autowired
    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public boolean existByDisciplineName(String disciplineName){
        return this.disciplineRepository.existsByName(disciplineName);
    }

    public void addDiscipline(String disciplineName){

        Discipline discipline = new Discipline();
        discipline.setName(disciplineName);
        this.disciplineRepository.save(discipline);

    }




}
