package com.podium.model.entity.contact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.podium.model.entity.contact.Subject;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","subject"})
@Table(name = "CONTACT")
public class Contact {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "contact_id")
    private int contactId;

    @NotNull
    @Column(name = "user_email")
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

}
