package com.podium.service;

import com.podium.dal.entity.Subject;
import com.podium.dal.repository.SubjectRepository;
import com.podium.service.dto.request.SubjectAddServiceRequest;
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
    public void addSubject(SubjectAddServiceRequest subjectAddServiceRequest) throws PodiumEntityAlreadyExistException {

        if(this.subjectRepository.existsBySubject(subjectAddServiceRequest.getSubject()))
            throw new PodiumEntityAlreadyExistException("Subject");

        this.subjectRepository.save(
                this.convertServiceAddDtoToEntity(subjectAddServiceRequest)
        );

    }

    @Transactional
    public void deleteSubjectByName(String name) throws PodiumEntityNotFoundException {

        if(!this.subjectRepository.existsBySubject(name))
            throw new PodiumEntityNotFoundException("Subject");

        this.subjectRepository.deleteBySubject(name);
    }

    public Iterable<Subject> findAllSubjects(){
        return this.subjectRepository.findAll();
    }

    public Subject findSubjectByName(String subjectName) throws PodiumEntityNotFoundException {

        return this
                .subjectRepository.findBySubject(subjectName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Subject"));
    }

    public boolean existSubjectByName(String subjectName){
        return this.subjectRepository.existsBySubject(subjectName);
    }

    private Subject convertServiceAddDtoToEntity(SubjectAddServiceRequest subjectAddServiceRequest){
        return new Subject(subjectAddServiceRequest.getSubject());
    }

}
