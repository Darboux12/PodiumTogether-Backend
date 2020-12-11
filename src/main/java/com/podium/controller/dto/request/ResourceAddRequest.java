package com.podium.controller.dto.request;

import com.podium.controller.validation.annotation.PodiumNotNull;
import com.podium.controller.validation.annotation.PodiumTextNotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ResourceAddRequest {

   @PodiumNotNull
   private List<MultipartFile> files;
   @PodiumTextNotEmpty
   private String id;
}
