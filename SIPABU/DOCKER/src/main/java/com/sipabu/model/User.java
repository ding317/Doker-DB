package com.sipabu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nama;

    @Column(nullable = false, unique = true)
    private String email;

    // Nilai rata-rata skor kumulatif dari seluruh transaksi (Default 100.0)
    private Double averageScore = 100.0;

    // Rating global user (Default "A")
    private String globalRating = "A";

    public User() {}

    public User(String nama, String email) {
        this.nama = nama;
        this.email = email;
    }

    // Getter dan Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return nama; }
    public void setName(String name) { this.nama = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Double getAverageScore() { return averageScore; }
    public void setAverageScore(Double averageScore) { this.averageScore = averageScore; }

    public String getGlobalRating() { return globalRating; }
    public void setGlobalRating(String globalRating) { this.globalRating = globalRating; }
}
