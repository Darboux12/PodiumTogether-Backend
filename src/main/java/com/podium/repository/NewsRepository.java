package com.podium.repository;

import com.podium.model.entity.News;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface NewsRepository extends CrudRepository<News,Integer> {

    News findByDate(Date date);


}
