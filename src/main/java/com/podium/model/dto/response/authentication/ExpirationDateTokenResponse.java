package com.podium.model.dto.response.authentication;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ExpirationDateTokenResponse {
    private Date expirationDate;
}
