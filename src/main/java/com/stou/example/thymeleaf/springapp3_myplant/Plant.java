package com.stou.example.thymeleaf.springapp3_myplant;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String thName;
    private String engName;
    private String family;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Date creageAt = new Date();;

    @Column(nullable = true, length = 64)
    private String imageFileName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThName() {
        return thName;
    }

    public void setThName(String thName) {
        this.thName = thName;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreageAt() {
        return creageAt;
    }

    public void setCreageAt(Date creageAt) {
        this.creageAt = creageAt;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "id=" + id +
                ", thName='" + thName + '\'' +
                ", engName='" + engName + '\'' +
                ", family='" + family + '\'' +
                ", description='" + description + '\'' +
                ", creageAt=" + creageAt +
                ", imageFileName='" + imageFileName + '\'' +
                '}';
    }
}
