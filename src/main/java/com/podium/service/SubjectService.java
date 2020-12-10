package com.podium.service;

import com.podium.model.dto.request.SubjectRequestDto;
import com.podium.model.dto.response.SubjectResponseDto;
import com.podium.model.entity.Subject;
import com.podium.repository.SubjectRepository;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {

    private SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public void addSubject(SubjectRequestDto requestDto){

        if(this.subjectRepository.existsBySubject(requestDto.getSubject()))
            throw new PodiumEntityAlreadyExistException("Subject");

        this.subjectRepository.save(
                this.convertRequestDtoToEntity(requestDto)
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

    private Subject convertRequestDtoToEntity(SubjectRequestDto requestDto){

        Subject subject = new Subject();
        subject.setSubject(requestDto.getSubject());
        return subject;
    }

}
