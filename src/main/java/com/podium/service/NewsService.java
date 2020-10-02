package com.podium.service;

import com.podium.model.News;
import com.podium.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewsService {

    private NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> getAllNews(){
        return this.newsRepository.findAll();
    }

    public void addNews(
            String title,
            String shortText,
            String linkText,
            String fullText,
            MultipartFile image
            ) throws IOException {

        News news = new News();
        news.setTitle(title);
        news.setShortText(shortText);
        news.setLinkText(linkText);
        news.setText(fullText);
        news.setImage(image.getBytes());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Date date = new Date();

        news.setDate(date);

        this.newsRepository.save(news);
    }



}
