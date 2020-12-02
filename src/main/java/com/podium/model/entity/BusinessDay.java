package com.podium.model.entity;

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
    @Column(name = "open")
    private boolean open;

    @Column(name = "open_from")
    private LocalTime openTimeFrom;

    @Column(name = "open_to")
    private LocalTime openTimeTo;

    @ManyToMany(mappedBy="businessDays")
    private Set<Place> places = new HashSet<>();

    public BusinessDay(WeekDay day, boolean isOpen, LocalTime openTimeFrom, LocalTime openTimeTo) {
        this.day = day;
        this.open = isOpen;
        this.openTimeFrom = openTimeFrom;
        this.openTimeTo = openTimeTo;
    }
}
