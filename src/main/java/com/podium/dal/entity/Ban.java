package com.podium.dal.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "BAN")
public class Ban {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "ban_id")
    private int id;

    @NotNull
    @Column(name = "date_from")
    private LocalDateTime dateFrom;

    @NotNull
    @Column(name = "date_to")
    private LocalDateTime dateTo;

    @NotEmpty
    @Column(name = "reason")
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public Ban(LocalDateTime dateFrom, LocalDateTime dateTo, String reason, User user) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.reason = reason;
        this.user = user;
    }
}
