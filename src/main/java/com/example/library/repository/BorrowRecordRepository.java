package com.example.library.repository;

import com.example.library.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    // Checks if a book is currently borrowed (returnDate is null)
    boolean existsByBookIdAndReturnDateIsNull(Long bookId);

    // Resolves N+1 Problem for member's borrow records
    @Query("SELECT br FROM BorrowRecord br JOIN FETCH br.book b JOIN FETCH b.author JOIN FETCH br.member WHERE br.member.id = :memberId")
    List<BorrowRecord> findByMemberIdWithDetails(@Param("memberId") Long memberId);

    // Resolves N+1 Problem for active borrow records
    @Query("SELECT br FROM BorrowRecord br JOIN FETCH br.book b JOIN FETCH b.author JOIN FETCH br.member WHERE br.returnDate IS NULL")
    List<BorrowRecord> findActiveRecordsWithDetails();
}