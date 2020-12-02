package com.podium.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CONTACT")
public class Contact {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "contact_id")
    private int contactId;

    @NotNull
    @Column(name = "user_email", unique = true)
    private String userEmail;

    @NotNull
    @Type(type = "text")
    @Column(name = "message")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinColumn(name="subject", nullable=false)
    private Subject subject;

    public Contact(String userEmail, String message, Subject subject) {
        this.userEmail = userEmail;
        this.message = message;
        this.subject = subject;
    }
}
