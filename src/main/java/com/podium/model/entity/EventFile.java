package com.podium.model.entity;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

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



}
