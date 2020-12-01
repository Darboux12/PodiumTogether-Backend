package com.podium.repository.news;

import com.podium.model.entity.news.News;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface NewsRepository extends CrudRepository<News,Integer> {

    News findByDate(Date date);

    News findByTitle(String newsTitle);

    boolean existsByTitle(String newsTitle);

    boolean existsByDate(Date date);

    Iterable<News> findAllByOrderByDateDesc();

}
