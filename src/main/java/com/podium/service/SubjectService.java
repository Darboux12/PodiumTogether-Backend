package com.podium.service;

import com.podium.model.dto.request.contact.SubjectRequestDto;
import com.podium.model.dto.response.contact.SubjectResponseDto;
import com.podium.model.entity.Subject;
import com.podium.repository.SubjectRepository;
import com.podium.controller.response.PodiumNotFoundWebException;
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

        this.subjectRepository.save(
                this.convertSubjectRequestDtoToEntity(requestDto)
        );

    }

    @Transactional
    public void deleteSubjectByName(String name){
        this.subjectRepository.deleteBySubject(name);
    }

    public Iterable<SubjectResponseDto> findAllSubjects(){

        List<SubjectResponseDto> responseDtos = new ArrayList<>();

        for(Subject subject : this.subjectRepository.findAll())
            responseDtos.add(this.convertSubjectEntityToResponseDto(subject));

        return responseDtos;
    }

    public SubjectResponseDto findSubjectByName(String subjectName){
        return this.convertSubjectEntityToResponseDto(
                this.findSubjectEntity(subjectName)
        );
    }

    public boolean existSubjectByName(String subjectName){
        return this.subjectRepository.existsBySubject(subjectName);
    }

    private Subject convertSubjectRequestDtoToEntity(SubjectRequestDto requestDto){

        Subject subject = new Subject();
        subject.setSubject(requestDto.getSubject());
        return subject;
    }

    private SubjectResponseDto convertSubjectEntityToResponseDto(Subject subject){

        return new SubjectResponseDto(subject.getSubject());

    }

    private Subject findSubjectEntity(String subjectName){


        return this.subjectRepository.findBySubject(subjectName).orElseThrow(() -> new PodiumNotFoundWebException("Hej"));
    }

}
