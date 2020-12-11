package com.podium.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ResourceAddServiceDto {
   private List<MultipartFile> files;
   private String id;
}
