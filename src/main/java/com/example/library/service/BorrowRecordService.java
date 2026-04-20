package com.example.library.service;

import com.example.library.dto.BorrowRecordRequestDto;
import com.example.library.dto.BorrowRecordResponseDto;
import com.example.library.entity.Book;
import com.example.library.entity.BorrowRecord;
import com.example.library.entity.Member;
import com.example.library.exception.BookAlreadyBorrowedException;
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

@Service
@RequiredArgsConstructor
public class BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BorrowRecordMapper borrowRecordMapper;

    @Transactional
    public BorrowRecordResponseDto borrowBook(BorrowRecordRequestDto request) {
        // 1. Check if book exists and is not currently borrowed
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        
        if (borrowRecordRepository.existsByBookIdAndReturnDateIsNull(book.getId())) {
            throw new BookAlreadyBorrowedException("Book is already borrowed"); // [cite: 53, 87]
        }

        // 2. Check if member exists
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        // 3. Create record
        BorrowRecord record = BorrowRecord.builder()
                .book(book)
                .member(member)
                .build();

        BorrowRecord savedRecord = borrowRecordRepository.save(record);
        return borrowRecordMapper.toDto(savedRecord);
    }

    @Transactional
    public BorrowRecordResponseDto returnBook(Long id) {
        BorrowRecord record = borrowRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));

        if (record.getReturnDate() != null) {
            throw new IllegalArgumentException("Book is already returned"); // [cite: 97, 99]
        }

        record.setReturnDate(LocalDate.now()); // [cite: 53]
        BorrowRecord updatedRecord = borrowRecordRepository.save(record);
        
        return borrowRecordMapper.toDto(updatedRecord);
    }

    @Transactional(readOnly = true)
    public List<BorrowRecordResponseDto> getRecordsByMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member not found");
        }
        List<BorrowRecord> records = borrowRecordRepository.findByMemberIdWithDetails(memberId);
        return borrowRecordMapper.toDtoList(records);
    }

    @Transactional(readOnly = true)
    public List<BorrowRecordResponseDto> getActiveBorrowRecords() {
        List<BorrowRecord> records = borrowRecordRepository.findActiveRecordsWithDetails();
        return borrowRecordMapper.toDtoList(records);
    }
}