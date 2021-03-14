package com.shubho.contactmanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CONTACT")
@Getter
@Setter
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cId;
    private String name;
    private String nickName;
    private String work;
    private String phone;
    private String email;
    private String imageUrl;
    @Column(length = 5000)
    private String description;
    @ManyToOne
    private User user;
}