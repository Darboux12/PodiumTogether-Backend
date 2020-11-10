package com.podium.model.entity.time;

import com.podium.model.entity.discipline.Discipline;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "OPENING_HOUR")
public class OpeningHour {

    @NotNull
    @Id
    @Column(name = "opening_hour_id")
    private int id;

    @Column(name = "open_hour")
    private LocalTime openHour;

    @Column(name = "close_hour")
    private LocalTime closeHour;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="week_day", nullable=false)
    private WeekDay weekDay;

}
