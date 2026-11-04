package com.sipabu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String judul;
    private String penulis;
    private Integer stok;

    @Column(name = "status_ketersediaan")
    private Boolean statusKetersediaan = true;

    public Book() {}

    public Long getId() { return id; }
    public String getJudul() { return judul; }
    public String getPenulis() { return penulis; }
    public Integer getStok() { return stok; }
    public Boolean getStatusKetersediaan() { return statusKetersediaan; }
    
    public void setId(Long id) { this.id = id; }
    public void setJudul(String judul) { this.judul = judul; }
    public void setPenulis(String penulis) { this.penulis = penulis; }
    public void setStok(Integer stok) { this.stok = stok; }
    public void setStatusKetersediaan(Boolean statusKetersediaan) { this.statusKetersediaan = statusKetersediaan; }
}