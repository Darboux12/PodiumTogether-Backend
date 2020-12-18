package com.podium.service;

import com.podium.dal.entity.PodiumResource;
import com.podium.dal.files.FileRepository;
import com.podium.dal.files.exception.PodiumFileNotExistException;
import com.podium.dal.files.exception.PodiumMoreThanOneFileException;
import com.podium.dal.files.exception.PodiumNotSupportedImageType;
import com.podium.dal.repository.ResourceRepository;
import com.podium.service.exception.PodiumFileUploadFailException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ResourceService {

    private FileRepository fileRepository;
    private ResourceRepository resourceRepository;

    public ResourceService(FileRepository fileRepository, ResourceRepository resourceRepository) {
        this.fileRepository = fileRepository;
        this.resourceRepository = resourceRepository;
    }

    public Set<PodiumResource> createPodiumImageResources(Set<MultipartFile> images){

        Set<PodiumResource> resources = new HashSet<>();

        images.stream().filter(image -> this.fileRepository.isAcceptedImagesType(image.getContentType())).forEach(image -> {


                PodiumResource resource = new PodiumResource();
                resource.setName(image.getOriginalFilename());
                resource.setType(image.getContentType());

                String path = this.fileRepository.saveImageAndGetPath(image);

                resource.setPath(path);

                resources.add(resource);



        });

        return resources;

    }

    public Set<PodiumResource> createPodiumDocumentResources(Set<MultipartFile> documents){

        Set<PodiumResource> resources = new HashSet<>();

        documents
                .stream()
                .filter(doc -> this.fileRepository.isAcceptedDocumentType(doc.getContentType()))
                .forEach(doc -> {

                PodiumResource resource = new PodiumResource();
                resource.setName(doc.getOriginalFilename());
                resource.setType(doc.getContentType());

                String path = this.fileRepository.saveDocumentAndGetPath(doc);

                resource.setPath(path);

                resources.add(resource);

        });

        return resources;

    }

    @Transactional
    public void deleteResources(Set<PodiumResource> resources){

        this.deleteResourcesFromServer(resources);

        this.resourceRepository.deleteAll(resources);
    }

    private void deleteResourcesFromServer(Set<PodiumResource> resources){

        resources.forEach(resource -> {

            if(!this.fileRepository.existFileByPath(resource.getPath()))
                try {
                    throw new PodiumFileUploadFailException();
                } catch (PodiumFileUploadFailException e) {
                    e.printStackTrace();
                }

            this.fileRepository.deleteFile(resource.getPath());

        });
    }

}
