package com.podium.model.dto.response;

import com.podium.model.dto.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityResponseDto implements ResponseDto {

    private int id;
    private String city;
}
