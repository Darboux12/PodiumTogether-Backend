package com.podium.service.resource;

import com.podium.constant.PodiumPath;
import com.podium.model.dto.request.resource.ResourceRequestDto;
import com.podium.model.entity.event.Event;
import com.podium.model.entity.news.News;
import com.podium.model.entity.resource.PodiumResource;
import com.podium.model.entity.user.User;
import com.podium.repository.event.EventRepository;
import com.podium.repository.news.NewsRepository;
import com.podium.repository.resource.ResourceRepository;
import com.podium.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ResourceService {

    private UserRepository userRepository;
    private NewsRepository newsRepository;
    private ResourceRepository resourceRepository;
    private EventRepository eventRepository;

    @Autowired
    public ResourceService(UserRepository userRepository, NewsRepository newsRepository, ResourceRepository resourceRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;
        this.resourceRepository = resourceRepository;
        this.eventRepository = eventRepository;
    }

    public void uploadUserProfileImages(ResourceRequestDto requestDto) throws IOException {

        MultipartFile image = requestDto.getFiles().stream().findAny().orElse(null);

        PodiumResource resource = null;

        if (image != null) {

            resource = this.createPodiumResourceImage(image);
            User user = this.userRepository.findByUsername(requestDto.getId()).orElse(null);

            PodiumResource resourceToDelete;

            if (user != null) {

                resourceToDelete = user.getProfileImage();

                user.setProfileImage(resource);

                if(resourceToDelete != null) {

                    this.resourceRepository.
                            deleteByResourceId(resourceToDelete.getResourceId());

                    if(!this.resourceRepository.existsByResourceId(resourceToDelete.getResourceId())){
                        this.deleteResourceFromServer(resourceToDelete);
                    }

                }

            }

            this.userRepository.save(user);

        }

    }

    public void uploadNewsImages(ResourceRequestDto requestDto) throws IOException {

        for (MultipartFile image : requestDto.getFiles()) {

            PodiumResource resource = this.createPodiumResourceImage(image);

            News news = this.newsRepository.findByTitle(requestDto.getId());

            news.getNewsResources().add(resource);

            resource.getNews().add(news);

            this.resourceRepository.save(resource);
        }
    }

    public void uploadEventImages(ResourceRequestDto requestDto) throws IOException {

        for (MultipartFile image : requestDto.getFiles()) {

            PodiumResource resource = this.createPodiumResourceImage(image);

            Event event = this.eventRepository.findByTitle(requestDto.getId());

            event.getEventResources().add(resource);

            resource.getEvents().add(event);

            this.resourceRepository.save(resource);
        }
    }

    public void uploadEventFiles(ResourceRequestDto requestDto) throws IOException {

        for (MultipartFile file : requestDto.getFiles()) {

            PodiumResource resource = this.createPodiumResourceFile(file);

            Event event = this.eventRepository.findByTitle(requestDto.getId());

            event.getEventResources().add(resource);

            resource.getEvents().add(event);

            this.resourceRepository.save(resource);
        }
    }

    private PodiumResource createPodiumResourceImage(MultipartFile image) throws IOException {

        String path = PodiumPath.images + image.getOriginalFilename();

        File file = new File(path);

        image.transferTo(file);

        PodiumResource resource = new PodiumResource();
        resource.setName(image.getOriginalFilename());
        resource.setType(image.getContentType());
        resource.setPath(path);

        return resource;
    }

    private PodiumResource createPodiumResourceFile(MultipartFile image) throws IOException {

        String path = PodiumPath.files + image.getOriginalFilename();

        File file = new File(path);

        image.transferTo(file);

        PodiumResource resource = new PodiumResource();
        resource.setName(image.getOriginalFilename());
        resource.setType(image.getContentType());
        resource.setPath(path);

        return resource;
    }

    private void deleteResourceFromServer(PodiumResource resource){

        File file = new File(resource.getPath());

        if(file.delete())
            System.out.println("File deleted successfully");

        else
            System.out.println("Failed to delete the file");

    }
}
