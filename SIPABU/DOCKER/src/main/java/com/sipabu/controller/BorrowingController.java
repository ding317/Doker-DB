package com.sipabu.controller;

import com.sipabu.model.Borrowing;
import com.sipabu.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/borrowing")

public class BorrowingController {
    @Autowired 
    private BorrowingRepository borrowingRepository;

    @GetMapping 
    public List<Borrowing> getAllbBorrowings(){
        return borrowingRepository.findAll();

    }

    @PostMapping 
    public ResponseEntity<Borrowing> createBorrowing(@RequestBody Borrowing borrowing){
        Borrowing saveBorrowing = borrowingRepository.save(borrowing);
        return new ResponseEntity<>(saveBorrowing, HttpStatus.CREATED);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<String> deleteBorrowing(@PathVariable long id){
        if (!borrowingRepository.existsById(id)){
            return new ResponseEntity<>("Peminjan dengan "+id+" tidak ditemukan" ,HttpStatus.NOT_FOUND);
        }
        borrowingRepository.deleteById(id);
        return new ResponseEntity<>("Peminjam berhasil di hapus " , HttpStatus.OK);
    }

    
}
