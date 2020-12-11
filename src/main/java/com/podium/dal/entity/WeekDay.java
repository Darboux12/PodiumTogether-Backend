package com.podium.dal.entity;

import com.sun.istack.NotNull;
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

    @OneToMany(mappedBy="day")
    private Set<BusinessDay> businessDays = new HashSet<>();

}
