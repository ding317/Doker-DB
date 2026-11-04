package com.sipabu.repository;

import com.sipabu.model.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    // Mencari seluruh riwayat peminjaman berdasarkan ID User
    List<Borrowing> findByUserId(Long userId);
    
    // Mencari daftar transaksi berdasarkan status (misal: "DIPINJAM")
    List<Borrowing> findByStatus(String status);
}