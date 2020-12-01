package com.podium.service.contact;

import com.podium.model.dto.request.contact.SubjectRequestDto;
import com.podium.model.dto.response.contact.SubjectResponseDto;
import com.podium.model.entity.contact.Subject;
import com.podium.repository.contact.SubjectRepository;
import com.podium.validation.exception.PodiumNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {

    private SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public void addSubject(SubjectRequestDto requestDto){

        this.subjectRepository.save(
                this.convertSubjectRequestDtoToEntity(requestDto)
        );

    }

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

        return subject == null
                ? new SubjectResponseDto()
                : null;

    }

    private Subject findSubjectEntity(String subjectName){


        return this.subjectRepository.findBySubject(subjectName).orElseThrow(() -> new PodiumNotFoundException("Hej"));
    }

}
