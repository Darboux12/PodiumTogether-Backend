package com.podium.dal.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "user_id")
    private int id;

    @NotNull
    @Column(name = "username", unique = true)
    private String username;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="country_id", nullable=false)
    private Country country;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role") })
    private Set<Role> roles;

    @NotNull
    @Column(name = "birthday")
    private Date birthday;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_id", referencedColumnName = "resource_id")
    private PodiumResource profileImage;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "user_event",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id") })
    private Set<Event> eventsJoined = new HashSet<>();

    @OneToMany(mappedBy="author")
    private Set<Event> eventsCreated = new HashSet<>();

    @Column(name = "description")
    @Type(type = "text")
    private String description;

    @OneToMany(mappedBy="author")
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy="placeLocalization")
    private Set<Place> places = new HashSet<>();

    @OneToMany(mappedBy="user")
    private Set<Ban> bans = new HashSet<>();

    public User(String username, String email, String password,
                Country country, Set<Role> roles, Date birthday,
                PodiumResource profileImage, String description) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.country = country;
        this.roles = roles;
        this.birthday = birthday;
        this.profileImage = profileImage;
        this.description = description;
    }
}
