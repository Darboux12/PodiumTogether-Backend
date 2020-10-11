package com.podium.model.entity;



import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "EVENT_FILE")
public class EventFile {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "file_id")
    private int fileId;

    @NotNull
    @Column(name="name")
    private String name;

    @NotNull
    @Column(name="type")
    private String type;

    @NotNull
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "content")
    private byte[] content;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="event_id", nullable=false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="news_id", nullable=false)
    private News news;



}
