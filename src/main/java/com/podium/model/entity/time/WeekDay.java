package com.podium.model.entity.time;

import com.podium.model.entity.event.Event;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "WEEK_DAY")
public class WeekDay {

    @NotNull
    @Id
    @Column(name = "day")
    private String day;

    @OneToMany(mappedBy="weekDay")
    private Set<OpeningHour> openingHours;

}
