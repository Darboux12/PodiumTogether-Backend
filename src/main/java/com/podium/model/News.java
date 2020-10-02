package com.podium.model;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "NEWS")
public class News {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "news_id")
    private int newsId;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "short_text")
    private String shortText;

    @NotNull
    @Column(name = "text")
    private String text;

    @NotNull
    @Column(name = "link_text")
    private String linkText;

    @NotNull
    @Column(name = "date")
    private Date date;

    @NotNull
    @Lob
    @Column(name = "image")
    private byte[] image;

}
