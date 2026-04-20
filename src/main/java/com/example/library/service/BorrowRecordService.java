package com.example.library.service;

import com.example.library.dto.BorrowRecordRequestDTO;
import com.example.library.dto.BorrowRecordResponseDTO;
import com.example.library.entity.Book;
import com.example.library.entity.BorrowRecord;
import com.example.library.entity.Member;
import com.example.library.exception.DuplicateResourceException;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.mapper.BorrowRecordMapper;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BorrowRecordRepository;
import com.example.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BorrowRecordMapper borrowRecordMapper;

    @Transactional
    public BorrowRecordResponseDTO borrowBook(BorrowRecordRequestDTO request) {
        // 1. Check if book exists and is not currently borrowed
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        
        if (borrowRecordRepository.existsByBookIdAndReturnDateIsNull(book.getId())) {
            throw new DuplicateResourceException("Book is already borrowed");
        }

        // 2. Check if member exists
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        // 3. Create record
        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setMember(member);

        BorrowRecord savedRecord = borrowRecordRepository.save(record);
        return borrowRecordMapper.toResponseDTO(savedRecord);
    }

    @Transactional
    public BorrowRecordResponseDTO returnBook(Long id) {
        BorrowRecord record = borrowRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));

        if (record.getReturnDate() != null) {
            throw new IllegalArgumentException("Book is already returned");
        }

        record.setReturnDate(LocalDate.now());
        BorrowRecord updatedRecord = borrowRecordRepository.save(record);
        
        return borrowRecordMapper.toResponseDTO(updatedRecord);
    }

    @Transactional(readOnly = true)
    public List<BorrowRecordResponseDTO> getRecordsByMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member not found");
        }
        List<BorrowRecord> records = borrowRecordRepository.findByMemberIdWithDetails(memberId);
        return records.stream().map(borrowRecordMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BorrowRecordResponseDTO> getActiveBorrowRecords() {
        List<BorrowRecord> records = borrowRecordRepository.findActiveRecordsWithDetails();
        return records.stream().map(borrowRecordMapper::toResponseDTO).collect(Collectors.toList());
    }
}