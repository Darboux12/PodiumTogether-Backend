package com.podium.model.dto.request;

import com.podium.validation.annotation.PodiumImageType;
import com.podium.validation.annotation.PodiumNotNull;
import com.podium.validation.annotation.PodiumTextNotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ResourceRequestDto {

   @PodiumNotNull
   private List<MultipartFile> files;
   @PodiumTextNotEmpty
   private String id;
}
