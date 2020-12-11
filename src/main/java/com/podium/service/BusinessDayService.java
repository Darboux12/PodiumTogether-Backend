package com.podium.service;

import com.podium.dal.repository.BusinessDayRepository;
import org.springframework.stereotype.Service;

@Service
public class BusinessDayService {

    private BusinessDayRepository businessDayRepository;

    public BusinessDayService(BusinessDayRepository businessDayRepository) {
        this.businessDayRepository = businessDayRepository;
    }
}
