package com.podium.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BanUserServiceRequest {
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String reason;
    private String usernameToBan;
    private String adminUsername;
}
