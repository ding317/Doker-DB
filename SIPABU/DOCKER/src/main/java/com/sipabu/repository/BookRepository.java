package com.sipabu.repository;

import com.sipabu.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Mencari buku berdasarkan status ketersediaan (misal: TRUE untuk buku yang bisa dipinjam)
    List<Book> findByStatusKetersediaan(Boolean status_ketersediaan);

    // Mencari buku berdasarkan judul (case-insensitive)
    List<Book> findByJudulContainingIgnoreCase(String judul);
}