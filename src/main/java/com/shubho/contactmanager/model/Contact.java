package com.shubho.contactmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(nullable = true)
    private String imageUrl = "defaultImage.png";
    @Column(length = 5000)
    private String description;
    @ManyToOne
    @JsonIgnore
    private User user;

//    @Override
//    public String toString() {
//        return "Contact{" +
//                "cId=" + cId +
//                ", name='" + name + '\'' +
//                ", nickName='" + nickName + '\'' +
//                ", work='" + work + '\'' +
//                ", phone='" + phone + '\'' +
//                ", email='" + email + '\'' +
//                ", imageUrl='" + imageUrl + '\'' +
//                ", description='" + description + '\'' +
//                ", user=" + user +
//                '}';
//    }
}