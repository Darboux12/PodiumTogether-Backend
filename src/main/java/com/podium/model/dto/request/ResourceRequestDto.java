package com.podium.model.dto.request;

import com.podium.model.dto.validation.annotation.dto.PodiumNotNull;
import com.podium.model.dto.validation.annotation.dto.PodiumTextNotEmpty;
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
