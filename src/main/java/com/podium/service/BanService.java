package com.podium.service;

import com.podium.dal.entity.Ban;
import com.podium.dal.entity.User;
import com.podium.dal.repository.BanRepository;
import com.podium.service.dto.request.BanUserServiceRequest;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BanService {

    private BanRepository banRepository;

    private UserService userService;

    public BanService(BanRepository banRepository, UserService userService) {
        this.banRepository = banRepository;
        this.userService = userService;
    }

    public void addBan(BanUserServiceRequest request) throws PodiumEntityNotFoundException {

        this.banRepository.save(this.convertServiceRequestToEntity(request));
    }

    private Ban convertServiceRequestToEntity(BanUserServiceRequest request) throws PodiumEntityNotFoundException {

        User user = userService.getEntity(request.getUsernameToBan());

        return new Ban(
                request.getDateFrom(),
                request.getDateTo(),
                request.getReason(),
                user
        );

    }





}
