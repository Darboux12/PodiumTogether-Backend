package com.podium.service;

import com.podium.model.entity.Discipline;
import com.podium.repository.DisciplineRepository;
import org.apache.commons.text.WordUtils;
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

        disciplineName = WordUtils.capitalize(disciplineName);

        return this.disciplineRepository.existsByDiscipline(disciplineName);
    }

    public void addDiscipline(String disciplineName){

        Discipline discipline = new Discipline();
        discipline.setDiscipline(disciplineName);
        this.disciplineRepository.save(discipline);

    }

    public Iterable<Discipline> findAllDiscipline(){
        return this.disciplineRepository.findAll();
    }




}
