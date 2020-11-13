package com.podium.model.entity.time;

import com.podium.model.entity.event.Event;
import com.podium.model.entity.place.Place;
import com.podium.model.entity.time.WeekDay;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "BUSINESS_DAY")
public class BusinessDay {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "business_day_id")
    private int businessDayId;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="day", nullable=false)
    private WeekDay day;

    @NotNull
    @Column(name = "is_open")
    private boolean isOpen;

    @NotNull
    @Column(name = "is_opening_time_limit")
    private boolean isOpeningTimeLimit;

    @Column(name = "open_from")
    private LocalTime openTimeFrom;

    @Column(name = "open_to")
    private LocalTime openTimeTo;

    @OneToMany(mappedBy="businessDay")
    private Set<Place> places = new HashSet<>();

}
