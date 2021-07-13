package com.kronos.roomlivedataexample.model;

import androidx.annotation.StringDef;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class User  implements Serializable {

    private final static long serialVersionUid = 1L;

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String ci;

    public User() {
        this.name = "";
        this.ci = "";
    }

    public User(String name, String ci) {
        this.name = name;
        this.ci = ci;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                getName().equals(user.getName()) &&
                getCi().equals(user.getCi());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCi());
    }
}
