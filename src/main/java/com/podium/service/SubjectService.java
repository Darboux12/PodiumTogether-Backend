package com.podium.service;

import com.podium.dal.entity.Subject;
import com.podium.dal.repository.SubjectRepository;
import com.podium.service.dto.SubjectAddServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SubjectService {

    private SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public void addSubject(SubjectAddServiceDto subjectAddServiceDto){

        if(this.subjectRepository.existsBySubject(subjectAddServiceDto.getSubject()))
            throw new PodiumEntityAlreadyExistException("Subject");

        this.subjectRepository.save(
                this.convertServiceAddDtoToEntity(subjectAddServiceDto)
        );

    }

    @Transactional
    public void deleteSubjectByName(String name){

        if(!this.subjectRepository.existsBySubject(name))
            throw new PodiumEntityNotFoundException("Subject");

        this.subjectRepository.deleteBySubject(name);
    }

    public Iterable<Subject> findAllSubjects(){
        return this.subjectRepository.findAll();
    }

    public Subject findSubjectByName(String subjectName){

        return this
                .subjectRepository.findBySubject(subjectName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Subject"));
    }

    public boolean existSubjectByName(String subjectName){
        return this.subjectRepository.existsBySubject(subjectName);
    }

    private Subject convertServiceAddDtoToEntity(SubjectAddServiceDto subjectAddServiceDto){
        return new Subject(subjectAddServiceDto.getSubject());
    }

}
