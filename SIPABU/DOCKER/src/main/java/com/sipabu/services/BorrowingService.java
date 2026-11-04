package com.sipabu.services;

import com.sipabu.model.Borrowing;
import com.sipabu.model.User;
import com.sipabu.repository.BorrowingRepository;
import com.sipabu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Borrowing processReturn(Long borrowingId, String bookCondition) {
        // 1. Cari data transaksi peminjaman
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new RuntimeException("Transaksi peminjaman tidak ditemukan dengan ID: " + borrowingId));

        if ("DIKEMBALIKAN".equalsIgnoreCase(borrowing.getStatus())) {
            throw new RuntimeException("Buku untuk transaksi ini sudah dikembalikan sebelumnya.");
        }

        // 2. Set tanggal kembali dan kondisi buku
        LocalDateTime returnTime = LocalDateTime.now();
        borrowing.setReturnDate(returnTime);
        borrowing.setBookCondition(bookCondition);
        borrowing.setStatus("DIKEMBALIKAN");

        // 3. Kalkulasi Skor Peminjaman (60% Waktu + 40% Kondisi)
        double timeScore = calculateTimeScore(borrowing.getBorrowDate(), returnTime);
        double conditionScore = calculateConditionScore(bookCondition);
        double transactionScore = (0.60 * timeScore) + (0.40 * conditionScore);

        borrowing.setTotalScore(transactionScore);
        Borrowing updatedBorrowing = borrowingRepository.save(borrowing);

        // 4. Update akumulasi rating pada profil User
        updateUserGlobalRating(borrowing.getUser());

        return updatedBorrowing;
    }

    // Skor Ketepatan Waktu (Batas ideal: 7 hari pinjam)
    private double calculateTimeScore(LocalDateTime borrowDate, LocalDateTime returnDate) {
        long daysBorrowed = ChronoUnit.DAYS.between(borrowDate, returnDate);
        long maxDays = 7; // Batas peminjaman gratis terlambat

        if (daysBorrowed <= maxDays) {
            return 100.0;
        } else {
            long daysLate = daysBorrowed - maxDays;
            double score = 100.0 - (daysLate * 10.0); // Denda 10 poin per hari terlambat
            return Math.max(score, 0.0);
        }
    }

    // Skor Kondisi Buku
    private double calculateConditionScore(String condition) {
        if (condition == null) return 100.0;
        switch (condition.toUpperCase()) {
            case "GOOD":
            case "BAIK":
                return 100.0;
            case "RUSAK_RINGAN":
                return 70.0;
            case "RUSAK_BERAT":
                return 40.0;
            case "HILANG":
                return 0.0;
            default:
                return 80.0;
        }
    }

    // Kalkulasi Rata-rata Skor Kumulatif & Penentuan Grade (A-D)
    private void updateUserGlobalRating(User user) {
        List<Borrowing> userBorrowings = borrowingRepository.findByUserId(user.getId());
        
        if (userBorrowings.isEmpty()) return;

        double sumScore = 0.0;
        int count = 0;

        for (Borrowing b : userBorrowings) {
            if (b.getTotalScore() != null) {
                sumScore += b.getTotalScore();
                count++;
            }
        }

        if (count > 0) {
            double average = sumScore / count;
            user.setAverageScore(average);

            // Penentuan Grade SIPABU
            if (average >= 85.0) {
                user.setGlobalRating("A");
            } else if (average >= 70.0) {
                user.setGlobalRating("B");
            } else if (average >= 55.0) {
                user.setGlobalRating("C");
            } else {
                user.setGlobalRating("D");
            }

            userRepository.save(user);
        }
    }
}