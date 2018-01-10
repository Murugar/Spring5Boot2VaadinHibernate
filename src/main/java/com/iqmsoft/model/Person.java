package com.iqmsoft.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Person implements Serializable{
    @Id
    @GenericGenerator(name="person_gen", strategy="increment")
    @GeneratedValue(generator="person_gen")
    @Column(unique = true, nullable = false)
    private Integer personId;

    private String name;

    @Column
    private String surname;

    @Column
    private String skills;

    @ManyToOne
    @JoinColumn(name="group_id")
    private Groups team;

    public void setMemberId(Integer memberId) {
        this.personId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Groups getTeam() {
        return team;
    }

    public void setTeam(Groups team) {
        this.team = team;
    }

    public Integer getPersonId() {
        return personId;
    }
}
