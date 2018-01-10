package com.iqmsoft.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Groups implements Serializable{
    @Id
    @GenericGenerator(name="groups_gen", strategy="increment")
    @GeneratedValue(generator="groups_gen")
    @Column(name = "group_id", unique = true, nullable = false)
    private Integer groupId;

    @Column
    private String name;

    @Column
    private String description;

    public void setTeamId(Integer teamId) {
        this.groupId = teamId;
    }

    public Integer getTeamId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
